package sillock.icelandicdiscordbot.models

import kotlinx.serialization.Serializable

@Serializable
data class Word(val ord: String,
                val guid: String,
                val ofl_heiti: String,
                val ofl: String,
                val kyn: String,
                val hluti: String = String(),
                val bmyndir: List<InflectionalForm> = listOf()
)
