package sillock.icelandicdiscordbot.models.inflectedforms

import sillock.icelandicdiscordbot.models.enums.GrammaticalDegree
import sillock.icelandicdiscordbot.models.enums.GrammaticalForm
import sillock.icelandicdiscordbot.models.enums.GrammaticalNumber

data class DegreeForm(override var inflectedString: String,
                 override var grammaticalNumber: GrammaticalNumber?,
                 override var grammaticalForm: GrammaticalForm?,
                 var grammaticalDegree: GrammaticalDegree
): InflectedForm(inflectedString, grammaticalNumber, grammaticalForm)