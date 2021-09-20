package sillock.icelandicdiscordbot.commands

import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionChoice
import org.javacord.api.interaction.SlashCommandOptionType
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.helpers.Failure
import sillock.icelandicdiscordbot.helpers.Success
import sillock.icelandicdiscordbot.processors.DmiiDuplicateWordProcessor
import sillock.icelandicdiscordbot.processors.InflectionProcessor
import sillock.icelandicdiscordbot.services.DmiiCoreService

@Component
class DeclineCommand(private val dmiiCoreService: DmiiCoreService,
                     private val dmiiDuplicateWordProcessor: DmiiDuplicateWordProcessor,
                     private val inflectionProcessor: InflectionProcessor) : ICommand {
    override val name: String
        get() = "decline"
    override val description: String
        get() = "Finds declensions of a word"
    override val options: List<SlashCommandOption>
        get() = listOf(
             SlashCommandOption.createWithChoices(
                    SlashCommandOptionType.STRING,
                        "type",
                        "Type of word to search by",
                 true,
                    listOf(
                                SlashCommandOptionChoice.create("Adverb (Atviksorð)", "ao"),
                                SlashCommandOptionChoice.create("Other Pronouns (Önnur fornöfn)", "fn"),
                                SlashCommandOptionChoice.create("The Definite Article (Greinir)", "gr"),
                                SlashCommandOptionChoice.create("Adjectives (Lýsingarorð)", "lo"),
                                SlashCommandOptionChoice.create("Noun (Nafnorð)", "no"),
                                SlashCommandOptionChoice.create("Personal Pronouns (Persónufornöfn)", "pfn"),
                                SlashCommandOptionChoice.create("Ordinals (Raðtölur)", "rt"),
                                SlashCommandOptionChoice.create("Numerals (Töluorð)", "to")
                        )),
            SlashCommandOption.create(SlashCommandOptionType.STRING, "word", "The word to search", true))

    override fun execute(event: SlashCommandInteraction) {
        event.respondLater().join()
        val responseMessager = event.createFollowupMessageBuilder()
        val wordTypeParam = event.firstOptionStringValue
        val wordParam = event.secondOptionStringValue

        when(val response = dmiiCoreService.getDeclensions(wordTypeParam.get(), wordParam.get())){
            is Failure -> {
                responseMessager.setContent(response.reason).send()
            }
            is Success -> {
                if(response.value.count() != 1){
                    responseMessager.setContent("Sorry I couldn't find the exact word you were looking for...").send()
                    dmiiDuplicateWordProcessor.response(event, response.value)
                    return
                }
                val images = inflectionProcessor.process(response.value)
                for(image in images.reversed()) {
                    responseMessager.addAttachment(image, "inflected${image.hashCode()}.png")
                }
                responseMessager.send()
            }
        }
    }
}