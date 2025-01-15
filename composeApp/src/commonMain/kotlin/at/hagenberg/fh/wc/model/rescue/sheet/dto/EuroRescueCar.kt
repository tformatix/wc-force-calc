package at.hagenberg.fh.wc.model.rescue.sheet.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EuroRescueCar(
    @SerialName("build_year_until") val buildYearUntil: String? = null,
    @SerialName("name") val name: String,
    @SerialName("make_id") val makeId: String,
    @SerialName("make_name") val makeName: String,
    @SerialName("model_id") val modelId: String,
    @SerialName("model_name") val modelName: String,
    @SerialName("body_type") val bodyType: String,
    @SerialName("build_year_from") val buildYearFrom: String,
    @SerialName("doors") val doors: String,
    @SerialName("powertrain") val powertrain: String,
    @SerialName("documents") val documents: List<EuroRescueCarDocument>
)
