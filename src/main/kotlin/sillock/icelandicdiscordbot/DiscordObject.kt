package sillock.icelandicdiscordbot

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.interactions.commands.Command
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import sillock.icelandicdiscordbot.models.commands.ICommand

object DiscordObject{

    fun init (jda: JDA, commands: List<ICommand>)
    {
        val guild = jda.guilds.first()

        val updateCommands = guild.updateCommands()//jda.updateCommands()

        val commandData = mutableListOf<CommandData>()
        commands.forEach { command ->
            var commandToRegister = CommandData(command.name, command.description)

            command.options.forEach { option ->
                var optionData = OptionData(option.type, option.name, option.description, option.required)
                option.choices.forEach { choice ->
                    optionData.addChoice(choice.name, choice.asString)
                }
                commandToRegister.addOption(option.type, option.name, option.description, option.required)
            }

            commandData.add(commandToRegister)
        }

        updateCommands.addCommands(commandData).queue()
    }
}