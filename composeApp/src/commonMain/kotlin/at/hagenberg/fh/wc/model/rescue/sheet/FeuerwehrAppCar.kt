package at.hagenberg.fh.wc.model.rescue.sheet

import kotlinx.datetime.Instant

private val FEUERWEHR_APP_FIELDS = mapOf(
    "Antrieb" to "powertrain",
    "Marke" to "make",
    "Name" to "model",
    "Type" to "type",
    "Höchstzul. Masse" to "maxTotalMass",
    "Erstzulassung" to "initialRegistrationDate",
    "FIN" to "vin",
    "Variante" to "variant",
    "Version" to "version"
)

private const val FEUERWEHR_APP_PATTERN = "<td>(.*?)</td>\\s*<td><h3>(.*?)</h3></td>"

data class FeuerwehrAppCar(
    val powertrain: Powertrain?,
    val make: String?,
    val model: String?,
    val type: String?,
    val maxTotalMass: Int?,
    val initialRegistrationDate: Instant?,
    val vin: String?,
    val variant: String?,
    val version: String?,
) {
    companion object {
        fun parseFromHtml(html: String): FeuerwehrAppCar {
            val values = mutableMapOf<String, String>()

            Regex(FEUERWEHR_APP_PATTERN).findAll(html).forEach { matchResult ->
                val key = matchResult.groupValues[1]
                val value = matchResult.groupValues[2]
                if (FEUERWEHR_APP_FIELDS.containsKey(key)) {
                    values[FEUERWEHR_APP_FIELDS[key]!!] = value
                }
            }

            return FeuerwehrAppCar(
                powertrain = values["powertrain"]?.let { Powertrain.from(it) },
                make = values["make"],
                model = values["model"],
                type = values["type"],
                maxTotalMass = values["maxTotalMass"]?.toInt(),
                initialRegistrationDate = values["initialRegistrationDate"]?.let { Instant.parse(it) },
                vin = values["vin"],
                variant = values["variant"],
                version = values["version"]
            )
        }
    }
}