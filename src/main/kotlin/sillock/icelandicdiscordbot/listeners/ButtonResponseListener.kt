package sillock.icelandicdiscordbot.listeners

import org.javacord.api.entity.message.MessageBuilder
import org.javacord.api.event.interaction.MessageComponentCreateEvent
import org.javacord.api.listener.interaction.MessageComponentCreateListener
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.helpers.Failure
import sillock.icelandicdiscordbot.helpers.Success
import sillock.icelandicdiscordbot.processors.InflectionProcessor
import sillock.icelandicdiscordbot.services.DmiiCoreService

@Component
class ButtonResponseListener(private val dmiiCoreService: DmiiCoreService,
                             private val inflectionProcessor: InflectionProcessor): MessageComponentCreateListener {

    override fun onComponentCreate(event: MessageComponentCreateEvent?) {
        val messageBuilder = MessageBuilder()

        when(val response = dmiiCoreService.getDeclensionsByGuid(event!!.messageComponentInteraction.customId)){
            is Failure -> {
                messageBuilder.setContent(response.reason).send(event.interaction.channel.get())
            }
            is Success -> {
                messageBuilder.setContent("Results:")
                val imageList = inflectionProcessor.process(response.value)
                imageList.forEach{
                        x -> messageBuilder.addAttachment(x, "inflect${x.hashCode()}.png")
                }
                messageBuilder.send(event.interaction.channel.get())
            }
        }

        //Delete the original message to prevent spam
        event.messageComponentInteraction.message?.get()?.delete()
    }
}