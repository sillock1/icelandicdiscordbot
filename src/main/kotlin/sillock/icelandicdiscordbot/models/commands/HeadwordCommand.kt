package sillock.icelandicdiscordbot.models.commands

//import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
//import net.dv8tion.jda.api.interactions.commands.OptionType
import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionType
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.services.DmiiCoreService
import sillock.icelandicdiscordbot.creators.NounEmbedCreator

@Component
class HeadwordCommand (private val dmiiCoreService: DmiiCoreService, private val nounEmbedCreator: NounEmbedCreator) : ICommand {
    override val name: String
        get() = "headword"
    override val description: String
        get() = "Searches for headword"
    override val options: List<SlashCommandOption>
        get() = listOf(SlashCommandOption.createWithChoices(SlashCommandOptionType.STRING, "word", "Word to search for", true))

    override fun execute(event: SlashCommandInteraction) {
        val wordParam = event.firstOptionStringValue
        val response = dmiiCoreService.getHeadword(wordParam.get())

        //val embed = nounEmbedCreator.create()

        event.channel.get().sendMessage("")
    }
}