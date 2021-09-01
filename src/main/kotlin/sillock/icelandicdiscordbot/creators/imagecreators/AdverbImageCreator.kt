package sillock.icelandicdiscordbot.creators.imagecreators

import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.mappers.WordTypeMapper
import sillock.icelandicdiscordbot.models.enums.InflectionType
import sillock.icelandicdiscordbot.models.inflectedforms.InflectedForm
import sillock.icelandicdiscordbot.models.serialisations.DmiiWord
import java.awt.Color
import java.awt.Font
import java.awt.RenderingHints
import java.awt.image.BufferedImage

@Component
class AdverbImageCreator(private val tableDrawingCreator: TableDrawingCreator,
                         private val tablePopulator: TablePopulator,
                         private val wordTypeMapper: WordTypeMapper): IImageCreator<InflectedForm> {
    override val inflectionType: InflectionType
        get() = InflectionType.Adverb

    override fun create(dmiiWord: DmiiWord, inflectionalFormList: List<InflectedForm?>): List<BufferedImage> {
        var width = 200
        val height = 500
        val backgroundColor = Color(54, 57, 63) //Discord embed colour

        width = (width * inflectionalFormList.size)

        var tableXOffset = 60

        val bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        val g2d = bufferedImage.createGraphics()

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)

        g2d.color = backgroundColor
        g2d.fillRect(0,0, width, height)

        val subHeadingList = mutableListOf("Positive", "Comparative", "Superlative")
        val imageDataList : MutableList<MutableList<String>> = mutableListOf()
        val rowData : MutableList<String> = mutableListOf()
            imageDataList.add(subHeadingList)
            for(form in inflectionalFormList){
                rowData.add(form?.inflectedString ?: "Undefined")
            }
            imageDataList.add(rowData)
            tableDrawingCreator.drawTable(g2d, 2, tableXOffset, 260, 480, 70)
            tablePopulator.populateTable(g2d, tableXOffset, 260, 24, 70, 55, "Adverb table",  imageDataList)
            tableXOffset+=630


        g2d.color = Color.WHITE
        g2d.font= Font("Segoe UI", Font.BOLD, 64)
        g2d.drawString(dmiiWord.baseWordForm, 60, 100)

        val wordType = wordTypeMapper.map(dmiiWord.shortHandWordClass)

        g2d.font= Font("Segoe UI", Font.BOLD, 36)
        g2d.color = Color.ORANGE
        g2d.drawString(wordType, 60, 220)


        g2d.dispose()
        return listOf(bufferedImage)
    }
}