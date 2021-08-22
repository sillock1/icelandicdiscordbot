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
import kotlin.math.min

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
        val msgBuilder = MessageBuilder().setContent("Here's the top 5 results I could find")
        for(word in response.subList(0, min(5, response.size))){
            val wordClass = wordTypeMapper.map(word.shortHandWordClass)
            msgBuilder.addComponents(ActionRow.of(Button.secondary(word.guid, "${word.baseWordForm}  $wordClass")))
        }
        msgBuilder.send(event.channel.get())
    }
}