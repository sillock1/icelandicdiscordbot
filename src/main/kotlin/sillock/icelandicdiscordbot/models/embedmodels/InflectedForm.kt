package sillock.icelandicdiscordbot.models.embedmodels

import sillock.icelandicdiscordbot.models.enums.GrammaticalForm
import sillock.icelandicdiscordbot.models.enums.GrammaticalNumber

abstract class InflectedForm(
    open var inflectedString: String,
    open var grammaticalNumber: GrammaticalNumber,
    open var grammaticalForm: GrammaticalForm)