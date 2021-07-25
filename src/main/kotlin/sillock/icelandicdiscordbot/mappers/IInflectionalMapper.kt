package sillock.icelandicdiscordbot.mappers

import sillock.icelandicdiscordbot.models.embedmodels.InflectedForm

interface IInflectionalMapper {
    fun map (grammaticalForm: String, inflectedWord: String): InflectedForm?
}