package sillock.icelandicdiscordbot.creators.imagecreators

import sillock.icelandicdiscordbot.models.serialisations.Word
import sillock.icelandicdiscordbot.models.enums.InflectionType
import sillock.icelandicdiscordbot.models.inflectedforms.InflectedForm
import java.awt.Graphics2D
import java.awt.image.BufferedImage

interface IImageCreator {
    val inflectionType: InflectionType
    fun create(word: Word, inflectionalFormList: List<InflectedForm?>): BufferedImage
}