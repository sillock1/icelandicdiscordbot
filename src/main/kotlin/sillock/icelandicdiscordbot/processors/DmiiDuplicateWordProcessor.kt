package sillock.icelandicdiscordbot.processors

import org.javacord.api.entity.message.MessageBuilder
import org.javacord.api.entity.message.component.ActionRow
import org.javacord.api.entity.message.component.Button
import org.javacord.api.interaction.SlashCommandInteraction
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.models.Word

@Component
class DmiiDuplicateWordProcessor {
    fun response(event: SlashCommandInteraction, response: List<Word>){
        event.createImmediateResponder().setContent("Sorry I couldn't find the exact word you were looking for...").respond()
        var msgBuilder = MessageBuilder().setContent("Here's what I could find")
        for(word in response){
            msgBuilder.addComponents(
                ActionRow.of(
                    Button.success(word.guid, word.ord)
                )
            )
        }
        msgBuilder.send(event.channel.get())
    }
}