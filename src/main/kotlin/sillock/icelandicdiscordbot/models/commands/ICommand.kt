package sillock.icelandicdiscordbot.models.commands

import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption

interface ICommand {
    val name: String
    val description: String
    val options: List<SlashCommandOption>

    fun execute(event: SlashCommandInteraction)
}