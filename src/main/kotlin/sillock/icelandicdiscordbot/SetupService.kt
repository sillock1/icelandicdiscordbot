package sillock.icelandicdiscordbot

import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.creators.JdaCreator
import sillock.icelandicdiscordbot.models.commands.ICommand
import javax.annotation.PostConstruct

@Component
class SetupService(private val jdaCreator: JdaCreator,
                   private val listeners: List<ListenerAdapter>,
                   private val commands: List<ICommand>){
    @PostConstruct
    fun setup()
    {
        val jda = jdaCreator.create()
        for (listener: ListenerAdapter in listeners) {
            jda.addEventListener(listener)
        }
        DiscordObject.init(jda, commands)
    }
}