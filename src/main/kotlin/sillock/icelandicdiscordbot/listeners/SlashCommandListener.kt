package sillock.icelandicdiscordbot.listeners

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.stereotype.Component

@Component
class SlashCommandListener : ListenerAdapter() {

    override fun onSlashCommand(event: SlashCommandEvent) {
        if(!event.name.equals("ping")) return
        val time = System.currentTimeMillis()
        event.reply("Pong!").setEphemeral(false)
            .flatMap { event.hook.editOriginalFormat("Pong: %d ms", System.currentTimeMillis() - time)}.queue()
    }

}