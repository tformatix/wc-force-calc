package at.hagenberg.fh.wc.data

import at.hagenberg.fh.wc.model.rescue.sheet.FeuerwehrAppCar
import at.hagenberg.fh.wc.model.rescue.sheet.dto.EuroRescueCar
import at.hagenberg.fh.wc.model.rescue.sheet.dto.EuroRescueRoot
import com.willowtreeapps.fuzzywuzzy.diffutils.FuzzySearch
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.json.Json

private const val API_URL = "https://api.rescue.euroncap.com/euro-rescue/variants"
private const val FUZZY_SEARCH_THRESHOLD = 90

class EuroRescueRepository {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    suspend fun findCar(feuerwehrAppCar: FeuerwehrAppCar): EuroRescueCar? {
        val feuerwehrAppCarMake = requireNotNull(feuerwehrAppCar.make)
        val feuerwehrAppCarModel = requireNotNull(feuerwehrAppCar.model)
        val feuerwehrAppCarInitialRegistrationYear =
            requireNotNull(feuerwehrAppCar.initialRegistrationDate).year
        val feuerwehrAppCarPowertrain = requireNotNull(feuerwehrAppCar.powertrain)

        val requestBody = """
            {
                "query": "SELECT * FROM c WHERE UPPER(c.make_name) LIKE @make_name AND c.powertrain LIKE @powertrain",
                "parameters": [
                    { "name": "@make_name", "value": "${feuerwehrAppCarMake.uppercase()}" },
                    { "name": "@powertrain", "value": "%${feuerwehrAppCarPowertrain}%" }
                ]
            }
        """

        val httpClient = HttpClient(CIO)
        val httpResponse: HttpResponse = httpClient.post(API_URL) {
            setBody(requestBody)
        }

        val euroRescueRoot: EuroRescueRoot = json.decodeFromString(httpResponse.body())
        httpClient.close()

        val matchingCarsByInitialRegistrationYear =
            euroRescueRoot.documents.filter { euroRescueCar ->
                if (euroRescueCar.buildYearUntil == null) {
                    euroRescueCar.buildYearFrom <= feuerwehrAppCarInitialRegistrationYear
                } else {
                    val buildYearUntil = euroRescueCar.buildYearUntil.toInt()
                    feuerwehrAppCarInitialRegistrationYear in euroRescueCar.buildYearFrom..buildYearUntil
                }
            }

        val carsWithHighestMatch = matchingCarsByInitialRegistrationYear.filter { car ->
            val matchingScoreTokenSetRatio =
                FuzzySearch.tokenSetRatio(car.modelName, feuerwehrAppCarModel)
            val matchingScorePartialRatio =
                FuzzySearch.partialRatio(car.modelName, feuerwehrAppCarModel)

            matchingScorePartialRatio >= FUZZY_SEARCH_THRESHOLD || matchingScoreTokenSetRatio >= FUZZY_SEARCH_THRESHOLD
        }

        // TODO: Return multiple cars (e.g. in case of different body types)
        return carsWithHighestMatch.firstOrNull()
    }
}