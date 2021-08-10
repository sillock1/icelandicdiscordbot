package sillock.icelandicdiscordbot.mappers

import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.models.enums.*
import sillock.icelandicdiscordbot.models.inflectedforms.AdjectiveForm
import sillock.icelandicdiscordbot.models.inflectedforms.InflectedForm

@Component
class AdjectiveMapper: IInflectionalMapper {
    override val inflectionType: InflectionType
        get() = InflectionType.Adjective

    override fun map(formInflectedWordPair: List<Pair<String, String>>): List<InflectedForm?> {
        val adjectiveForms = mutableListOf<AdjectiveForm?>()

        var grammaticalDegree: GrammaticalDegree? = null
        var grammaticalGender: GrammaticalGender? = null
        var number: GrammaticalNumber? = null
        var form: GrammaticalForm? = null
        var strength: InflectedStrength? = null

        formInflectedWordPair.forEach{ x ->

                if (x.first.contains("FSB")){
                    grammaticalDegree = GrammaticalDegree.Positive
                    strength = InflectedStrength.Strong
                }
                if(x.first.contains("FVB")){
                    grammaticalDegree = GrammaticalDegree.Positive
                    strength = InflectedStrength.Weak
                }
                if(x.first.contains("MST")){
                    grammaticalDegree = GrammaticalDegree.Comparative
                    strength = InflectedStrength.Strong
                }
                if(x.first.contains("ESB")){
                    grammaticalDegree = GrammaticalDegree.Superlative
                    strength = InflectedStrength.Strong
                }
                if(x.first.contains("EVB")){
                    grammaticalDegree = GrammaticalDegree.Superlative
                    strength = InflectedStrength.Weak
                }
                if(x.first.contains("KK")) grammaticalGender = GrammaticalGender.Male
                if(x.first.contains("KVK")) grammaticalGender = GrammaticalGender.Female
                if(x.first.contains("HK")) grammaticalGender = GrammaticalGender.Neuter
                if(x.first.contains("ET")) number = GrammaticalNumber.Singular
                if(x.first.contains("FT")) number = GrammaticalNumber.Plural
                if(x.first.contains("NF")) form = GrammaticalForm.Nominative
                if(x.first.contains("ÞF")) form = GrammaticalForm.Accusative
                if(x.first.contains("ÞGF")) form = GrammaticalForm.Dative
                if(x.first.contains("EF")) form = GrammaticalForm.Genitive

            val adjectiveForm = AdjectiveForm(x.second, number, form, grammaticalDegree!!, strength!!, grammaticalGender!!)
            adjectiveForms.add(adjectiveForm)
        }

        return adjectiveForms.filterNotNull().sortedWith (compareBy({ it.grammaticalDegree }, {it.strength}, {it.grammaticalGender}, {it.grammaticalForm}))
    }
}