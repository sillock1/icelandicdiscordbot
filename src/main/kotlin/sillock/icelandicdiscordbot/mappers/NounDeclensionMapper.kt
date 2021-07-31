package sillock.icelandicdiscordbot.mappers

import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.models.inflectedforms.NounDeclensionForm
import sillock.icelandicdiscordbot.models.enums.GrammaticalForm
import sillock.icelandicdiscordbot.models.enums.GrammaticalNumber
import sillock.icelandicdiscordbot.models.enums.InflectionType

@Component
class NounDeclensionMapper : IInflectionalMapper {
    override val inflectionType: InflectionType
        get() = InflectionType.Article
    override fun map(grammaticalForm: String, inflectedWord: String) : NounDeclensionForm?{
        return when (grammaticalForm){
            "NFET"    -> {NounDeclensionForm(inflectedWord, GrammaticalNumber.Singular, GrammaticalForm.Nominative, false)}
            "NFETgr"  -> {NounDeclensionForm(inflectedWord, GrammaticalNumber.Singular, GrammaticalForm.Nominative, true)}
            "NFFT"    -> {NounDeclensionForm(inflectedWord, GrammaticalNumber.Plural, GrammaticalForm.Nominative, false)}
            "NFFTgr"  -> {NounDeclensionForm(inflectedWord, GrammaticalNumber.Plural, GrammaticalForm.Nominative, true)}
            "ÞFET"    -> {NounDeclensionForm(inflectedWord, GrammaticalNumber.Singular, GrammaticalForm.Accusative, false)}
            "ÞFETgr"  -> {NounDeclensionForm(inflectedWord, GrammaticalNumber.Singular, GrammaticalForm.Accusative, true)}
            "ÞFFT"    -> {NounDeclensionForm(inflectedWord, GrammaticalNumber.Plural, GrammaticalForm.Accusative, false)}
            "ÞFFTgr"  -> {NounDeclensionForm(inflectedWord, GrammaticalNumber.Plural, GrammaticalForm.Accusative, true)}
            "ÞGFET"   -> {NounDeclensionForm(inflectedWord, GrammaticalNumber.Singular, GrammaticalForm.Dative, false)}
            "ÞGFETgr" -> {NounDeclensionForm(inflectedWord, GrammaticalNumber.Singular, GrammaticalForm.Dative, true)}
            "ÞGFFT"   -> {NounDeclensionForm(inflectedWord, GrammaticalNumber.Plural, GrammaticalForm.Dative, false)}
            "ÞGFFTgr" -> {NounDeclensionForm(inflectedWord, GrammaticalNumber.Plural, GrammaticalForm.Dative, true)}
            "EFET"    -> {NounDeclensionForm(inflectedWord, GrammaticalNumber.Singular, GrammaticalForm.Genitive, false)}
            "EFETgr"  -> {NounDeclensionForm(inflectedWord, GrammaticalNumber.Singular, GrammaticalForm.Genitive, true)}
            "EFFT"    -> {NounDeclensionForm(inflectedWord, GrammaticalNumber.Plural, GrammaticalForm.Genitive, false)}
            "EFFTgr"  -> {NounDeclensionForm(inflectedWord, GrammaticalNumber.Plural, GrammaticalForm.Genitive, true)}
            else      -> null
        }
    }
}