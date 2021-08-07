package sillock.icelandicdiscordbot.models.inflectedforms

import sillock.icelandicdiscordbot.models.enums.*

data class AdjectiveForm(override var inflectedString: String,
                         override var grammaticalNumber: GrammaticalNumber?,
                         override var grammaticalForm: GrammaticalForm?,
                         var degree: Degree,
                         var strength: InflectedStrength,
                         var gender: Gender) : InflectedForm(inflectedString, grammaticalNumber, grammaticalForm)