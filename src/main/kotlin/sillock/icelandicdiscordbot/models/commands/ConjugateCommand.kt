package sillock.icelandicdiscordbot.models.commands

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.DmiiCoreService

@Component
class ConjugateCommand(private val dmiiCoreService: DmiiCoreService): ICommand {
    override val name: String
        get() = "conjugate"
    override val description: String
        get() = "Finds conjugations of a verb"
    override val options: List<CommandOption>
        get() = listOf(CommandOption("verb", "Verb to conjugate", OptionType.STRING, true, listOf()))

    override fun execute(event: SlashCommandEvent) {
        val wordParam = event.getOption("verb")
        val response = dmiiCoreService.getVerbConjugation(wordParam!!.asString)
        event.reply(response.toString()).queue()
    }
}