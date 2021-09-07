package sillock.icelandicdiscordbot.listeners

import org.javacord.api.entity.message.MessageBuilder
import org.javacord.api.event.interaction.MessageComponentCreateEvent
import org.javacord.api.listener.interaction.MessageComponentCreateListener
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.processors.InflectionProcessor
import sillock.icelandicdiscordbot.services.DmiiCoreService

@Component
class ButtonResponseListener(private val dmiiCoreService: DmiiCoreService,
                             private val inflectionProcessor: InflectionProcessor): MessageComponentCreateListener {

    override fun onComponentCreate(event: MessageComponentCreateEvent?) {

        val response = dmiiCoreService.getDeclensionsByGuid(event!!.messageComponentInteraction.customId)

        event.interaction.createImmediateResponder().setContent("Result:").respond()

        val messageBuilder = MessageBuilder()
        /*
        val imageList = inflectionProcessor.process(response)
        imageList.forEach{
            x ->  messageBuilder.addAttachment(x, "inflect.png").send(event.interaction.channel.get())
        }
        //Delete the original message to prevent spam
        event.messageComponentInteraction.message?.get()?.delete()

         */
    }
}