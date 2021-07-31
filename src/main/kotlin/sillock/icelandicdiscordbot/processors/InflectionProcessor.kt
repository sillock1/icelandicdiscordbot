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
        val inflectedList: MutableList<InflectedForm> = mutableListOf()
        val word = wordList.first()
        val wordType = inflectionTypeMapper.map(word.ofl) ?: return null
        val inflectionalMapper = inflectionalMapperFactory.create(wordType)
        val imageCreator = imageCreatorFactory.create(wordType)
        word.bmyndir.forEach {x ->
            val res = inflectionalMapper.map(x.g, x.b)
            if(res != null) inflectedList.add(res)
        }
        return imageCreator.create(word, inflectedList)
    }
}