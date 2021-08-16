package sillock.icelandicdiscordbot.commands

import org.javacord.api.interaction.*
import org.springframework.stereotype.Component
import org.yaml.snakeyaml.util.EnumUtils
import sillock.icelandicdiscordbot.factories.ImageCreatorFactory
import sillock.icelandicdiscordbot.mappers.InflectionTypeMapper
import sillock.icelandicdiscordbot.mappers.VerbMapper
import sillock.icelandicdiscordbot.models.enums.GrammaticalMood
import sillock.icelandicdiscordbot.models.enums.GrammaticalUsage
import sillock.icelandicdiscordbot.models.enums.GrammaticalVoice
import sillock.icelandicdiscordbot.models.inflectedforms.VerbForm
import sillock.icelandicdiscordbot.services.DmiiCoreService
import kotlin.reflect.typeOf

@Component
class ConjugateCommand(private val dmiiCoreService: DmiiCoreService,
                       private val inflectionTypeMapper: InflectionTypeMapper,
                       private val verbMapper: VerbMapper): ICommand {
    override val name: String
        get() = "conjugate"
    override val description: String
        get() = "Finds conjugations of a verb"
    override val options: List<SlashCommandOption>
        get() = listOf(
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

    override fun execute(event: SlashCommandInteraction) {
        //Call a parser to grab relevant argument data
        //Call the dmii service to grab the conjugation data
        //Call a mapper to format your data
        //Then filter on the data using the argument information
        //Then call the image creator factory and draw the appropriate table(s)
       // val wordParam = event.options.firstOrNull {     x -> x.stringValue.get() == "word" }//.getOptionStringValueByName("word")//.getOptionByName("word").get().stringValue
        val paramFilters = parseParams(event.options)
        val response = dmiiCoreService.getVerbConjugation(paramFilters.word)

        val word = response.first()
        val wordType = inflectionTypeMapper.map(word.shortHandWordClass) ?: return
        val inflectedVerbs = mutableListOf<Pair<String, String>>()
        word.inflectionalFormList.forEach { x ->
            inflectedVerbs.add(Pair(x.grammaticalTagString, x.inflectedString))
        }
        val verbFormsList = verbMapper.map(inflectedVerbs)
        val filteredList = verbFormsList.filter { x ->
            paramFilters.voice?.let{y -> y == x?.grammaticalVoice} ?: true
                && paramFilters.usage?.let{y -> y == x?.grammaticalUsage} ?: true
                && paramFilters.mood?.let{y -> y == x?.grammaticalMood} ?: true
                && paramFilters.interrogative.let{y -> y == x?.interrogativeMood}}


        event.channel.get().sendMessage(response.toString())
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