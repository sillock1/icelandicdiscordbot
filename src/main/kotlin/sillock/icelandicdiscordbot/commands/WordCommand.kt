package sillock.icelandicdiscordbot.commands

import org.javacord.api.entity.message.component.ActionRow
import org.javacord.api.entity.message.component.Button
import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionType
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.helpers.Failure
import sillock.icelandicdiscordbot.helpers.Success
import sillock.icelandicdiscordbot.mappers.WordTypeMapper
import sillock.icelandicdiscordbot.services.DmiiCoreService
import kotlin.math.min

@Component
class WordCommand (private val dmiiCoreService: DmiiCoreService,
                   private val wordTypeMapper: WordTypeMapper) : ICommand {
    override val name: String
        get() = "word"
    override val description: String
        get() = "Searches for headword"
    override val options: List<SlashCommandOption>
        get() = listOf(SlashCommandOption.createWithChoices(SlashCommandOptionType.STRING, "word", "Word to search for", true))

    override fun execute(event: SlashCommandInteraction) {
        event.respondLater().join()
        val msgBuilder = event.createFollowupMessageBuilder()
        val wordParam = event.firstOptionStringValue

        when(val response = dmiiCoreService.getHeadword(wordParam.get())){
            is Failure -> {msgBuilder.setContent("Cannot find word")}
            is Success -> {
                msgBuilder.setContent("Top 5 results")
                for(word in response.value.subList(0, min(5, response.value.size))){
                    val wordClass = wordTypeMapper.map(word.shortHandWordClass)
                    msgBuilder.addComponents(ActionRow.of(Button.secondary(word.guid, "${word.baseWordForm}  $wordClass")))
                }
            }
        }
        msgBuilder.send()
    }
}