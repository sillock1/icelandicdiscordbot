package sillock.icelandicdiscordbot.models.imagegeneration

import sillock.icelandicdiscordbot.models.enums.GrammaticalForm
import sillock.icelandicdiscordbot.models.enums.GrammaticalNumber

data class NounDeclensionForm(override var inflectedString: String,
                         override var grammaticalNumber: GrammaticalNumber,
                         override var grammaticalForm: GrammaticalForm,
                         var withArticle: Boolean): InflectedForm(inflectedString, grammaticalNumber, grammaticalForm)