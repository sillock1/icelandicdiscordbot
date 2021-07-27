package sillock.icelandicdiscordbot.commands

import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionType
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.services.DmiiCoreService

@Component
class HeadwordCommand (private val dmiiCoreService: DmiiCoreService) : ICommand {
    override val name: String
        get() = "headword"
    override val description: String
        get() = "Searches for headword"
    override val options: List<SlashCommandOption>
        get() = listOf(SlashCommandOption.createWithChoices(SlashCommandOptionType.STRING, "word", "Word to search for", true))

    override fun execute(event: SlashCommandInteraction) {
        val wordParam = event.firstOptionStringValue
        val response = dmiiCoreService.getHeadword(wordParam.get())

        //val embed = nounEmbedCreator.create()

        event.channel.get().sendMessage("")
    }
}