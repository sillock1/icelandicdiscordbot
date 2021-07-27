package sillock.icelandicdiscordbot.creators.imagecreators

import sillock.icelandicdiscordbot.models.enums.InflectionType
import sillock.icelandicdiscordbot.models.imagegeneration.InflectedForm
import java.awt.Graphics2D
import java.awt.image.BufferedImage

interface IImageCreator {
    val inflectionType: InflectionType
    fun populateTable(g2d: Graphics2D, offsetX: Int, offsetY: Int, rowSpacing: Int, columnSpacing: Int, tableHeader: String, subHeadingList: List<String>, contentList: MutableList<MutableList<String>>)
    fun create(title: String, subTitle: String, inflectionalFormList: List<InflectedForm>): BufferedImage
}