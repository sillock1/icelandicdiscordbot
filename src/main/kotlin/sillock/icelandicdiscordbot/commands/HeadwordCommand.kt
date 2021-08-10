package sillock.icelandicdiscordbot.commands

import org.javacord.api.entity.message.MessageBuilder
import org.javacord.api.entity.message.component.ActionRow
import org.javacord.api.entity.message.component.Button
import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionType
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.mappers.WordTypeMapper
import sillock.icelandicdiscordbot.services.DmiiCoreService
import java.util.*

@Component
class HeadwordCommand (private val dmiiCoreService: DmiiCoreService,
                       private val wordTypeMapper: WordTypeMapper) : ICommand {
    override val name: String
        get() = "headword"
    override val description: String
        get() = "Searches for headword"
    override val options: List<SlashCommandOption>
        get() = listOf(SlashCommandOption.createWithChoices(SlashCommandOptionType.STRING, "word", "Word to search for", true))

    override fun execute(event: SlashCommandInteraction) {
        val wordParam = event.firstOptionStringValue
        val response = dmiiCoreService.getHeadword(wordParam.get())

        event.createImmediateResponder().setContent("Here's a list of words I found for you").respond()
        val msgBuilder = MessageBuilder().setContent("I found ${response.count()} words")
        for(word in response){
            val wordClass = wordTypeMapper.map(word.ofl)
            msgBuilder.addComponents(ActionRow.of(Button.secondary(word.guid, "${word.ord}  $wordClass")))
        }
        msgBuilder.send(event.channel.get())
    }
}