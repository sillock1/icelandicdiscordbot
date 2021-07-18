package sillock.icelandicdiscordbot.factories

import org.springframework.stereotype.Service
import sillock.icelandicdiscordbot.models.commands.ICommand

@Service
class CommandFactory (private val commands: List<ICommand>){

    fun getCommandByName(commandName: String) : ICommand?{
        return commands.firstOrNull { x -> x.name == commandName }
    }
}