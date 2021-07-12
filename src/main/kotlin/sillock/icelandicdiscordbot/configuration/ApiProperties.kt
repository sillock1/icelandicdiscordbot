package sillock.icelandicdiscordbot.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("dmiicore")
data class ApiProperties(
    var endpoint: String
)
