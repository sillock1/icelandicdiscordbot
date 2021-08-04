package sillock.icelandicdiscordbot.mappers

import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.models.enums.Gender
import sillock.icelandicdiscordbot.models.enums.GrammaticalForm
import sillock.icelandicdiscordbot.models.enums.GrammaticalNumber
import sillock.icelandicdiscordbot.models.enums.InflectionType
import sillock.icelandicdiscordbot.models.inflectedforms.GenderedForm

@Component
class GenericGenderMapper : IInflectionalMapper{
    override val inflectionType: InflectionType
        get() = InflectionType.Gender

    override fun map(formInflectedWordPair: List<Pair<String, String>>): List<GenderedForm?> {
        val genderedForms = mutableListOf<GenderedForm?>()
            formInflectedWordPair.forEach { pair ->
                val form = when(pair.first){
                    "KK-NFET"   -> {GenderedForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Nominative, Gender.Male)}
                    "KVK-NFET"  -> {GenderedForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Nominative, Gender.Female)}
                    "HK-NFET"   -> {GenderedForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Nominative, Gender.Neuter)}
                    "KK-NFFT"   -> {GenderedForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Nominative, Gender.Male)}
                    "KVK-NFFT"  -> {GenderedForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Nominative, Gender.Female)}
                    "HK-NFFT"   -> {GenderedForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Nominative, Gender.Neuter)}

                    "KK-ÞFET"   -> {GenderedForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Accusative, Gender.Male)}
                    "KVK-ÞFET"  -> {GenderedForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Accusative, Gender.Female)}
                    "HK-ÞFET"   -> {GenderedForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Accusative, Gender.Neuter)}
                    "KK-ÞFFT"   -> {GenderedForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Accusative, Gender.Male)}
                    "KVK-ÞFFT"  -> {GenderedForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Accusative, Gender.Female)}
                    "HK-ÞFFT"   -> {GenderedForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Accusative, Gender.Neuter)}

                    "KK-ÞGFET"  -> {GenderedForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Dative, Gender.Male)}
                    "KVK-ÞGFET" -> {GenderedForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Dative, Gender.Female)}
                    "HK-ÞGFET"  -> {GenderedForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Dative, Gender.Neuter)}
                    "KK-ÞGFFT"  -> {GenderedForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Dative, Gender.Male)}
                    "KVK-ÞGFFT" -> {GenderedForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Dative, Gender.Female)}
                    "HK-ÞGFFT"  -> {GenderedForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Dative, Gender.Neuter)}

                    "KK-EFET"   -> {GenderedForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Genitive, Gender.Male)}
                    "KVK-EFET"  -> {GenderedForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Genitive, Gender.Female)}
                    "HK-EFET"   -> {GenderedForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Genitive, Gender.Neuter)}
                    "KK-EFFT"   -> {GenderedForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Genitive, Gender.Male)}
                    "KVK-EFFT"  -> {GenderedForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Genitive, Gender.Female)}
                    "HK-EFFT"   -> {GenderedForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Genitive, Gender.Neuter)}
                    else -> null
                }
                genderedForms.add(form)
            }
        return genderedForms.filterNotNull().sortedWith (compareBy ({ it.grammaticalNumber }, {it.grammaticalForm}, {it.gender}))
    }

}