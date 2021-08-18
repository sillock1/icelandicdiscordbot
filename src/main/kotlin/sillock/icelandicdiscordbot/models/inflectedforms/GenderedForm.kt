package sillock.icelandicdiscordbot.models.inflectedforms

import sillock.icelandicdiscordbot.models.enums.GrammaticalGender
import sillock.icelandicdiscordbot.models.enums.GrammaticalForm
import sillock.icelandicdiscordbot.models.enums.GrammaticalNumber

data class GenderedForm(override var inflectedString: String,
                        override var grammaticalNumber: GrammaticalNumber?,
                        override var grammaticalForm: GrammaticalForm?,
                        var grammaticalGender: GrammaticalGender): InflectedForm(inflectedString, grammaticalNumber, grammaticalForm)