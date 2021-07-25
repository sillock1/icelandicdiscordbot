package sillock.icelandicdiscordbot.services

//import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.javacord.api.listener.GloballyAttachableListener
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.creators.DiscordApiCreator
import sillock.icelandicdiscordbot.models.commands.ICommand
import javax.annotation.PostConstruct

@Component
class SetupService(//private val jdaCreator: JdaCreator,
    private val commandRegistrationService: CommandRegistrationService,
    private val discordApiCreator: DiscordApiCreator,
    private val listeners: List<GloballyAttachableListener>,
    private val commands: List<ICommand>){
    @PostConstruct
    fun setup()
    {
        var api = discordApiCreator.create()
        commandRegistrationService.registerCommands(api)
        for (listener: GloballyAttachableListener in listeners) {
            api.addListener(listener)
        }


        /*
        val jda = jdaCreator.create()

        DiscordObject.init(jda, commands)
        */

    }
}