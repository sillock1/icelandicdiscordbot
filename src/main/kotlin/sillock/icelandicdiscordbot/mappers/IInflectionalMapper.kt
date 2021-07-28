package sillock.icelandicdiscordbot.mappers

import sillock.icelandicdiscordbot.models.enums.InflectionType
import sillock.icelandicdiscordbot.models.imagegeneration.InflectedForm

interface IInflectionalMapper {
    val inflectionType: InflectionType
    fun map (grammaticalForm: String, inflectedWord: String): InflectedForm?
}