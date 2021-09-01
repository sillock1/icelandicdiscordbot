package sillock.icelandicdiscordbot.commands

import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionType
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.services.OrdabokService

@Component
class WordAudioSampleCommand(private val ordabokService: OrdabokService): ICommand {
    override val name: String
        get() = "say"
    override val description: String
        get() = "Get audio file for word"
    override val options: List<SlashCommandOption>
        get() = listOf(
            SlashCommandOption.create(SlashCommandOptionType.STRING, "word", "Word to search by", true)
        )

    override fun execute(event: SlashCommandInteraction) {
        event.respondLater().join()
        val wordParam = event.firstOptionStringValue.get()

        val wordResult = ordabokService.getWordId(wordParam)
        if(wordResult.resultList.isNotEmpty()) {
            val wordData = ordabokService.getDataByWordId(wordResult.resultList.first().wordId)
            val mp3Data = wordData.wordItems.first { x -> x.itemTag == "FRAMB" }
            val mp3File = ordabokService.getAudioFile(wordData.wordId.toString().substring(0, 2), mp3Data.itemId)
            event.createFollowupMessageBuilder().addAttachment(mp3File, "${wordData.word}.ogg").send()
            return
        }
        event.createFollowupMessageBuilder().setContent("No audio sample available").send()
    }
}