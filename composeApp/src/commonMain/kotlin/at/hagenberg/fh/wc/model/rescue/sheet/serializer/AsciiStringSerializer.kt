package at.hagenberg.fh.wc.model.rescue.sheet.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object AsciiStringSerializer : KSerializer<String> {
    private val ACCENT_MAP = mapOf(
        'À' to 'A', 'Á' to 'A', 'Â' to 'A', 'Ã' to 'A', 'Ä' to 'A', 'Å' to 'A',
        'à' to 'a', 'á' to 'a', 'â' to 'a', 'ã' to 'a', 'ä' to 'a', 'å' to 'a',
        'Ç' to 'C', 'ç' to 'c',
        'È' to 'E', 'É' to 'E', 'Ê' to 'E', 'Ë' to 'E',
        'è' to 'e', 'é' to 'e', 'ê' to 'e', 'ë' to 'e',
        'Ì' to 'I', 'Í' to 'I', 'Î' to 'I', 'Ï' to 'I',
        'ì' to 'i', 'í' to 'i', 'î' to 'i', 'ï' to 'i',
        'Ñ' to 'N', 'ñ' to 'n',
        'Ò' to 'O', 'Ó' to 'O', 'Ô' to 'O', 'Õ' to 'O', 'Ö' to 'O',
        'ò' to 'o', 'ó' to 'o', 'ô' to 'o', 'õ' to 'o', 'ö' to 'o',
        'Ù' to 'U', 'Ú' to 'U', 'Û' to 'U', 'Ü' to 'U',
        'ù' to 'u', 'ú' to 'u', 'û' to 'u', 'ü' to 'u',
        'Ý' to 'Y', 'ý' to 'y', 'ÿ' to 'y'
    )

    /**
     * Removes diacritics from a string by mapping accented characters
     * to their nearest ASCII equivalent.
     */
    fun removeDiacritics(input: String): String {
        val sb = StringBuilder(input.length)
        for (ch in input) {
            sb.append(ACCENT_MAP[ch] ?: ch)
        }
        return sb.toString()
    }

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("AsciiString", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: String) {
        val asciiValue = removeDiacritics(value)
        encoder.encodeString(asciiValue)
    }

    override fun deserialize(decoder: Decoder): String {
        val decoded = decoder.decodeString()
        return removeDiacritics(decoded)
    }
}