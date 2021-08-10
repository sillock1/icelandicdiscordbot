package sillock.icelandicdiscordbot.commands

import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionType
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.factories.ImageCreatorFactory
import sillock.icelandicdiscordbot.factories.InflectionalMapperFactory
import sillock.icelandicdiscordbot.mappers.InflectionTypeMapper
import sillock.icelandicdiscordbot.services.DmiiCoreService
import java.util.*

@Component
class ConjugateCommand(private val dmiiCoreService: DmiiCoreService,
                       private val imageCreatorFactory: ImageCreatorFactory,
                       private val inflectionTypeMapper: InflectionTypeMapper): ICommand {
    override val name: String
        get() = "conjugate"
    override val description: String
        get() = "Finds conjugations of a verb"
    override val options: List<SlashCommandOption>
        get() = listOf(SlashCommandOption.createWithChoices(SlashCommandOptionType.STRING,"verb", "Verb to conjugate", true))

    override fun execute(event: SlashCommandInteraction) {
        val wordParam = event.firstOptionStringValue
        val response = dmiiCoreService.getVerbConjugation(wordParam.get())
        val word = response.first()
        val wordType = inflectionTypeMapper.map(word.ofl) ?: return
        val imageList = imageCreatorFactory.create(wordType)

        event.channel.get().sendMessage(response.toString())
    }
}