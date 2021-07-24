package sillock.icelandicdiscordbot.models.commands

//import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption

interface ICommand {
    val name: String
    val description: String
    val options: List<SlashCommandOption>

    fun execute(event: SlashCommandInteraction)
}