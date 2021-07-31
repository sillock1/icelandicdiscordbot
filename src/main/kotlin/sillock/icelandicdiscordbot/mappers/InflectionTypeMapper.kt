package sillock.icelandicdiscordbot.mappers

import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.models.enums.InflectionType

@Component
class InflectionTypeMapper {
    fun map(wordType: String) : InflectionType?{
        return when (wordType){
            "no" -> InflectionType.Article
            "pfn" -> InflectionType.PersonalPronoun
            "ao" -> InflectionType.Adverb
            "so" -> InflectionType.Verb
            "fn" -> InflectionType.Gender
            "gr" -> InflectionType.Gender
            "lo" -> InflectionType.Gender
            "rt" -> InflectionType.Gender
            "to" -> InflectionType.Gender
            else -> null
        }
    }
}