package sillock.icelandicdiscordbot.mappers

import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.models.enums.*
import sillock.icelandicdiscordbot.models.inflectedforms.DegreeForm
import sillock.icelandicdiscordbot.models.inflectedforms.InflectedForm

@Component
class DegreeMapper: IInflectionalMapper {
    override val inflectionType: InflectionType
        get() = InflectionType.Adverb

    override fun map(formInflectedWordPair: List<Pair<String, String>>): List<InflectedForm?> {
        val degreeForms = mutableListOf<DegreeForm?>()
        formInflectedWordPair.forEach { pair ->
            val form = when(pair.first){
                "FST" -> {DegreeForm(pair.second, null, null, Degree.Positive)}
                "MST" -> {DegreeForm(pair.second, null, null, Degree.Comparative)}
                "EST" -> {DegreeForm(pair.second, null, null, Degree.Superlative)}
                else  -> null
            }
            degreeForms.add(form)
        }
        return degreeForms.filterNotNull().sortedWith (compareBy { it.degree })
    }
}