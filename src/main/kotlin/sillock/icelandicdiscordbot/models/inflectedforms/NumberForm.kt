package sillock.icelandicdiscordbot.models.inflectedforms

import sillock.icelandicdiscordbot.models.enums.GrammaticalForm
import sillock.icelandicdiscordbot.models.enums.GrammaticalNumber

data class NumberForm(override var inflectedString: String,
                      override var grammaticalNumber: GrammaticalNumber?,
                      override var grammaticalForm: GrammaticalForm?)
    : InflectedForm(inflectedString, grammaticalNumber, grammaticalForm)