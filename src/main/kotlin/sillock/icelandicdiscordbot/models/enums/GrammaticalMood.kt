package sillock.icelandicdiscordbot.models.enums

enum class GrammaticalMood {
    IndicativeMood,
    SubjunctiveMood,
    ImperativeMood;

    override fun toString(): String {
        var name = name.split(Regex("(?=M)")).joinToString(separator = " ") {word -> word.replaceFirstChar { it.lowercase() }}
        name = name.replaceFirstChar { it.uppercase() }
        return name
    }
}