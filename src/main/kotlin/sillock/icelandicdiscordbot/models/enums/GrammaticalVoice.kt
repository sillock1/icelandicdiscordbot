package sillock.icelandicdiscordbot.models.enums

enum class GrammaticalVoice {
    ActiveVoice,
    MiddleVoice;

    override fun toString(): String {
        var name = name.split(Regex("(?=V)")).joinToString(separator = " ") {word -> word.replaceFirstChar { it.lowercase() }}
        name = name.replaceFirstChar { it.uppercase() }
        return name
    }
}