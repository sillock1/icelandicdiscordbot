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

        var degree: Degree? = null
        var gender: Gender? = null
        var number: GrammaticalNumber? = null
        var form: GrammaticalForm? = null
        var strength: InflectedStrength? = null

        formInflectedWordPair.forEach{ x ->

                if (x.first.contains("FSB")){
                    degree = Degree.Positive
                    strength = InflectedStrength.Strong
                }
                if(x.first.contains("FVB")){
                    degree = Degree.Positive
                    strength = InflectedStrength.Weak
                }
                if(x.first.contains("MST")){
                    degree = Degree.Comparative
                    strength = InflectedStrength.Strong
                }
                if(x.first.contains("ESB")){
                    degree = Degree.Superlative
                    strength = InflectedStrength.Strong
                }
                if(x.first.contains("EVB")){
                    degree = Degree.Superlative
                    strength = InflectedStrength.Weak
                }
                if(x.first.contains("KK")) gender = Gender.Male
                if(x.first.contains("KVK")) gender = Gender.Female
                if(x.first.contains("HK")) gender = Gender.Neuter
                if(x.first.contains("ET")) number = GrammaticalNumber.Singular
                if(x.first.contains("FT")) number = GrammaticalNumber.Plural
                if(x.first.contains("NF")) form = GrammaticalForm.Nominative
                if(x.first.contains("ÞF")) form = GrammaticalForm.Accusative
                if(x.first.contains("ÞGF")) form = GrammaticalForm.Dative
                if(x.first.contains("EF")) form = GrammaticalForm.Genitive

            val adjectiveForm = AdjectiveForm(x.second, number, form, degree!!, strength!!, gender!!)
            adjectiveForms.add(adjectiveForm)
        }

        return adjectiveForms.filterNotNull().sortedWith (compareBy({ it.degree }, {it.strength}, {it.gender}, {it.grammaticalForm}))
    }
}