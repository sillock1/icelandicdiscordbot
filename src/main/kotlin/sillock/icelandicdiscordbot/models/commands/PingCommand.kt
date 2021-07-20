package sillock.icelandicdiscordbot.models.commands

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import org.springframework.stereotype.Component

@Component
class PingCommand: ICommand {
    override val name: String
        get() = "ping"
    override val description: String
        get() = "Pings the bot"
    override val options: List<CommandOption>
        get() = listOf()

    override fun execute(event: SlashCommandEvent) {
        val time = System.currentTimeMillis()
        event.reply("Pong!").setEphemeral(false)
            .flatMap { event.hook.editOriginalFormat("Pong: %d ms", System.currentTimeMillis() - time)}.queue()
    }
}