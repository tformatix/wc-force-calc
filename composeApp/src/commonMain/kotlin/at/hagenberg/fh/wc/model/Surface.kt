package at.hagenberg.fh.wc.model

enum class Surface(val rollingResidenceCoefficient: Double, private val title: String) {
    SOLID(1.0 / 25.0, "Asphalt, Beton"),
    GRASS(1.0 / 7.0, "Gras, Wiese"),
    GRAVELY(1.0 / 5.0, "Schotter"),
    LOOSE(1.0 / 4.0, "Lockerer Boden, Sand"),
    MUD_OVER_AXLE(1.0, "Über Achse im Schlamm"),
    MUD_OVER_TYRE(2.0, "Über Räder im Schlamm"),
    MUD_UP_TO_BODY(3.0, "Bis Aufbau im Schlamm"),
    RAIL(0.003, "Schiene");

    override fun toString(): String {
        return title
    }
}