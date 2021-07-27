package sillock.icelandicdiscordbot.commands

import org.javacord.api.entity.message.MessageBuilder
import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionChoice
import org.javacord.api.interaction.SlashCommandOptionType
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.services.DmiiCoreService
import sillock.icelandicdiscordbot.creators.imagecreators.NounDeclensionImageCreator
import sillock.icelandicdiscordbot.factories.ImageCreatorFactory
import sillock.icelandicdiscordbot.factories.InflectionalMapperFactory
import sillock.icelandicdiscordbot.mappers.NounDeclensionMapper
import sillock.icelandicdiscordbot.mappers.NounGenderMapper
import sillock.icelandicdiscordbot.mappers.WordTypeMapper
import sillock.icelandicdiscordbot.models.imagegeneration.InflectedForm
import sillock.icelandicdiscordbot.models.imagegeneration.NounDeclensionForm
import sillock.icelandicdiscordbot.processors.DmiiDuplicateWordProcessor
import sillock.icelandicdiscordbot.processors.InflectionProcessor
import java.awt.image.BufferedImage
import java.util.*

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
        val wordTypeParam = event.firstOptionStringValue
        val wordParam = event.secondOptionStringValue
        val response = dmiiCoreService.getDeclensions(wordTypeParam.get(), wordParam.get().lowercase(Locale.getDefault())
        )

        if(response.count() != 1){
            dmiiDuplicateWordProcessor.response(event, response)
            return
        }
        event.createImmediateResponder().setContent("Result:").respond()
        val messageBuilder = MessageBuilder()
        val image = inflectionProcessor.process(response)
        if(image != null)
            messageBuilder.addAttachment(image, "inflect.png").send(event.channel.get())
    }
}