package sillock.icelandicdiscordbot.commands

import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionType
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.services.DmiiCoreService
import java.util.*

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