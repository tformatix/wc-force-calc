package at.hagenberg.fh.wc.data

import at.hagenberg.fh.wc.model.rescue.sheet.FeuerwehrAppCar
import at.hagenberg.fh.wc.model.rescue.sheet.dto.EuroRescueCar
import at.hagenberg.fh.wc.model.rescue.sheet.dto.EuroRescueRoot
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.json.Json

private const val API_URL = "https://api.rescue.euroncap.com/euro-rescue/variants"

class EuroRescueRepository {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    suspend fun findCar(feuerwehrAppCar: FeuerwehrAppCar): EuroRescueCar? {
        val requestBody = """
            {
                "query": "SELECT * FROM c WHERE UPPER(c.make_name) LIKE @make_name AND c.powertrain LIKE @powertrain",
                "parameters": [
                    { "name": "@make_name", "value": "${feuerwehrAppCar.carMake.uppercase()}" },
                    { "name": "@powertrain", "value": "%${feuerwehrAppCar.powertrain}%" }
                ]
            }
        """

        val httpClient = HttpClient(CIO)
        val httpResponse: HttpResponse = httpClient.post(API_URL) {
            setBody(requestBody)
        }

        val euroRescueRoot: EuroRescueRoot = json.decodeFromString(httpResponse.body())

        // TODO: find right car

        return euroRescueRoot.documents.firstOrNull()
    }
}