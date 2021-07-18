package sillock.icelandicdiscordbot.models.commands

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent

interface ICommand {
    val name: String
    val description: String
    val options: List<CommandOption>

    fun execute(event: SlashCommandEvent)
}