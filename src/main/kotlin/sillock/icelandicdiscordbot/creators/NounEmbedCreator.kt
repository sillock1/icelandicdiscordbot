package sillock.icelandicdiscordbot.creators

//import net.dv8tion.jda.api.EmbedBuilder
//import net.dv8tion.jda.api.entities.MessageEmbed
import org.javacord.api.entity.message.embed.EmbedBuilder
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.models.embedmodels.NounDeclensionForm
import sillock.icelandicdiscordbot.models.enums.GrammaticalForm
import sillock.icelandicdiscordbot.models.enums.GrammaticalNumber

@Component
class NounEmbedCreator{

    fun create(words: MutableList<NounDeclensionForm>) : EmbedBuilder{
        val embedBuilder = EmbedBuilder()
        embedBuilder.setTitle("Declensions")
        embedBuilder.setDescription("Some description")
        embedBuilder.setAuthor("Author Name", "http://google.com/", "https://cdn.discordapp.com/embed/avatars/0.png")
        /*embedBuilder.addField("With article (án greinis)", "", false)
        //Loop over map set singular with articles
        embedBuilder.addField("Nf.", "Some value2", false)
        embedBuilder.addField("Þf.", "Some value2", false)
        embedBuilder.addField("Þgf.", "Some value2", false)
        embedBuilder.addField("Ef.", "Some value2", false)
        embedBuilder.addField("Without article (með greini)", "", false)
        //Loop over map set singular without articles
        embedBuilder.addField("Nf. ", "Some value2", false)
        embedBuilder.addField("Þf.", "Some value2", false)
        embedBuilder.addField("Þgf.", "Some value2", false)
        embedBuilder.addField("Ef.", "Some value2", false)
        embedBuilder.addField("Plural (Eintala)", "", false)
        embedBuilder.addField("With article (án greinis)", "", false)
        //Set plurals with articles
        embedBuilder.addField("Nf.", "Some value2", false)
        embedBuilder.addField("Þf.", "Some value2", false)
        embedBuilder.addField("Þgf.", "Some value2", false)
        embedBuilder.addField("Ef.", "Some value2", false)
        embedBuilder.addField("Without article (með greini)", "", false)
        //Set plurals without articles
        embedBuilder.addField("Nf. ", "Some value2", false)
        embedBuilder.addField("Þf.", "Some value2", false)
        embedBuilder.addField("Þgf.", "Some value2", false)
        embedBuilder.addField("Ef.", "Some value2", false)*/

        //  for space

        embedBuilder.addField("Singular (Eintala)", "singular", false)

        data class GroupNoun(val withArticle: Boolean, val grammaticalForm: GrammaticalForm)
        fun NounDeclensionForm.toGroup() = GroupNoun(withArticle, grammaticalForm)
        val group = words.groupBy ({it.grammaticalNumber}, {it.toGroup()})

        for (word in words) {
            if (word.grammaticalNumber == GrammaticalNumber.Singular) {
                embedBuilder.addField(
                    if (word.withArticle) "with article:" else "without article:",
                    "${word.grammaticalForm}: **${word.inflectedString}**", false
                )
            }
        }

        embedBuilder.addField("Plural (Fleirtala)", "Plural", false)
        for (word in words){
            if(word.grammaticalNumber == GrammaticalNumber.Plural)
                embedBuilder.addField(if(word.withArticle) "with article:" else "without article:",
                    "${word.grammaticalForm}: **${word.inflectedString}**", false )
        }
        /*embedBuilder.addField("nominative singular definite", "Some value", false)
        embedBuilder.addField("nominative singular definite with article", "Some value", false)
        embedBuilder.addField("nominative plural", "Some value", false)
        embedBuilder.addField("nominative plural definite with article", "Some value", false)

        embedBuilder.addField("accusative singular", "Some value", false)
        embedBuilder.addField("accusative singular definite with article", "Some value", false)
        embedBuilder.addField("accusative plural", "Some value", false)
        embedBuilder.addField("accusative plural definite with article", "Some value", false)
        */
        return embedBuilder
    }
}