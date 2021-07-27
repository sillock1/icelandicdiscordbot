package sillock.icelandicdiscordbot.listeners

import org.javacord.api.entity.message.MessageBuilder
import org.javacord.api.event.interaction.MessageComponentCreateEvent
import org.javacord.api.listener.interaction.MessageComponentCreateListener
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.services.DmiiCoreService
import sillock.icelandicdiscordbot.creators.imagecreators.NounDeclensionImageCreator
import sillock.icelandicdiscordbot.factories.ImageCreatorFactory
import sillock.icelandicdiscordbot.factories.InflectionalMapperFactory
import sillock.icelandicdiscordbot.mappers.NounDeclensionMapper
import sillock.icelandicdiscordbot.mappers.WordTypeMapper
import sillock.icelandicdiscordbot.models.imagegeneration.InflectedForm
import sillock.icelandicdiscordbot.models.imagegeneration.NounDeclensionForm
import java.awt.image.BufferedImage

@Component
class ButtonResponseListener(private val dmiiCoreService: DmiiCoreService,
                             private val wordTypeMapper: WordTypeMapper,
                             private val inflectionalMapperFactory: InflectionalMapperFactory,
                             private val imageCreatorFactory: ImageCreatorFactory
): MessageComponentCreateListener {
    override fun onComponentCreate(event: MessageComponentCreateEvent?) {
        val inflectedList: MutableList<InflectedForm> = mutableListOf()

        val response = dmiiCoreService.getDeclensionsByGuid(event!!.messageComponentInteraction.customId)
        val word = response[0]
        val wordType = wordTypeMapper.map(word.ofl) ?: return
        val inflectionalMapper = inflectionalMapperFactory.create(wordType)
        val imageCreator = imageCreatorFactory.create(wordType)

        word.bmyndir.forEach {
                x ->
            val res = inflectionalMapper.map(x.g, x.b)
            if(res != null) inflectedList.add(res)
        }
        val image = imageCreator.create(word.ord, word.kyn, inflectedList)

        event.interaction.createImmediateResponder().setContent("Here's your word!").respond()
        val messageBuilder = MessageBuilder()

        messageBuilder.addAttachment(image, "decline.png").send(event.interaction.channel.get())
    }
}