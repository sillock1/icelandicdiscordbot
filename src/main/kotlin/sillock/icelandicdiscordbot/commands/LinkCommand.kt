package sillock.icelandicdiscordbot.commands

import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionChoice
import org.javacord.api.interaction.SlashCommandOptionType
import org.springframework.stereotype.Component

@Component
class LinkCommand : ICommand{
    override val name: String
        get() = "link"
    override val description: String
        get() = "Creates link for a word"
    override val options: List<SlashCommandOption>
        get() = listOf(
            SlashCommandOption.createWithChoices(
                SlashCommandOptionType.STRING,
                "website",
                "Website to create link for",
                true,
                listOf(
                    SlashCommandOptionChoice.create("Islex", "https://islex.arnastofnun.is/is/leit/"),
                    SlashCommandOptionChoice.create("Íslensk Orðabók", "https://islenskordabok.arnastofnun.is/leit/"),
                    SlashCommandOptionChoice.create("BÍN", "https://bin.arnastofnun.is/leit/")
                )),
            SlashCommandOption.create(SlashCommandOptionType.STRING, "word", "The word to search", true))

    override fun execute(event: SlashCommandInteraction) {
        val websiteParam = event.firstOptionStringValue.get()
        val wordParam = event.secondOptionStringValue.get()
        event.createImmediateResponder().setContent(websiteParam + wordParam).respond()
    }
}