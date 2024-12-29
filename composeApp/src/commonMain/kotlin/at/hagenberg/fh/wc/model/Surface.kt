package at.hagenberg.fh.wc.model

enum class Surface {
    SOLID, GRASS, GRAVELY, LOOSE, MUD_OVER_AXLE, MUD_OVER_TYRE, MUD_UP_TO_BODY;

    fun getRollingResistanceCoefficient(): Double {
        return when (this) {
            SOLID -> 1.0 / 25.0
            GRASS -> 1.0 / 7.0
            GRAVELY -> 1.0 / 5.0
            LOOSE -> 1.0 / 4.0
            MUD_OVER_AXLE -> 1.0
            MUD_OVER_TYRE -> 2.0
            MUD_UP_TO_BODY -> 3.0
        }
    }
}