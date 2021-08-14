package sillock.icelandicdiscordbot.models.serialisations

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import sillock.icelandicdiscordbot.models.serialisations.InflectionalForm
import java.io.Serial

@Serializable
data class Word(
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
    @SerialName("bmyndir")
    val inflectionalFormList: List<InflectionalForm> = listOf()
)
