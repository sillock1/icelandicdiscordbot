package sillock.icelandicdiscordbot.models.commands

//import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
//import net.dv8tion.jda.api.interactions.commands.Command
//import net.dv8tion.jda.api.interactions.commands.OptionType

import org.javacord.api.entity.message.MessageBuilder
import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionChoice
import org.javacord.api.interaction.SlashCommandOptionType
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.services.DmiiCoreService
import sillock.icelandicdiscordbot.creators.imagecreators.NounDeclensionImageCreator
import sillock.icelandicdiscordbot.mappers.NounDeclensionMapper
import sillock.icelandicdiscordbot.models.embedmodels.NounDeclensionForm
import sillock.icelandicdiscordbot.processors.DmiiDuplicateWordProcessor
import java.awt.image.BufferedImage


@Component
class DeclineCommand(private val dmiiCoreService: DmiiCoreService,
                     private val nounDeclensionMapper: NounDeclensionMapper,
                     private val nounDeclensionImageCreator: NounDeclensionImageCreator,
                     private val dmiiDuplicateWordProcessor: DmiiDuplicateWordProcessor) : ICommand {

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
                                SlashCommandOptionChoice.create("The Reflexive Pronoun (Afturbeygt fornafn)", "afn"),
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
        val response = dmiiCoreService.getDeclensions(wordTypeParam.get(), wordParam.get())

        if(response.count() != 1){
            dmiiDuplicateWordProcessor.response(event, response)
            return
        }

        val word = response.first()
        val declinedList: MutableList<NounDeclensionForm> = mutableListOf()
        val imageList: MutableList<BufferedImage> = mutableListOf()
        var gender: String = ""
        word.bmyndir.forEach {x ->
            val res = nounDeclensionMapper.map(x.g, x.b)
            if(res != null) declinedList.add(res)
        }
        if(word.kyn == "kvk") gender = "kvenkynsnafnorð (Female noun)"
        if(word.kyn == "hk") gender = "hvorugkynsnafnorð (Neuter noun)"
        if(word.kyn == "kk") gender = "karlkynsnafnorð (Male noun)"
        imageList.add(nounDeclensionImageCreator.create(gender, declinedList))


        val messageBuilder = MessageBuilder()

        event.createImmediateResponder().setContent("Here ya go!").respond()
        for(image in imageList) {
            messageBuilder.addAttachment(image, "decline.png").send(event.channel.get())
        }

    }
}