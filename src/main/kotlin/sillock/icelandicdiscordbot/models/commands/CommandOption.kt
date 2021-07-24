package sillock.icelandicdiscordbot.models.commands

//import net.dv8tion.jda.api.interactions.commands.Command
//import net.dv8tion.jda.api.interactions.commands.OptionType
import org.javacord.api.interaction.SlashCommandOptionChoice
import org.javacord.api.interaction.SlashCommandOptionType

data class CommandOption (
        val name: String,
        val description: String,
        val type: SlashCommandOptionType,
        val required: Boolean = false,
        val choices: List<SlashCommandOptionChoice> = listOf())