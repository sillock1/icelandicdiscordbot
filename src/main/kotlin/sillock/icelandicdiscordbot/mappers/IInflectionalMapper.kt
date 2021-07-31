package sillock.icelandicdiscordbot.mappers

import sillock.icelandicdiscordbot.models.enums.InflectionType
import sillock.icelandicdiscordbot.models.inflectedforms.InflectedForm

interface IInflectionalMapper {
    val inflectionType: InflectionType
    fun map (grammaticalForm: String, inflectedWord: String): InflectedForm?
}