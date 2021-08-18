package sillock.icelandicdiscordbot.models.serialisations

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InflectionalForm(
    @SerialName("g")
    val grammaticalTagString: String,
    @SerialName("b")
    val inflectedString: String)
