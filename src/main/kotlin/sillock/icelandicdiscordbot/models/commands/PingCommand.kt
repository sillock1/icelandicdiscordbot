package sillock.icelandicdiscordbot.models.commands

import org.javacord.api.entity.message.MessageBuilder
import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption
import org.springframework.stereotype.Component
import java.awt.Color
import java.awt.Font
import java.awt.RenderingHints
import java.awt.image.BufferedImage

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