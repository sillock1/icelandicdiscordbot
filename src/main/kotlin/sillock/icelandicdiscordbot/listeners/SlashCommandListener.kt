package sillock.icelandicdiscordbot.listeners

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.DmiiCoreService

@Component
class SlashCommandListener(
    private val dmiiCoreService: DmiiCoreService
) : ListenerAdapter() {

    override fun onSlashCommand(event: SlashCommandEvent) {

        //probably command factory here

        if(event.name.equals("ord"))
        {
            val wordParam = event.getOption("word")
            val response = dmiiCoreService.getHeadword(wordParam!!.asString)

            event.reply(response.toString()).queue()
        }

        if(!event.name.equals("ping")) return
        val time = System.currentTimeMillis()
        event.reply("Pong!").setEphemeral(false)
            .flatMap { event.hook.editOriginalFormat("Pong: %d ms", System.currentTimeMillis() - time)}.queue()
    }

}