package sillock.icelandicdiscordbot.services


import org.javacord.api.listener.GloballyAttachableListener
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.creators.DiscordApiCreator
import sillock.icelandicdiscordbot.models.commands.ICommand
import javax.annotation.PostConstruct

@Component
class SetupService(private val commandRegistrationService: CommandRegistrationService,
    private val discordApiCreator: DiscordApiCreator,
    private val listeners: List<GloballyAttachableListener>){
    @PostConstruct
    fun setup()
    {
        val api = discordApiCreator.create()
        commandRegistrationService.registerCommands(api)
        for (listener: GloballyAttachableListener in listeners) {
            api.addListener(listener)
        }
    }
}