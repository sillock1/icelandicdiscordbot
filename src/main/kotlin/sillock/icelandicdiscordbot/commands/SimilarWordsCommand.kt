package sillock.icelandicdiscordbot.commands

import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionType
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.services.OrdabokService

@Component
class SimilarWordsCommand(private val ordabokService: OrdabokService): ICommand {
    override val name: String
        get() = "similar"
    override val description: String
        get() = "Find similar words"
    override val options: List<SlashCommandOption>
        get() = listOf(
            SlashCommandOption.create(SlashCommandOptionType.STRING, "word", "Word to search by", true)
        )
    override fun execute(event: SlashCommandInteraction) {
        event.respondLater().join()
        val wordParam = event.firstOptionStringValue.get()

        val wordResult = ordabokService.getWordId(wordParam)
        if(wordResult.resultList.isNotEmpty()) {
            val similarWordsResult = ordabokService.getSimilarWordsByWordId(wordResult.resultList.first().wordId)
            var result = ""
            similarWordsResult.resultList.forEach { x ->
                result += "${x.wordName}\n"
            }
            event.createFollowupMessageBuilder().setContent(result).send()
            return
        }
        event.createFollowupMessageBuilder().setContent("No similar words available").send()
    }
}