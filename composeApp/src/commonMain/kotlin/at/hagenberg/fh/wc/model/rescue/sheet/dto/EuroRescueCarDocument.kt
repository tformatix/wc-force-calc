package at.hagenberg.fh.wc.model.rescue.sheet.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EuroRescueCarDocument(
    @SerialName("id") val id: String,
    @SerialName("url") val url: String,
    @SerialName("timestamp") val timestamp: Long,
    @SerialName("language") val language: String,
    @SerialName("type") val type: String
)