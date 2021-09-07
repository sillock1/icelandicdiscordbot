package sillock.icelandicdiscordbot.models.enums

enum class GrammaticalUsage {
    Personal,
    ImpersonalDative,
    ImpersonalDummy;

    override fun toString(): String {
        var name = name.split(Regex("(?=D)")).joinToString(separator = " ") {word -> word.replaceFirstChar { it.lowercase() }}
        name = name.replaceFirstChar { it.uppercase() }
        return "$name Usage"
    }
}