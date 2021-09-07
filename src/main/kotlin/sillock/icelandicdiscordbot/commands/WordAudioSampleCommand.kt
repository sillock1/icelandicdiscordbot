package sillock.icelandicdiscordbot.commands

import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionType
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.helpers.Failure
import sillock.icelandicdiscordbot.helpers.Success
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

        var errorMessage = ""
        when(val wordResult = ordabokService.getWordId(wordParam)){
            is Failure -> {errorMessage += wordResult.reason + "\n"}
            is Success -> {
                if(wordResult.value.resultList.isNotEmpty()) {
                    when(val wordData = ordabokService.getDataByWordId(wordResult.value.resultList.first().wordId)){
                        is Failure -> {errorMessage += wordData.reason + "\n"}
                        is Success -> {
                            val mp3Data = wordData.value.wordItems.firstOrNull { x -> x.itemTag == "FRAMB" }
                            if(mp3Data != null){
                                val trimmedWordId = if(wordData.value.wordId < 10000) wordData.value.wordId.toString().substring(0, 1) else wordData.value.wordId.toString().substring(0, 2)
                                when(val audioResult = ordabokService.getAudioFile(trimmedWordId, mp3Data.itemId)) {
                                    is Failure -> {errorMessage += audioResult.reason + "\n"}
                                    is Success -> {
                                        event.createFollowupMessageBuilder().addAttachment(audioResult.value, "${wordData.value.word}.ogg").send()
                                        return
                                    }
                                }
                            }
                            else{
                                errorMessage+="No audio sample available"
                            }
                        }
                    }
                }
                else{
                    errorMessage+="No audio sample available"
                }
            }
        }
        event.createFollowupMessageBuilder().setContent(errorMessage).send()
    }
}