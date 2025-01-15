package at.hagenberg.fh.wc.model.rescue.sheet.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EuroRescueRoot(
    @SerialName("Documents")
    val documents: List<EuroRescueCar>,
    @SerialName("_count")
    val count: Int
)
