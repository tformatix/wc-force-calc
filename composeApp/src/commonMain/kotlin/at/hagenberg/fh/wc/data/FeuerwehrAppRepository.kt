package at.hagenberg.fh.wc.data

import at.hagenberg.fh.wc.FEUERWEHR_APP_TOKEN
import at.hagenberg.fh.wc.model.rescue.sheet.FeuerwehrAppCar
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.cookies.AcceptAllCookiesStorage
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.parameters

private const val BASE_URL = "https://www.feuerwehrapp.at/int"
private const val LOGIN_URL = "$BASE_URL/index.php?token=$FEUERWEHR_APP_TOKEN"
private const val LICENCE_PLATE_URL = "$BASE_URL/kennzeichenuebung/index.php"
private const val AUTHORITY_KEY = "plate_pref"
private const val NUMBER_KEY = "plate_number"

class FeuerwehrAppRepository {
    suspend fun searchLicencePlate(authority: String, number: String): FeuerwehrAppCar {
        val httpClient = HttpClient(CIO) {
            install(HttpCookies) {
                storage = AcceptAllCookiesStorage()
            }
        }

        httpClient.get(LOGIN_URL)

        val response = httpClient.submitForm(
            url = LICENCE_PLATE_URL,
            formParameters = parameters {
                append(AUTHORITY_KEY, authority)
                append(NUMBER_KEY, number)
            }
        )

        return FeuerwehrAppCar.parseFromHtml(response.bodyAsText())
    }
}