package sillock.icelandicdiscordbot.commands

import org.javacord.api.entity.message.MessageBuilder
import org.javacord.api.interaction.*
import org.springframework.stereotype.Component
import org.yaml.snakeyaml.util.EnumUtils
import sillock.icelandicdiscordbot.creators.imagecreators.ImperativeVerbImageCreator
import sillock.icelandicdiscordbot.factories.ImageCreatorFactory
import sillock.icelandicdiscordbot.factories.VerbImageCreatorFactory
import sillock.icelandicdiscordbot.mappers.InflectionTypeMapper
import sillock.icelandicdiscordbot.mappers.VerbMapper
import sillock.icelandicdiscordbot.models.enums.GrammaticalMood
import sillock.icelandicdiscordbot.models.enums.GrammaticalUsage
import sillock.icelandicdiscordbot.models.enums.GrammaticalVoice
import sillock.icelandicdiscordbot.models.enums.VerbImageCreatorType
import sillock.icelandicdiscordbot.models.inflectedforms.VerbForm
import sillock.icelandicdiscordbot.services.DmiiCoreService
import java.awt.image.BufferedImage
import kotlin.reflect.typeOf

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
            /*listOf(
            SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "ActiveVoice", "Active voice (Germynd)",
                listOf(
                    SlashCommandOption.createWithChoices(SlashCommandOptionType.STRING, "usage", "Verb usage", true,
                        listOf(
                            SlashCommandOptionChoice.create("Personal usage (Persónuleg notkun)", GrammaticalUsage.Personal.toString()),
                            SlashCommandOptionChoice.create("Impersonal usage dative form (Ópersónuleg notkun - (Frumlag í þágufalli))", GrammaticalUsage.ImpersonalDative.toString()),
                            SlashCommandOptionChoice.create("Impersonal usage dummy subject (Ópersónuleg notkun - (Gervifrumlag))", GrammaticalUsage.ImpersonalDummy.toString()))
                    ),
                    SlashCommandOption.createWithChoices(SlashCommandOptionType.STRING, "mood", "Verb mood", true,
                        listOf(
                            SlashCommandOptionChoice.create("Indicative mood (Framsöguháttur)", GrammaticalMood.Indicative.toString()),
                            SlashCommandOptionChoice.create("Subjunctive mood (Viðtengingarháttur)", GrammaticalMood.Subjunctive.toString()))
                    ),
                    SlashCommandOption.create(SlashCommandOptionType.STRING, "word", "The verb to search", true)
                )),
            SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "MiddleVoice", "Middle voice (Miðmynd)",
                listOf(
                    SlashCommandOption.createWithChoices(SlashCommandOptionType.STRING, "usage", "Verb usage", true,
                        listOf(
                            SlashCommandOptionChoice.create("Personal usage (Persónuleg notkun)", GrammaticalUsage.Personal.toString()),
                            SlashCommandOptionChoice.create("Impersonal usage dative form (Ópersónuleg notkun - (Frumlag í þágufalli))", GrammaticalUsage.ImpersonalDative.toString()),
                            SlashCommandOptionChoice.create("Impersonal usage dummy subject (Ópersónuleg notkun - (Gervifrumlag))", GrammaticalUsage.ImpersonalDummy.toString()))
                    ),
                    SlashCommandOption.createWithChoices(SlashCommandOptionType.STRING, "mood", "Verb mood", true,
                        listOf(
                            SlashCommandOptionChoice.create("Indicative mood (Framsöguháttur)", GrammaticalMood.Indicative.toString()),
                            SlashCommandOptionChoice.create("Subjunctive mood (Viðtengingarháttur)", GrammaticalMood.Subjunctive.toString()))
                    ),
                    SlashCommandOption.create(SlashCommandOptionType.STRING, "word", "The verb to search", true)
                )),
                    SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "Imperative", "Imperative form (Boðháttur)",
                        listOf(SlashCommandOption.create(SlashCommandOptionType.STRING, "word", "The verb to search", true))),
                    SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "Interrogative", "Interrogative form (Spurnarmyndir)",
                        listOf(SlashCommandOption.create(SlashCommandOptionType.STRING, "word", "The verb to search", true)))
        )
             */

    override fun execute(event: SlashCommandInteraction) {
        //Call a parser to grab relevant argument data
        //Call the dmii service to grab the conjugation data
        //Call a mapper to format your data
        //Then filter on the data using the argument information
        //Then call the image creator factory and draw the appropriate table(s)
       // val wordParam = event.options.firstOrNull {     x -> x.stringValue.get() == "word" }//.getOptionStringValueByName("word")//.getOptionByName("word").get().stringValue
        event.respondLater().join()
       // val paramFilters = parseParams(event.options)
        val response = dmiiCoreService.getVerbConjugation(event.firstOptionStringValue.get())

        if(response.isEmpty()) {
            MessageBuilder().setContent("No results").send(event.channel.get())
            return
        }

        val word = response.first()
        inflectionTypeMapper.map(word.shortHandWordClass) ?: return
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
            imageList.addAll(imageCreator.create(word, filteredVerbs))
        }

        val msgBuilder = event.createFollowupMessageBuilder()

        var i = 0
        imageList.reversed().take(imageList.size-2).forEach { x ->
            msgBuilder.addAttachment(x, "conjugation${i}.png")
            i+=1
            //messageBuilder.addAttachment(x, "conjugate${x.hashCode()}.png")
        }
        msgBuilder.send()

        i = imageList.size-2
        val messageBuilder = MessageBuilder()
        imageList.reversed().take(2).forEach { x ->
            messageBuilder.addAttachment(x, "conjugation${i}.png")
            i+=1
            //messageBuilder.addAttachment(x, "conjugate${x.hashCode()}.png")
        }
        messageBuilder.send(event.channel.get())


        //messageBuilder.send(event.channel.get())
        //messageResponder.send()
        /*
        val filteredList = verbFormsList.filter { x ->
            paramFilters.voice?.let{y -> y == x?.grammaticalVoice} ?: true
                && paramFilters.usage?.let{y -> y == x?.grammaticalUsage} ?: true
                && paramFilters.mood?.let{y -> y == x?.grammaticalMood} ?: true
                && paramFilters.interrogative.let{y -> y == x?.interrogativeMood}}

        if(filteredList.isEmpty()) {
            MessageBuilder().setContent("No results").send(event.channel.get())
            return
        }
        //val verbImageCreatorType: VerbImageCreatorType
        if(filteredList.firstOrNull()?.grammaticalMood == GrammaticalMood.Imperative){
            verbImageCreatorType = VerbImageCreatorType.Imperative
        }
        else if(filteredList.firstOrNull()?.interrogativeMood!!){
            verbImageCreatorType = VerbImageCreatorType.Interrogative
        }
        else{
            verbImageCreatorType = VerbImageCreatorType.Tense
        }
        val imageCreator = verbImageCreatorFactory.create(verbImageCreatorType)
        val images = imageCreator.create(word, filteredList)

        val messageBuilder = MessageBuilder()
        var i = 0
        for(image in images.reversed()) {
            messageBuilder.addAttachment(image, "conjugate${image.hashCode()}.png")
            i+=1
        }
        messageBuilder.send(event.channel.get())
         */
    }

    private fun filterVerb(verbFormList: List<VerbForm?>, imperativeForm: Boolean, interrogativeForm: Boolean): List<VerbForm?>{
        val filteredFormList = verbFormList.filter { x ->
            x?.interrogativeMood == interrogativeForm && if(imperativeForm) x.grammaticalMood == GrammaticalMood.Imperative else x.grammaticalMood != GrammaticalMood.Imperative
        }
        return filteredFormList.filterNotNull()
    }

    private fun parseParams(options: List<SlashCommandInteractionOption>) : ConjugateFilterObject{
        var voice: GrammaticalVoice? = null
        var usage: GrammaticalUsage? = GrammaticalUsage.Personal
        var mood: GrammaticalMood? = null
        val word: String?
        var interrogative = false
        when (val firstParam = options.firstOrNull()?.name?.lowercase()) {
            "interrogative" -> interrogative = true
            "imperative" -> {
                mood = EnumUtils.findEnumInsensitiveCase(GrammaticalMood::class.java, firstParam)
            }
            else -> {
                voice = EnumUtils.findEnumInsensitiveCase(GrammaticalVoice::class.java, firstParam)
            }
        }
        if(voice == GrammaticalVoice.ActiveVoice || voice == GrammaticalVoice.MiddleVoice){
        val usageParam = options.firstOrNull()?.options?.elementAt(0)?.stringValue?.get()
        val moodParam = options.firstOrNull()?.options?.elementAt(1)?.stringValue?.get()
            usage = usageParam?.let {  EnumUtils.findEnumInsensitiveCase(GrammaticalUsage::class.java, it) }
            mood = moodParam?.let {  EnumUtils.findEnumInsensitiveCase(GrammaticalMood::class.java, it) }
        word = options.firstOrNull()?.options?.elementAt(2)?.stringValue?.get()
        }
        else{
            word = options.firstOrNull()?.options?.elementAt(0)?.stringValue?.get()
        }

        return ConjugateFilterObject(word!!, voice, usage, mood, interrogative)
    }

    data class ConjugateFilterObject(val word: String, val voice: GrammaticalVoice?, val usage: GrammaticalUsage?, val mood: GrammaticalMood?, val interrogative: Boolean)
}