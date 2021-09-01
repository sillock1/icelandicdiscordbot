package sillock.icelandicdiscordbot.processors

import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.factories.ImageCreatorFactory
import sillock.icelandicdiscordbot.factories.InflectionalMapperFactory
import sillock.icelandicdiscordbot.mappers.InflectionTypeMapper
import sillock.icelandicdiscordbot.models.serialisations.DmiiWord
import java.awt.image.BufferedImage

@Component
class InflectionProcessor (private val inflectionalMapperFactory: InflectionalMapperFactory,
                           private val imageCreatorFactory: ImageCreatorFactory,
                           private val inflectionTypeMapper: InflectionTypeMapper){
    fun process(dmiiWordList: List<DmiiWord>) : List<BufferedImage?>{
        val word = dmiiWordList.first()
        val wordType = inflectionTypeMapper.map(word.shortHandWordClass) ?: return listOf()
        val inflectionalMapper = inflectionalMapperFactory.create(wordType)
        val imageCreator = imageCreatorFactory.create(wordType)
        val pairList = mutableListOf<Pair<String, String>>()
        word.inflectionalFormList.forEach { x ->
            val pair: Pair<String, String> = Pair(x.grammaticalTagString, x.inflectedString)
            pairList.add(pair)
        }
        val sortedForms = inflectionalMapper.map(pairList)
        return imageCreator.create(word, sortedForms)
    }
}