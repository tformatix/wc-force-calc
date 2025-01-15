package at.hagenberg.fh.wc.viewmodel

import at.hagenberg.fh.wc.model.rescue.sheet.Powertrain
import at.hagenberg.fh.wc.model.rescue.sheet.dto.EuroRescueRoot
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.utils.io.core.use
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

private const val EURO_RESCUE_API = "https://api.rescue.euroncap.com/euro-rescue/variants"

class LicencePlateViewModel : ViewModel() {
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
                            { "name": "@make_name", "value": "$carMake" },
                            { "name": "@powertrain", "value": "%$powertrain%" }
                        ]
                    }
                    """
                println(requestBody)
                val httpResponse: HttpResponse = HttpClient(CIO).use { httpClient ->
                    httpClient.post(EURO_RESCUE_API) {
                        setBody(requestBody)
                    }
                }

                val euroRescueRoot: EuroRescueRoot = json.decodeFromString(httpResponse.body())
                _response.value = "Number of matching cars: ${euroRescueRoot.count}"

            } catch (e: Exception) {
                _response.value = "Raw request failed: ${e.message}"
            }
        }
    }

    fun searchLicencePlate(authority: String, number: String) {

    }
}