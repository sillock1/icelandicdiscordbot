package sillock.icelandicdiscordbot.models.inflectedforms

import sillock.icelandicdiscordbot.models.enums.*

data class VerbForm(var grammaticalVoice: GrammaticalVoice,
                    var grammaticalMood: GrammaticalMood,
                    var grammaticalTense: GrammaticalTense,
                    var grammaticalPerson: GrammaticalPerson,
                    var grammaticalNumber: GrammaticalNumber,
                    val clipped: Boolean,
                    val interrogativeMood: Boolean)