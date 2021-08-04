package sillock.icelandicdiscordbot.mappers

import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.models.enums.GrammaticalForm
import sillock.icelandicdiscordbot.models.enums.GrammaticalNumber
import sillock.icelandicdiscordbot.models.enums.InflectionType
import sillock.icelandicdiscordbot.models.inflectedforms.NumberForm

@Component
class PersonalPronounMapper: IInflectionalMapper{
    override val inflectionType: InflectionType
        get() = InflectionType.PersonalPronoun

    override fun map(formInflectedWordPair: List<Pair<String, String>>): List<NumberForm?> {
        val personalPronounForm = mutableListOf<NumberForm?>()
        formInflectedWordPair.forEach { pair ->
            val form = when(pair.first) {
                "NFET"  -> NumberForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Nominative)
                "ÞFET"  -> NumberForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Accusative)
                "ÞGFET" -> NumberForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Dative)
                "EFET"  -> NumberForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Genitive)
                "NFFT"  -> NumberForm(pair.second, GrammaticalNumber.Plural,  GrammaticalForm.Nominative)
                "ÞFFT"  -> NumberForm(pair.second, GrammaticalNumber.Plural,  GrammaticalForm.Accusative)
                "ÞGFFT" -> NumberForm(pair.second, GrammaticalNumber.Plural,  GrammaticalForm.Dative)
                "EFFT"  -> NumberForm(pair.second, GrammaticalNumber.Plural,  GrammaticalForm.Genitive)
                else    -> null
            }
            personalPronounForm.add(form)
        }
        return personalPronounForm.filterNotNull().sortedWith(compareBy({it.grammaticalForm}, {it.grammaticalNumber}))
    }

}