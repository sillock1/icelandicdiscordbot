package sillock.icelandicdiscordbot.models.serialisations

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WordData(
    @SerialName("flid")
    val wordId: Int,
    @SerialName("fletta")
    val word: String,
    @SerialName("items")
    val wordItems: List<WordItems> = listOf()
)

@Serializable
data class WordItems(
    @SerialName("itid")
    val itemId: Int,
    @SerialName("texti")
    val text: String,
    @SerialName("teg")
    val itemTag: String
)