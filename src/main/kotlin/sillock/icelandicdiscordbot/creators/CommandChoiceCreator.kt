package sillock.icelandicdiscordbot.creators

import net.dv8tion.jda.api.interactions.commands.Command
import org.springframework.stereotype.Component

@Component
class CommandChoiceCreator {
    fun CreateChoices(choices: Map<String, String>) : MutableList<Command.Choice>{
        var commandChoices: MutableList<Command.Choice> = mutableListOf()
        choices.forEach{x ->
            commandChoices.add(Command.Choice(x.key, x.value))
        }
        return commandChoices
    }
}