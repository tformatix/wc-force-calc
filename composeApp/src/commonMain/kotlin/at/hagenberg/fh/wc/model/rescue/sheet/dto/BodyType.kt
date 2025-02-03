package at.hagenberg.fh.wc.model.rescue.sheet.dto

enum class BodyType(val englishDefinition: String, private val germanDefinition: String) {
    CABRIO("Cabrio", "Cabrio"),
    COUPE("Coupé", "Coupé"),
    HATCHBACK("Hatchback", "Schrägheck"),
    MPV("MPV", "Minivan"),
    ROADSTER("Roadster", "Roadster"),
    PICK_UP("Pick-up", "Pick-up"),
    SEDAN("Sedan", "Limousine"),
    STATIONWAGON("Stationwagon", "Kombi"),
    SUV("SUV", "SUV"),
    VAN("Van", "Lieferwagen");

    fun german(): String {
        return germanDefinition
    }

    companion object {
        /**
         * Returns the [BodyType] by matching its [englishDefinition].
         * If no match is found, returns `null`.
         */
        fun fromEnglishDefinition(english: String): BodyType? {
            return entries.firstOrNull { it.englishDefinition.equals(english, ignoreCase = true) }
        }
    }
}