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
import sillock.icelandicdiscordbot.processors.InflectionProcessor
import java.awt.image.BufferedImage

@Component
class ButtonResponseListener(private val dmiiCoreService: DmiiCoreService,
                             private val inflectionProcessor: InflectionProcessor): MessageComponentCreateListener {

    override fun onComponentCreate(event: MessageComponentCreateEvent?) {
        val response = dmiiCoreService.getDeclensionsByGuid(event!!.messageComponentInteraction.customId)

        event.interaction.createImmediateResponder().setContent("Result:").respond()
        val messageBuilder = MessageBuilder()
        val image = inflectionProcessor.process(response)
        if(image != null)
            messageBuilder.addAttachment(image, "inflect.png").send(event.interaction.channel.get())
    }
}