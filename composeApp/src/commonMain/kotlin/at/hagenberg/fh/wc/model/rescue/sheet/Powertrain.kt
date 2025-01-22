package at.hagenberg.fh.wc.model.rescue.sheet

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
        fun from(input: String): Powertrain {
            return when {
                input.contains(CNG.toString()) -> CNG
                input == FEUERWEHR_APP_POWERTRAIN_DIESEL -> DIESEL
                input == FEUERWEHR_APP_POWERTRAIN_ELECTRIC -> ELECTRIC
                input == FEUERWEHR_APP_POWERTRAIN_GASOLINE -> GASOLINE
                input.contains(FEUERWEHR_APP_POWERTRAIN_HYBRID) -> HYBRID
                input.contains(LNG.toString()) -> LNG
                else -> throw IllegalArgumentException("No enum constant found for value: $input")
            }
        }
    }

    override fun toString(): String {
        return nameForEuroRescueApp
    }
}