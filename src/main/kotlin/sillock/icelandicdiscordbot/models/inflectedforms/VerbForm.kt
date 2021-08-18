package sillock.icelandicdiscordbot.models.inflectedforms

import sillock.icelandicdiscordbot.models.enums.*

data class VerbForm(val conjugatedString: String,
                    var grammaticalVoice: GrammaticalVoice?,
                    var grammaticalMood: GrammaticalMood?,
                    var grammaticalTense: GrammaticalTense?,
                    var grammaticalPerson: GrammaticalPerson?,
                    var grammaticalNumber: GrammaticalNumber?,
                    val clipped: Boolean,
                    val interrogativeMood: Boolean,
                    val supine: Boolean,
                    val grammaticalUsage: GrammaticalUsage?)