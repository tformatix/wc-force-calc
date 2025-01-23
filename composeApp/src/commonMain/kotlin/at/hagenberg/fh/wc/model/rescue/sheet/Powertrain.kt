package at.hagenberg.fh.wc.model.rescue.sheet

private const val FEUERWEHR_APP_POWERTRAIN_PATTERN = "(.*) &nbsp;.*"

private const val FEUERWEHR_APP_POWERTRAIN_DIESEL = "Diesel"
private const val FEUERWEHR_APP_POWERTRAIN_ELECTRIC = "Elektro"
private const val FEUERWEHR_APP_POWERTRAIN_GASOLINE = "Benzin"
private const val FEUERWEHR_APP_POWERTRAIN_HYBRID = "Hybr."

enum class Powertrain(val nameForEuroRescueApp: String) {
    CNG("CNG"),
    DIESEL("Diesel"),
    ELECTRIC("Electric"),
    GASOLINE("Gasoline"),
    HYBRID("Hybrid"),
    LNG("LNG");

    companion object {
        fun from(input: String): Powertrain? {
            val parsedInput =
                Regex(FEUERWEHR_APP_POWERTRAIN_PATTERN).find(input)?.groupValues?.get(1)

            return when {
                parsedInput == null -> null
                parsedInput.contains(CNG.toString()) -> CNG
                parsedInput == FEUERWEHR_APP_POWERTRAIN_DIESEL -> DIESEL
                parsedInput == FEUERWEHR_APP_POWERTRAIN_ELECTRIC -> ELECTRIC
                parsedInput == FEUERWEHR_APP_POWERTRAIN_GASOLINE -> GASOLINE
                parsedInput.contains(FEUERWEHR_APP_POWERTRAIN_HYBRID) -> HYBRID
                parsedInput.contains(LNG.toString()) -> LNG
                else -> null
            }
        }
    }

    override fun toString(): String {
        return nameForEuroRescueApp
    }
}