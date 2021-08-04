package sillock.icelandicdiscordbot.mappers

import org.springframework.stereotype.Component

@Component
class WordTypeMapper {
    fun map(wordType: String) : String{
        return when (wordType){
            "no"  -> "Noun (Nafnorð)"
            "pfn" -> "Personal Pronouns (Persónufornöfn)"
            "ao"  -> "Adverb (Atviksorð)"
            "so"  -> "Verb (Sagnorð)"
            "fn"  -> "Other Pronouns (Önnur fornöfn)"
            "gr"  -> "The Definite Article (Greinir)"
            "lo"  -> "Adjectives (Lýsingarorð)"
            "rt"  -> "Ordinals (Raðtölur)"
            "to"  -> "Numerals (Töluorð)"
            else  -> ""
        }
    }
}