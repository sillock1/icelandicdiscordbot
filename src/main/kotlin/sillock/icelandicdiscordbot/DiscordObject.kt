package sillock.icelandicdiscordbot

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.CommandData

object DiscordObject {

    fun init (jda: JDA)
    {
        val updateCommands = jda.updateCommands()

        val commandData = mutableListOf<CommandData>()
        val data = CommandData("ping", "Pings the bot lmao")

        commandData.add(data)
        data.addOption(OptionType.MENTIONABLE, "ping", "I ping")

        updateCommands.addCommands(commandData).queue()
    }
}