package sillock.icelandicdiscordbot.services

import io.kotest.core.spec.style.StringSpec
import io.kotest.property.Arb
import io.kotest.property.Gen
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.list
import io.kotest.property.arbitrary.next
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.condition.AnyOf
import org.javacord.api.DiscordApi
import org.javacord.api.interaction.SlashCommandBuilder
import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption
import org.springframework.boot.test.context.SpringBootTest
import sillock.icelandicdiscordbot.commands.ICommand


    class AllPropertiesValid: StringSpec({
        class MockCommand(private val nameP: String, private val descriptionP: String, private val optionsP: List<SlashCommandOption>): ICommand {
            override val name: String
                get() = nameP
            override val description: String
                get() = descriptionP
            override val options: List<SlashCommandOption>
                get() = optionsP

            override fun execute(event: SlashCommandInteraction) {
            }
        }

        "Always calls bulk update on slash commands"{
            val mockCommand = arbitrary {
                val name = Arb.string().next()
                val description = Arb.string().next()
                val slashCommandList = listOf(mockk<SlashCommandOption>())
                MockCommand(name, description, slashCommandList)
            }
            checkAll(Arb.list(mockCommand, 0..10)){commands ->
                val service = CommandRegistrationService(commands)
                val discordApiMock = mockk<DiscordApi>(relaxed = true)

                service.registerCommands(discordApiMock)
                verify{
                    discordApiMock.bulkOverwriteGlobalSlashCommands(any<List<SlashCommandBuilder>>())
                }
            }
        }
    })