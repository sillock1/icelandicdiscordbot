package sillock.icelandicdiscordbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import sillock.icelandicdiscordbot.configuration.ApiProperties
import sillock.icelandicdiscordbot.configuration.ApplicationProperties

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties::class, ApiProperties::class)
class IcelandicDiscordBotApplication

fun main(args: Array<String>) {
    runApplication<IcelandicDiscordBotApplication>(*args)
}
