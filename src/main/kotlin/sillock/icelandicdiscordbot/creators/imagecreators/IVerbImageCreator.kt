package sillock.icelandicdiscordbot.creators.imagecreators

import sillock.icelandicdiscordbot.models.enums.VerbImageCreatorType
import sillock.icelandicdiscordbot.models.inflectedforms.VerbForm
import sillock.icelandicdiscordbot.models.serialisations.DmiiWord
import java.awt.image.BufferedImage

interface IVerbImageCreator {
    val verbImageCreatorType: VerbImageCreatorType
    fun create(dmiiWord: DmiiWord, verbFormList: List<VerbForm?>): List<BufferedImage>
}