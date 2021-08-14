package sillock.icelandicdiscordbot.mappers

import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.models.enums.*
import sillock.icelandicdiscordbot.models.inflectedforms.VerbForm

@Component
class VerbMapper {

    fun map(formInflectedWordPair: List<Pair<String, String>>): List<VerbForm?> {
        val verbForms = mutableListOf<VerbForm?>()

        var grammaticalVoice: GrammaticalVoice? = null
        var grammaticalMood: GrammaticalMood? = null
        var grammaticalTense: GrammaticalTense? = null
        var grammaticalPerson: GrammaticalPerson? = null
        var grammaticalNumber: GrammaticalNumber? = null
        var clipped = false
        var interrogativeMood = false
        var supine = false
        var grammaticalUsage: GrammaticalUsage = GrammaticalUsage.Personal

        var impersonal = false
        var grammaticalForm: GrammaticalForm? = null

        formInflectedWordPair.forEach{ x ->

            grammaticalUsage = GrammaticalUsage.Personal
            if(x.first.contains("MM")) grammaticalVoice = GrammaticalVoice.MiddleVoice
            if(x.first.contains("GM")) grammaticalVoice = GrammaticalVoice.ActiveVoice

            if(x.first.contains("FH")) grammaticalMood = GrammaticalMood.Indicative
            if(x.first.contains("VH")) grammaticalMood = GrammaticalMood.Subjunctive
            if(x.first.contains("BH")) grammaticalMood = GrammaticalMood.Imperative // Boðháttur

            if(x.first.contains("NT")) grammaticalTense = GrammaticalTense.Present
            if(x.first.contains("ÞT")) grammaticalTense = GrammaticalTense.Past

            if(x.first.contains("1P")) grammaticalPerson = GrammaticalPerson.FirstPerson
            if(x.first.contains("2P")) grammaticalPerson = GrammaticalPerson.SecondPerson
            if(x.first.contains("3P")) grammaticalPerson = GrammaticalPerson.ThirdPerson

            if(x.first.contains("ET")) grammaticalNumber = GrammaticalNumber.Singular
            if(x.first.contains("FT")) grammaticalNumber = GrammaticalNumber.Plural

            if(x.first.contains("ST")) clipped = true
            if(x.first.contains("SP")) interrogativeMood = true
            if(x.first.contains("SAGNB")) supine = true

            if(x.first.contains("OP")) {
                impersonal = true
                if(x.first.contains("ÞGF"))
                {
                    grammaticalUsage = GrammaticalUsage.ImpersonalDative
                }
                if(x.first.contains("það"))
                {
                    grammaticalUsage = GrammaticalUsage.ImpersonalDummy
                }
            }

            if(x.first.contains("EF")) grammaticalForm = GrammaticalForm.Genitive
            if(x.first.contains("ÞF")) grammaticalForm = GrammaticalForm.Accusative
            if(x.first.contains("ÞGF")) grammaticalForm = GrammaticalForm.Dative

            val verbForm = VerbForm(x.second, grammaticalVoice!!, grammaticalMood!!, grammaticalTense!!, grammaticalPerson!!, grammaticalNumber!!, clipped, interrogativeMood, supine, grammaticalUsage)
            verbForms.add(verbForm)
        }

        return verbForms.filterNotNull().sortedWith (compareBy({ it.grammaticalVoice }, {it.grammaticalMood}, {it.grammaticalTense}, {it.grammaticalPerson}, {it.grammaticalNumber}, {it.supine}, {it.interrogativeMood}))
    }
}