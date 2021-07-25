package sillock.icelandicdiscordbot.listeners

import org.javacord.api.entity.message.MessageBuilder
import org.javacord.api.event.interaction.MessageComponentCreateEvent
import org.javacord.api.listener.interaction.MessageComponentCreateListener
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.services.DmiiCoreService
import sillock.icelandicdiscordbot.creators.imagecreators.NounDeclensionImageCreator
import sillock.icelandicdiscordbot.mappers.NounDeclensionMapper
import sillock.icelandicdiscordbot.models.embedmodels.NounDeclensionForm
import java.awt.image.BufferedImage

@Component
class ButtonResponseListener(private val dmiiCoreService: DmiiCoreService,
                             private val nounDeclensionMapper: NounDeclensionMapper,
                             private val nounDeclensionImageCreator: NounDeclensionImageCreator
): MessageComponentCreateListener {
    override fun onComponentCreate(event: MessageComponentCreateEvent?) {
        val response = dmiiCoreService.getDeclensionsByGuid(event!!.messageComponentInteraction.customId)

        val declinedList: MutableList<NounDeclensionForm> = mutableListOf()
        val imageList: MutableList<BufferedImage> = mutableListOf()
        for (word in response) {
            word.bmyndir.forEach {
                    x ->
                val res = nounDeclensionMapper.map(x.g, x.b)
                if(res != null) declinedList.add(res)
            }
            imageList.add(nounDeclensionImageCreator.create(word.kyn, declinedList))
        }
        event.interaction.createImmediateResponder().setContent("Here's your word!").respond()
        val messageBuilder = MessageBuilder()

        for(image in imageList) {
            messageBuilder.addAttachment(image, "decline.png").send(event.interaction.channel.get())
        }
    }
}