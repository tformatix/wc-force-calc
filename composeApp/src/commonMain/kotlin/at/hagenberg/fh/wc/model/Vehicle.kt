package at.hagenberg.fh.wc.model

enum class Vehicle(val weight: Int?, private val title: String) {
    CAR(15, "PKW"),
    SUV(17, "Van/Kleinbus/SUV"),
    OFF_ROAD_CAR(25, "Geländewagen"),
    TRUCK_2_AXIS(180, "LKW 2 Achsen"),
    TRUCK_3_AXIS(260, "LKW 3 Achsen"),
    TRUCK_4_AXIS(320, "LKW 4 Achsen"),
    CUSTOM(null, "Benutzerdefiniert");

    override fun toString(): String {
        return title
    }
}