package sillock.icelandicdiscordbot.models.inflectedforms

import sillock.icelandicdiscordbot.models.enums.*

data class AdjectiveForm(override var inflectedString: String,
                         override var grammaticalNumber: GrammaticalNumber?,
                         override var grammaticalForm: GrammaticalForm?,
                         var grammaticalDegree: GrammaticalDegree,
                         var strength: InflectedStrength,
                         var grammaticalGender: GrammaticalGender) : InflectedForm(inflectedString, grammaticalNumber, grammaticalForm)