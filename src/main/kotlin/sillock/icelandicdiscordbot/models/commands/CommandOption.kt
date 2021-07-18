package sillock.icelandicdiscordbot.models.commands

import net.dv8tion.jda.api.interactions.commands.Command
import net.dv8tion.jda.api.interactions.commands.OptionType

data class CommandOption (
        val name: String,
        val description: String,
        val type: OptionType,
        val required: Boolean = false,
        val choices: List<Command.Choice> = listOf())