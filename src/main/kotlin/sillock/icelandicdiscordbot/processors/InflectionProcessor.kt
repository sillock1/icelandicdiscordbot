package sillock.icelandicdiscordbot.processors

import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.factories.ImageCreatorFactory
import sillock.icelandicdiscordbot.factories.InflectionalMapperFactory
import sillock.icelandicdiscordbot.mappers.InflectionTypeMapper
import sillock.icelandicdiscordbot.models.serialisations.Word
import sillock.icelandicdiscordbot.models.inflectedforms.InflectedForm
import java.awt.image.BufferedImage

@Component
class InflectionProcessor (private val inflectionalMapperFactory: InflectionalMapperFactory,
                           private val imageCreatorFactory: ImageCreatorFactory,
                           private val inflectionTypeMapper: InflectionTypeMapper){
    fun process(wordList: List<Word>) : BufferedImage?{
        val word = wordList.first()
        val wordType = inflectionTypeMapper.map(word.ofl) ?: return null
        val inflectionalMapper = inflectionalMapperFactory.create(wordType)
        val imageCreator = imageCreatorFactory.create(wordType)
        val pairList = mutableListOf<Pair<String, String>>()
        word.bmyndir.forEach {x ->
            val pair: Pair<String, String> = Pair(x.g, x.b)
            pairList.add(pair)
        }
        val sortedForms = inflectionalMapper.map(pairList)
        return imageCreator.create(word, sortedForms)
    }
}