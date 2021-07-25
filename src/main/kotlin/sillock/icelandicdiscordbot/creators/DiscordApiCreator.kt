package sillock.icelandicdiscordbot.creators

import org.javacord.api.DiscordApi
import org.javacord.api.DiscordApiBuilder
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.configuration.ApplicationProperties

@Component
class DiscordApiCreator(private val properties: ApplicationProperties) {
    fun create(): DiscordApi {
        return DiscordApiBuilder()
            .setToken(properties.token)
            .login().join()
    }
}