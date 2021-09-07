package sillock.icelandicdiscordbot.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("apiendpoint")
data class ApiEndpoint(
    var dmiicore: String,
    var ordabok: String,
    var islex: String
)
