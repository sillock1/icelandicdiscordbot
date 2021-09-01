package sillock.icelandicdiscordbot.models.serialisations

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WordResult(
    @SerialName("results")
    val resultList: List<WordSearch> = listOf()
)

@Serializable
data class WordSearch(
    @SerialName("flid")
    val wordId: Int,
    @SerialName("fletta")
    val wordName: String
)
