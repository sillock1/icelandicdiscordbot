package sillock.icelandicdiscordbot.commands

import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionType
import org.springframework.stereotype.Component
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

        val wordResult = ordabokService.getWordId(wordParam)
        if(wordResult.resultList.isNotEmpty()) {
            val wordData = ordabokService.getDataByWordId(wordResult.resultList.first().wordId)
            val exampleData = wordData.wordItems.filter { x -> x.itemTag == "DÆMI" }.take(exampleCount)
            var result = ""
            var i = 0
            exampleData.forEach { x ->
                result += "${i}. ${x.text}\n"
                i+=1
            }
            event.createFollowupMessageBuilder().setContent(result).send()
            return
        }
        event.createFollowupMessageBuilder().setContent("No examples available").send()
    }
}