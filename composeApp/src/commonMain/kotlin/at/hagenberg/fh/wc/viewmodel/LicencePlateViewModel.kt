package at.hagenberg.fh.wc.viewmodel

import at.hagenberg.fh.wc.FEUERWEHR_APP_TOKEN
import at.hagenberg.fh.wc.model.rescue.sheet.Powertrain
import at.hagenberg.fh.wc.model.rescue.sheet.dto.EuroRescueRoot
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.parameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

private const val FEUERWEHR_APP_URL = "https://www.feuerwehrapp.at/int"
private const val FEUERWEHR_APP_LOGIN_URL =
    "$FEUERWEHR_APP_URL/index.php?token=$FEUERWEHR_APP_TOKEN"
private const val FEUERWEHR_APP_LICENCE_PLATE_URL = "$FEUERWEHR_APP_URL/kennzeichenuebung/index.php"
private const val FEUERWEHR_APP_AUTHORITY_KEY = "plate_pref"
private const val FEUERWEHR_APP_NUMBER_KEY = "plate_number"

private const val EURO_RESCUE_API = "https://api.rescue.euroncap.com/euro-rescue/variants"

class LicencePlateViewModel : ViewModel() {
    private val _httpClient = HttpClient(CIO) {
        install(HttpCookies)
    }
    private val _response = MutableStateFlow("")
    private val json = Json {
        ignoreUnknownKeys = true
    }
    val response = _response.asStateFlow()

    fun sendRaw(carMake: String, powertrain: Powertrain) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val requestBody = """
                    {
                        "query": "SELECT * FROM c WHERE UPPER(c.make_name) LIKE @make_name AND c.powertrain LIKE @powertrain",
                        "parameters": [
                            { "name": "@make_name", "value": "${carMake.uppercase()}" },
                            { "name": "@powertrain", "value": "%$powertrain%" }
                        ]
                    }
                    """
                println(requestBody)
                val httpResponse: HttpResponse = _httpClient.post(EURO_RESCUE_API) {
                    setBody(requestBody)
                }

                val euroRescueRoot: EuroRescueRoot = json.decodeFromString(httpResponse.body())
                _response.value = "Number of matching cars: ${euroRescueRoot.count}"

            } catch (e: Exception) {
                _response.value = "Raw request failed: ${e.message}"
            }
        }
    }

    fun searchLicencePlate(authority: String, number: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _httpClient.post(FEUERWEHR_APP_LOGIN_URL)

                val licencePlateResponse =
                    _httpClient.submitForm(url = FEUERWEHR_APP_LICENCE_PLATE_URL,
                        formParameters = parameters {
                            append(FEUERWEHR_APP_AUTHORITY_KEY, authority)
                            append(FEUERWEHR_APP_NUMBER_KEY, number)
                        }
                    )

                println(licencePlateResponse)

            } catch (e: Exception) {
                _response.value = "Licence plate search failed: ${e.message}"
            }
        }
    }
}