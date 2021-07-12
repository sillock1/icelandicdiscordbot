package sillock.icelandicdiscordbot

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.CommandData

object DiscordObject {

    fun init (jda: JDA)
    {
        val guild = jda.guilds.first()

        val updateCommands = guild.updateCommands()//jda.updateCommands()

        val commandData = mutableListOf<CommandData>()
        //val data = CommandData("ping", "Pings the bot lmao")
        val ordCommand = CommandData("ord", "Fetches word from BÍN")
        //data.addOption(OptionType.MENTIONABLE, "ping", "I ping")
        ordCommand.addOption(OptionType.STRING, "word", "Any word class", true)
        //commandData.add(data)
        commandData.add(ordCommand)

        updateCommands.addCommands(commandData).queue()
    }
}