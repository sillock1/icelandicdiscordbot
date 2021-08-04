package sillock.icelandicdiscordbot.models.serialisations

import kotlinx.serialization.Serializable
import sillock.icelandicdiscordbot.models.serialisations.InflectionalForm

@Serializable
data class Word(val ord: String,
                val guid: String,
                val ofl_heiti: String,
                val ofl: String,
                val kyn: String,
                val hluti: String = String(),
                val bmyndir: List<InflectionalForm> = listOf()
)
