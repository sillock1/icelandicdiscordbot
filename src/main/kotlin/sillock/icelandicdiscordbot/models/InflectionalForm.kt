package sillock.icelandicdiscordbot.models

import kotlinx.serialization.Serializable

@Serializable
data class InflectionalForm(val g: String,
                            val b: String)
