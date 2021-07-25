package sillock.icelandicdiscordbot.models.commands

import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption
import org.springframework.stereotype.Component

@Component
class PingCommand: ICommand {
    override val name: String
        get() = "ping"
    override val description: String
        get() = "Pings the bot"
    override val options: List<SlashCommandOption>
        get() = listOf()

    override fun execute(event: SlashCommandInteraction) {
        val time = System.currentTimeMillis()
        event.createImmediateResponder().setContent("Pong: ${System.currentTimeMillis() - time} ms").respond()
    }
}