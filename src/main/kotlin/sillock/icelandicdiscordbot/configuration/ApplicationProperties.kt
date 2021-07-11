package sillock.icelandicdiscordbot.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("discord")
data class ApplicationProperties (
    var token: String
)