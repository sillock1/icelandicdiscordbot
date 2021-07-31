package sillock.icelandicdiscordbot.mappers

import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.models.inflectedforms.NounDeclensionForm
import sillock.icelandicdiscordbot.models.enums.GrammaticalForm
import sillock.icelandicdiscordbot.models.enums.GrammaticalNumber
import sillock.icelandicdiscordbot.models.enums.InflectionType
import sillock.icelandicdiscordbot.models.inflectedforms.GenderedForm

@Component
class NounDeclensionMapper : IInflectionalMapper {
    override val inflectionType: InflectionType
        get() = InflectionType.Article
    override fun map(formInflectedWordPair: List<Pair<String, String>>) : List<NounDeclensionForm?>{
        val nounForms = mutableListOf<NounDeclensionForm?>()
        formInflectedWordPair.forEach { pair ->
            val form = when(pair.first){
            "NFET"    -> {NounDeclensionForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Nominative, false)}
            "NFETgr"  -> {NounDeclensionForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Nominative, true)}
            "NFFT"    -> {NounDeclensionForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Nominative, false)}
            "NFFTgr"  -> {NounDeclensionForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Nominative, true)}
            "ÞFET"    -> {NounDeclensionForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Accusative, false)}
            "ÞFETgr"  -> {NounDeclensionForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Accusative, true)}
            "ÞFFT"    -> {NounDeclensionForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Accusative, false)}
            "ÞFFTgr"  -> {NounDeclensionForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Accusative, true)}
            "ÞGFET"   -> {NounDeclensionForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Dative, false)}
            "ÞGFETgr" -> {NounDeclensionForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Dative, true)}
            "ÞGFFT"   -> {NounDeclensionForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Dative, false)}
            "ÞGFFTgr" -> {NounDeclensionForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Dative, true)}
            "EFET"    -> {NounDeclensionForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Genitive, false)}
            "EFETgr"  -> {NounDeclensionForm(pair.second, GrammaticalNumber.Singular, GrammaticalForm.Genitive, true)}
            "EFFT"    -> {NounDeclensionForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Genitive, false)}
            "EFFTgr"  -> {NounDeclensionForm(pair.second, GrammaticalNumber.Plural, GrammaticalForm.Genitive, true)}
            else      -> null
        }
            nounForms.add(form)
    }
        return nounForms.filterNotNull().sortedWith (compareBy ({ it.grammaticalNumber }, {it.grammaticalForm}, {it.withArticle}))
    }
}