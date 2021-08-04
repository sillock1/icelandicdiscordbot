package sillock.icelandicdiscordbot.models.serialisations

import kotlinx.serialization.Serializable

@Serializable
data class InflectionalForm(val g: String,
                            val b: String)
