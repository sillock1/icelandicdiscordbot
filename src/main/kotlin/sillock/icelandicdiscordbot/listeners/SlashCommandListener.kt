package sillock.icelandicdiscordbot.listeners

//import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
//import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.javacord.api.event.interaction.SlashCommandCreateEvent
import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.listener.interaction.SlashCommandCreateListener
import org.javacord.api.listener.message.MessageCreateListener
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.DmiiCoreService
import sillock.icelandicdiscordbot.factories.CommandFactory

@Component
class SlashCommandListener(
    private val commandFactory: CommandFactory
) : SlashCommandCreateListener {

    override fun onSlashCommandCreate(event: SlashCommandCreateEvent?) {
        var command = commandFactory.getCommandByName(event!!.slashCommandInteraction.commandName)
        command?.execute(event.slashCommandInteraction)
    }
}