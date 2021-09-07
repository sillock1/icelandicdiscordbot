package sillock.icelandicdiscordbot.commands

import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionType
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.helpers.Failure
import sillock.icelandicdiscordbot.helpers.Success
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
        var errorMessage = ""

        when(val wordResult = ordabokService.getWordId(wordParam)){
            is Failure -> {errorMessage += wordResult.reason + "\n"}
            is Success -> {
                if(wordResult.value.resultList.isNotEmpty()) {
                    when(val similarWordsResult = ordabokService.getSimilarWordsByWordId(wordResult.value.resultList.first().wordId)){
                        is Failure -> {errorMessage += similarWordsResult.reason + "\n"}
                        is Success -> {
                            var result = ""
                            similarWordsResult.value.resultList.forEach { x ->
                                result += "${x.wordName}\n"
                            }
                            event.createFollowupMessageBuilder().setContent(result).send()
                            return
                        }
                    }
                }
                else{
                    event.createFollowupMessageBuilder().setContent("No similar words available").send()
                    return
                }
            }
        }
        event.createFollowupMessageBuilder().setContent(errorMessage).send()
    }
}