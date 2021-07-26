package sillock.icelandicdiscordbot.mappers

import sillock.icelandicdiscordbot.models.imagegeneration.InflectedForm

interface IInflectionalMapper {
    fun map (grammaticalForm: String, inflectedWord: String): InflectedForm?
}