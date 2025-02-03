package at.hagenberg.fh.wc.model.rescue.sheet.dto

import at.hagenberg.fh.wc.model.rescue.sheet.serializer.AsciiStringSerializer
import at.hagenberg.fh.wc.model.rescue.sheet.serializer.BodyTypeSerializer
import at.hagenberg.fh.wc.model.rescue.sheet.serializer.StringToIntSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EuroRescueCar(
    @SerialName("build_year_until") val buildYearUntil: String? = null,
    @SerialName("name") val name: String,
    @SerialName("make_id") val makeId: String,
    @SerialName("make_name") val makeName: String,
    @SerialName("model_id") val modelId: String,
    @SerialName("model_name") @Serializable(with = AsciiStringSerializer::class) val modelName: String,
    @SerialName("body_type") @Serializable(with = BodyTypeSerializer::class) val bodyType: BodyType,
    @SerialName("build_year_from") @Serializable(with = StringToIntSerializer::class) val buildYearFrom: Int,
    @SerialName("doors") val doors: String,
    @SerialName("powertrain") val powertrain: String,
    @SerialName("documents") val documents: List<EuroRescueCarDocument>
)
