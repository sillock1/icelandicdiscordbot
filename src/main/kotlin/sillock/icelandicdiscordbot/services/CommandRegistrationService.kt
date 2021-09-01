package sillock.icelandicdiscordbot.services

import org.javacord.api.DiscordApi
import org.javacord.api.interaction.SlashCommandBuilder
import org.springframework.stereotype.Service
import sillock.icelandicdiscordbot.commands.ICommand

@Service
class CommandRegistrationService(private val commands: List<ICommand>) {
    fun registerCommands(discordApi: DiscordApi) {

        val slashCommandBuilderList = mutableListOf<SlashCommandBuilder>()

        commands.forEach { command ->
            var slashCommandBuilder = SlashCommandBuilder()
            slashCommandBuilder.setName(command.name)
            slashCommandBuilder.setDescription(command.description)
            slashCommandBuilder.setOptions(command.options)
            slashCommandBuilderList.add(slashCommandBuilder)
        }
        discordApi.bulkOverwriteGlobalSlashCommands(slashCommandBuilderList).join()

    }

}