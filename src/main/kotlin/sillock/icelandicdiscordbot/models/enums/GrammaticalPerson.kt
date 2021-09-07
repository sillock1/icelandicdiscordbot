package sillock.icelandicdiscordbot.models.enums

enum class GrammaticalPerson {
    FirstPerson,
    SecondPerson,
    ThirdPerson;

    override fun toString(): String {
        var name = name.split(Regex("(?=P)")).joinToString(separator = " ") {word -> word.replaceFirstChar { it.lowercase() }}
        name = name.replaceFirstChar { it.uppercase() }
        return name
    }
}