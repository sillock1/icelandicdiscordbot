package sillock.icelandicdiscordbot.commands

import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionType
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.helpers.Failure
import sillock.icelandicdiscordbot.helpers.Success
import sillock.icelandicdiscordbot.services.OrdabokService

@Component
class ExampleCommand(private val ordabokService: OrdabokService): ICommand {
    override val name: String
        get() = "example"
    override val description: String
        get() = "Get usage of word"
    override val options: List<SlashCommandOption>
        get() = listOf(
            SlashCommandOption.create(SlashCommandOptionType.STRING, "word", "Word to search by", true),
            SlashCommandOption.create(SlashCommandOptionType.INTEGER, "amount", "Amount of examples", false)
        )
    private val defaultExampleCount: Int = 6


    override fun execute(event: SlashCommandInteraction) {
        event.respondLater().join()
        val wordParam = event.firstOptionStringValue.get()
        val exampleCount = if (!event.secondOptionIntValue.isEmpty) event.secondOptionIntValue.get() else defaultExampleCount
        var errorMessage = ""

        when(val wordResult = ordabokService.getWordId(wordParam)){
            is Failure -> {errorMessage += wordResult.reason + "\n"}
            is Success -> {
                if(wordResult.value.resultList.isNotEmpty()) {
                    when(val wordData = ordabokService.getDataByWordId(wordResult.value.resultList.first().wordId)){
                        is Failure -> {errorMessage+= wordData.reason + "\n"}
                        is Success -> {
                            val exampleData = wordData.value.wordItems.filter { x -> x.itemTag == "DÆMI" }.take(exampleCount)
                            var result = ""
                            var i = 0
                            exampleData.forEach { x ->
                                result += "${i}. ${x.text}\n"
                                i+=1
                            }
                            event.createFollowupMessageBuilder().setContent(result.ifEmpty { "No examples available" }).send()
                            return
                        }
                    }
                }
                else{
                    event.createFollowupMessageBuilder().setContent("No examples available").send()
                }
            }
        }
        event.createFollowupMessageBuilder().setContent(errorMessage).send()
    }
}