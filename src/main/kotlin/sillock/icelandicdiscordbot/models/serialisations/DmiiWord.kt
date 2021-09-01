package sillock.icelandicdiscordbot.models.serialisations

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DmiiWord(
    @SerialName("ord")
    val baseWordForm: String,
    @SerialName("guid")
    val guid: String,
    @SerialName("ofl_heiti")
    val wordClass: String,
    @SerialName("ofl")
    val shortHandWordClass: String,
    @SerialName("kyn")
    val nounGender: String,
    @SerialName("hluti")
    val semanticClassification: String? = null,
    @SerialName("bmyndir")
    val inflectionalFormList: List<InflectionalForm> = listOf()
)
