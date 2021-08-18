package sillock.icelandicdiscordbot.creators.imagecreators

import sillock.icelandicdiscordbot.models.enums.VerbImageCreatorType
import sillock.icelandicdiscordbot.models.inflectedforms.VerbForm
import sillock.icelandicdiscordbot.models.serialisations.Word
import java.awt.image.BufferedImage

interface IVerbImageCreator {
    val verbImageCreatorType: VerbImageCreatorType
    fun create(word: Word, verbFormList: List<VerbForm?>): List<BufferedImage>
}