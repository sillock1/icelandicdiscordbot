package sillock.icelandicdiscordbot.models.commands

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.Command
import net.dv8tion.jda.api.interactions.commands.OptionType
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.DmiiCoreService

@Component
class DeclineCommand(private val dmiiCoreService: DmiiCoreService) : ICommand {

    override val name: String
        get() = "decline"
    override val description: String
        get() = "Finds declensions of a word"
    override val options: List<CommandOption>
        get() = listOf(
                CommandOption(
                        "type",
                        "Type of word to search by",
                        OptionType.STRING, //This is a placeholder due to the API not fully implementing choices yet
                        true,
                        listOf(
                                Command.Choice("The Reflexive Pronoun (Afturbeygt fornafn)", "afn"),
                                Command.Choice("Adverb (Atviksorð)", "ao"),
                                Command.Choice("Other Pronouns (Önnur fornöfn)", "fn"),
                                Command.Choice("Prepositions (Forsetningar)", "fs"),
                                Command.Choice("The Definite Article (Greinir)", "gr"),
                                Command.Choice("Adjectives (Lýsingarorð)", "lo"),
                                Command.Choice("The Nominative Marker (Nafnháttarmerki)", "nhm"),
                                Command.Choice("Noun (Nafnorð)", "no"),
                                Command.Choice("Personal Pronouns (Persónufornöfn)", "pfn"),
                                Command.Choice("Ordinals (Raðtölur)", "rt"),
                                Command.Choice("Verbs (Sagnorð)", "so"),
                                Command.Choice("Conjunctions (Samtengingar)", "st"),
                                Command.Choice("Numerals (Töluorð)", "to"),
                                Command.Choice("Exclamations (Upphrópanir)", "uh"),
                        )),
                CommandOption("word", "The word to search", OptionType.STRING, true, listOf()))

    override fun execute(event: SlashCommandEvent) {
        val wordTypeParam = event.getOption("type")
        val wordParam = event.getOption("word")
        val response = dmiiCoreService.getDeclensions(wordTypeParam!!.asString, wordParam!!.asString)
        //Temporary fix for exception when longer than 2000 characters
        //TODO: Call embed service and parser for the json
        event.reply(response.toString().substring(0, Math.min(response.toString().length, 2000))).queue()
    }
}