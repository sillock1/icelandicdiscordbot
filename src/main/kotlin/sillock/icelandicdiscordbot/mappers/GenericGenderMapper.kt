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

    override fun map(grammaticalForm: String, inflectedWord: String): GenderedForm? {
        return when(grammaticalForm){
            "HK-EFET"   -> {GenderedForm(inflectedWord, GrammaticalNumber.Singular, GrammaticalForm.Genitive, Gender.Neuter)}
            "HK-NFFT"   -> {GenderedForm(inflectedWord, GrammaticalNumber.Plural, GrammaticalForm.Nominative, Gender.Neuter)}
            "KK-NFET"   -> {GenderedForm(inflectedWord, GrammaticalNumber.Singular, GrammaticalForm.Nominative, Gender.Male)}
            "KK-ÞFET"   -> {GenderedForm(inflectedWord, GrammaticalNumber.Singular, GrammaticalForm.Accusative, Gender.Male)}
            "KK-ÞGFET"  -> {GenderedForm(inflectedWord, GrammaticalNumber.Singular, GrammaticalForm.Dative, Gender.Male)}
            "KK-EFET"   -> {GenderedForm(inflectedWord, GrammaticalNumber.Singular, GrammaticalForm.Genitive, Gender.Male)}
            "KK-NFFT"   -> {GenderedForm(inflectedWord, GrammaticalNumber.Plural, GrammaticalForm.Nominative, Gender.Male)}
            "KK-ÞFFT"   -> {GenderedForm(inflectedWord, GrammaticalNumber.Plural, GrammaticalForm.Accusative, Gender.Male)}
            "KK-ÞGFFT"  -> {GenderedForm(inflectedWord, GrammaticalNumber.Plural, GrammaticalForm.Dative, Gender.Male)}
            "KK-EFFT"   -> {GenderedForm(inflectedWord, GrammaticalNumber.Plural, GrammaticalForm.Genitive, Gender.Male)}
            "KVK-NFET"  -> {GenderedForm(inflectedWord, GrammaticalNumber.Singular, GrammaticalForm.Nominative, Gender.Female)}
            "KVK-ÞFET"  -> {GenderedForm(inflectedWord, GrammaticalNumber.Singular, GrammaticalForm.Accusative, Gender.Female)}
            "KVK-ÞGFET" -> {GenderedForm(inflectedWord, GrammaticalNumber.Singular, GrammaticalForm.Dative, Gender.Female)}
            "KVK-EFET"  -> {GenderedForm(inflectedWord, GrammaticalNumber.Singular, GrammaticalForm.Genitive, Gender.Female)}
            "KVK-NFFT"  -> {GenderedForm(inflectedWord, GrammaticalNumber.Plural, GrammaticalForm.Nominative, Gender.Female)}
            "KVK-ÞFFT"  -> {GenderedForm(inflectedWord, GrammaticalNumber.Plural, GrammaticalForm.Accusative, Gender.Female)}
            "KVK-ÞGFFT" -> {GenderedForm(inflectedWord, GrammaticalNumber.Plural, GrammaticalForm.Dative, Gender.Female)}
            "KVK-EFFT"  -> {GenderedForm(inflectedWord, GrammaticalNumber.Plural, GrammaticalForm.Genitive, Gender.Female)}
            "HK-NFET"   -> {GenderedForm(inflectedWord, GrammaticalNumber.Singular, GrammaticalForm.Nominative, Gender.Neuter)}
            "HK-ÞFET"   -> {GenderedForm(inflectedWord, GrammaticalNumber.Singular, GrammaticalForm.Accusative, Gender.Neuter)}
            "HK-ÞGFET"  -> {GenderedForm(inflectedWord, GrammaticalNumber.Singular, GrammaticalForm.Dative, Gender.Neuter)}
            "HK-ÞFFT"   -> {GenderedForm(inflectedWord, GrammaticalNumber.Plural, GrammaticalForm.Accusative, Gender.Neuter)}
            "HK-ÞGFFT"  -> {GenderedForm(inflectedWord, GrammaticalNumber.Plural, GrammaticalForm.Dative, Gender.Neuter)}
            "HK-EFFT"   -> {GenderedForm(inflectedWord, GrammaticalNumber.Plural, GrammaticalForm.Genitive, Gender.Neuter)}
            else -> null
        }
    }

}