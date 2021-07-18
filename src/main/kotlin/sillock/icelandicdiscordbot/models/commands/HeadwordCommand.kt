package sillock.icelandicdiscordbot.models.commands

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import org.apache.commons.collections4.IterableUtils.forEach
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.DmiiCoreService

@Component
class HeadwordCommand (private val dmiiCoreService: DmiiCoreService) : ICommand {
    override val name: String
        get() = "headword"
    override val description: String
        get() = "Searches for headword"
    override val options: List<CommandOption>
        get() = listOf(CommandOption("word", "Word to search for", OptionType.STRING, true, listOf()))

    override fun execute(event: SlashCommandEvent) {
        val wordParam = event.getOption("word")
        val response = dmiiCoreService.getHeadword(wordParam!!.asString)

        event.reply(response.toString()).queue()
    }
}