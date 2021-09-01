package sillock.icelandicdiscordbot.creators.imagecreators

import sillock.icelandicdiscordbot.models.serialisations.DmiiWord
import sillock.icelandicdiscordbot.models.enums.InflectionType
import java.awt.image.BufferedImage

interface IImageCreator<T> {
    val inflectionType: InflectionType
    fun create(dmiiWord: DmiiWord, inflectionalFormList: List<T?>): List<BufferedImage>
}