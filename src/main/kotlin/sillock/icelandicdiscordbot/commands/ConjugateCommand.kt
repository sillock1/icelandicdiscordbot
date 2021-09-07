package sillock.icelandicdiscordbot.commands

import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionType
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.factories.VerbImageCreatorFactory
import sillock.icelandicdiscordbot.helpers.Failure
import sillock.icelandicdiscordbot.helpers.Success
import sillock.icelandicdiscordbot.mappers.InflectionTypeMapper
import sillock.icelandicdiscordbot.mappers.VerbMapper
import sillock.icelandicdiscordbot.models.enums.GrammaticalMood
import sillock.icelandicdiscordbot.models.enums.VerbImageCreatorType
import sillock.icelandicdiscordbot.models.inflectedforms.VerbForm
import sillock.icelandicdiscordbot.services.DmiiCoreService
import java.awt.image.BufferedImage

@Component
class ConjugateCommand(private val dmiiCoreService: DmiiCoreService,
                       private val inflectionTypeMapper: InflectionTypeMapper,
                       private val verbMapper: VerbMapper,
                       private val verbImageCreatorFactory: VerbImageCreatorFactory): ICommand {
    override val name: String
        get() = "conjugate"
    override val description: String
        get() = "Finds conjugations of a verb"
    override val options: List<SlashCommandOption>
        get() = listOf(SlashCommandOption.create(SlashCommandOptionType.STRING, "word", "The verb to search", true))


    override fun execute(event: SlashCommandInteraction) {
        event.respondLater().join()
        when(val response = dmiiCoreService.getVerbConjugation(event.firstOptionStringValue.get())){
            is Failure -> {
                event.createFollowupMessageBuilder().setContent(response.reason).send()
            }
            is Success -> {
                val word = response.value.firstOrNull()
                word?.shortHandWordClass?.let { inflectionTypeMapper.map(it) } ?: run {
                    event.createFollowupMessageBuilder().setContent("Word not found").send()
                    return
                }
                val inflectedVerbs = mutableListOf<Pair<String, String>>()
                word.inflectionalFormList.forEach { x ->
                    inflectedVerbs.add(Pair(x.grammaticalTagString, x.inflectedString))
                }
                val verbFormsList = verbMapper.map(inflectedVerbs)

                val imageList: MutableList<BufferedImage> = mutableListOf()
                VerbImageCreatorType.values().forEach { x ->
                    val imageCreator = verbImageCreatorFactory.create(x)
                    var filteredVerbs: List<VerbForm?>
                    if(imageCreator.verbImageCreatorType == VerbImageCreatorType.Interrogative){
                        filteredVerbs = filterVerb(verbFormsList, imperativeForm = false, interrogativeForm = true)
                    }
                    else if(imageCreator.verbImageCreatorType == VerbImageCreatorType.Imperative){
                        filteredVerbs = filterVerb(verbFormsList, imperativeForm = true, interrogativeForm = false)
                    }
                    else{
                        filteredVerbs = filterVerb(verbFormsList, imperativeForm = false, interrogativeForm = false)
                    }
                    if(filteredVerbs.isNotEmpty())
                        imageList.addAll(imageCreator.create(word, filteredVerbs))
                }

                val responseAttachments = imageList.chunked(10)
                responseAttachments.reversed().forEach { listOfImages ->
                    val responseMessager = event.createFollowupMessageBuilder()
                    listOfImages.reversed().forEach { x ->
                        responseMessager.addAttachment(x, "conjugation${x.hashCode()}.png")
                    }
                    responseMessager.send().join()
                }
            }
        }
    }

    private fun filterVerb(verbFormList: List<VerbForm?>, imperativeForm: Boolean, interrogativeForm: Boolean): List<VerbForm?>{
        val filteredFormList = verbFormList.filter { x ->
            x?.interrogativeMood == interrogativeForm && if(imperativeForm) x.grammaticalMood == GrammaticalMood.ImperativeMood else x.grammaticalMood != GrammaticalMood.ImperativeMood
        }
        return filteredFormList.filterNotNull()
    }
}