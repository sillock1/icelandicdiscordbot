package sillock.icelandicdiscordbot.listeners

import org.javacord.api.event.interaction.SlashCommandCreateEvent
import org.javacord.api.listener.interaction.SlashCommandCreateListener
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.factories.CommandFactory

@Component
class SlashCommandListener(private val commandFactory: CommandFactory) : SlashCommandCreateListener {

    override fun onSlashCommandCreate(event: SlashCommandCreateEvent?) {
        val command = commandFactory.getCommandByName(event!!.slashCommandInteraction.commandName)
        command?.execute(event.slashCommandInteraction)
    }
}