package sillock.icelandicdiscordbot.models.commands

//import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
//import net.dv8tion.jda.api.interactions.commands.OptionType
import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionType
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.DmiiCoreService

@Component
class ConjugateCommand(private val dmiiCoreService: DmiiCoreService): ICommand {
    override val name: String
        get() = "conjugate"
    override val description: String
        get() = "Finds conjugations of a verb"
    override val options: List<SlashCommandOption>
        get() = listOf(SlashCommandOption.createWithChoices(SlashCommandOptionType.STRING,"verb", "Verb to conjugate", true))

    override fun execute(event: SlashCommandInteraction) {
        val wordParam = event.firstOptionStringValue
        val response = dmiiCoreService.getVerbConjugation(wordParam.get())
        event.channel.get().sendMessage(response.toString())
    }
}