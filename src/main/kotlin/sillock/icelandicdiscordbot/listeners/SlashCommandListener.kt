package sillock.icelandicdiscordbot.listeners

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.DmiiCoreService
import sillock.icelandicdiscordbot.factories.CommandFactory

@Component
class SlashCommandListener(
    private val dmiiCoreService: DmiiCoreService,
    private val commandFactory: CommandFactory
) : ListenerAdapter() {

    override fun onSlashCommand(event: SlashCommandEvent) {

        var command = commandFactory.getCommandByName(event.name)
        command?.execute(event)

        if(!event.name.equals("ping")) return
        val time = System.currentTimeMillis()
        event.reply("Pong!").setEphemeral(false)
            .flatMap { event.hook.editOriginalFormat("Pong: %d ms", System.currentTimeMillis() - time)}.queue()
    }

}