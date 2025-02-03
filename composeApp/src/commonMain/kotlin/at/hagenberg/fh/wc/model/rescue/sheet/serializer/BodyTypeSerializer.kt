package at.hagenberg.fh.wc.model.rescue.sheet.serializer

import at.hagenberg.fh.wc.model.rescue.sheet.dto.BodyType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object BodyTypeSerializer : KSerializer<BodyType> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("BodyType", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: BodyType) {
        encoder.encodeString(value.englishDefinition)
    }

    override fun deserialize(decoder: Decoder): BodyType {
        val decoded = decoder.decodeString()
        return BodyType.fromEnglishDefinition(decoded)
            ?: throw IllegalArgumentException("Unknown BodyType english definition: $decoded")
    }
}