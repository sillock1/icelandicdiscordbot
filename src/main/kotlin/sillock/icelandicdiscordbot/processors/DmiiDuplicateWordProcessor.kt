package sillock.icelandicdiscordbot.processors

import org.javacord.api.entity.message.MessageBuilder
import org.javacord.api.entity.message.component.ActionRow
import org.javacord.api.entity.message.component.Button
import org.javacord.api.interaction.SlashCommandInteraction
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.models.serialisations.DmiiWord

@Component
class DmiiDuplicateWordProcessor {
    fun response(event: SlashCommandInteraction, response: List<DmiiWord>){
        val msgBuilder = MessageBuilder()
        if(response.isNotEmpty())
            msgBuilder.setContent("Here's what I could find")

        for(word in response){
            msgBuilder.addComponents(
                ActionRow.of(
                    Button.success(word.guid, word.baseWordForm)
                )
            )
        }
        msgBuilder.send(event.channel.get())
    }
}