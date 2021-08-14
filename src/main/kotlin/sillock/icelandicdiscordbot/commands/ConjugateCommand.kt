package sillock.icelandicdiscordbot.commands

import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionChoice
import org.javacord.api.interaction.SlashCommandOptionType
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.factories.ImageCreatorFactory
import sillock.icelandicdiscordbot.mappers.InflectionTypeMapper
import sillock.icelandicdiscordbot.mappers.VerbMapper
import sillock.icelandicdiscordbot.models.enums.GrammaticalMood
import sillock.icelandicdiscordbot.models.enums.GrammaticalUsage
import sillock.icelandicdiscordbot.models.inflectedforms.VerbForm
import sillock.icelandicdiscordbot.services.DmiiCoreService

@Component
class ConjugateCommand(private val dmiiCoreService: DmiiCoreService,
                       private val imageCreatorFactory: ImageCreatorFactory,
                       private val inflectionTypeMapper: InflectionTypeMapper,
                       private val verbMapper: VerbMapper): ICommand {
    override val name: String
        get() = "conjugate"
    override val description: String
        get() = "Finds conjugations of a verb"
    override val options: List<SlashCommandOption>
        get() = listOf(
            SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "activevoice", "Active voice (Germynd)",
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
            SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "middlevoice", "Middle voice (Miðmynd)",
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
        val response = dmiiCoreService.getVerbConjugation("fara")
        val word = response.first()
        val wordType = inflectionTypeMapper.map(word.shortHandWordClass) ?: return
        var inflectedVerbs = mutableListOf<Pair<String, String>>()
        word.inflectionalFormList.forEach { x ->
            inflectedVerbs.add(Pair(x.grammaticalTagString, x.inflectedString))
        }
        val verbFormsList = verbMapper.map(inflectedVerbs)

        val imageList = imageCreatorFactory.create(wordType)

        event.channel.get().sendMessage(response.toString())
    }
}