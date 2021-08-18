package sillock.icelandicdiscordbot.mappers

import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.models.enums.GrammaticalGender
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
                    "KK-NFET"   -> {GenderedForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Nominative, GrammaticalGender.Male)}
                    "KVK-NFET"  -> {GenderedForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Nominative, GrammaticalGender.Female)}
                    "HK-NFET"   -> {GenderedForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Nominative, GrammaticalGender.Neuter)}
                    "KK-NFFT"   -> {GenderedForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Nominative, GrammaticalGender.Male)}
                    "KVK-NFFT"  -> {GenderedForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Nominative, GrammaticalGender.Female)}
                    "HK-NFFT"   -> {GenderedForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Nominative, GrammaticalGender.Neuter)}

                    "KK-ÞFET"   -> {GenderedForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Accusative, GrammaticalGender.Male)}
                    "KVK-ÞFET"  -> {GenderedForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Accusative, GrammaticalGender.Female)}
                    "HK-ÞFET"   -> {GenderedForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Accusative, GrammaticalGender.Neuter)}
                    "KK-ÞFFT"   -> {GenderedForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Accusative, GrammaticalGender.Male)}
                    "KVK-ÞFFT"  -> {GenderedForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Accusative, GrammaticalGender.Female)}
                    "HK-ÞFFT"   -> {GenderedForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Accusative, GrammaticalGender.Neuter)}

                    "KK-ÞGFET"  -> {GenderedForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Dative, GrammaticalGender.Male)}
                    "KVK-ÞGFET" -> {GenderedForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Dative, GrammaticalGender.Female)}
                    "HK-ÞGFET"  -> {GenderedForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Dative, GrammaticalGender.Neuter)}
                    "KK-ÞGFFT"  -> {GenderedForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Dative, GrammaticalGender.Male)}
                    "KVK-ÞGFFT" -> {GenderedForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Dative, GrammaticalGender.Female)}
                    "HK-ÞGFFT"  -> {GenderedForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Dative, GrammaticalGender.Neuter)}

                    "KK-EFET"   -> {GenderedForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Genitive, GrammaticalGender.Male)}
                    "KVK-EFET"  -> {GenderedForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Genitive, GrammaticalGender.Female)}
                    "HK-EFET"   -> {GenderedForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Genitive, GrammaticalGender.Neuter)}
                    "KK-EFFT"   -> {GenderedForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Genitive, GrammaticalGender.Male)}
                    "KVK-EFFT"  -> {GenderedForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Genitive, GrammaticalGender.Female)}
                    "HK-EFFT"   -> {GenderedForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Genitive, GrammaticalGender.Neuter)}
                    else -> null
                }
                genderedForms.add(form)
            }
        return genderedForms.filterNotNull().sortedWith (compareBy ({ it.grammaticalNumber }, {it.grammaticalForm}, {it.grammaticalGender}))
    }

}