package sillock.icelandicdiscordbot.creators.imagecreators

import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.mappers.WordTypeMapper
import sillock.icelandicdiscordbot.models.enums.InflectionType
import sillock.icelandicdiscordbot.models.inflectedforms.InflectedForm
import sillock.icelandicdiscordbot.models.serialisations.Word
import java.awt.Color
import java.awt.Font
import java.awt.RenderingHints
import java.awt.image.BufferedImage

@Component
class GenderedImageCreator(private val tableDrawingCreator: TableDrawingCreator,
                           private val tablePopulator: TablePopulator,
                           private val wordTypeMapper: WordTypeMapper): IImageCreator {
    override val inflectionType: InflectionType
        get() = InflectionType.Gender

    override fun create(word: Word, inflectionalFormList: List<InflectedForm>): BufferedImage {
        var width = 650
        val height = 700
        val backgroundColor = Color(54, 57, 63) //Discord embed colour

        val grouped = inflectionalFormList.groupBy { it.grammaticalNumber }.mapValues { (_, v) -> v.groupBy { it.grammaticalForm } }
        width = (width * grouped.size)

        var tableXOffset = 60

        val bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        val g2d = bufferedImage.createGraphics()

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2d.color = backgroundColor
        g2d.fillRect(0,0, width, height)

        val subHeadingList = mutableListOf("Form", "Male", "Female", "Neuter")
        for(grammaticalNum in grouped){
            val imageDataList : MutableList<MutableList<String>> = mutableListOf()
            imageDataList.add(subHeadingList)
            for(grammaticalForm in grammaticalNum.value){
                val rowData : MutableList<String> = mutableListOf()
                val nounDeclensionForms = grammaticalForm.value
                rowData.add(grammaticalForm.key.toString().substring(0, 3) + ".")
                rowData.add(nounDeclensionForms.getOrNull(0)?.inflectedString ?: "Undefined")
                rowData.add(nounDeclensionForms.getOrNull(1)?.inflectedString ?: "Undefined")
                rowData.add(nounDeclensionForms.getOrNull(2)?.inflectedString ?: "Undefined")
                imageDataList.add(rowData)
            }
            tableDrawingCreator.drawTable(g2d, 5, tableXOffset, 260, 500, 70)
            tablePopulator.populateTable(g2d, tableXOffset, 260, 70, 60, grammaticalNum.key.toString(),  subHeadingList, imageDataList)
            tableXOffset+=630
        }

        g2d.color = Color.WHITE
        g2d.font= Font("Segoe UI", Font.BOLD, 64)
        g2d.drawString(word.ord, 60, 100)

        val wordType = wordTypeMapper.map(word.ofl)

        g2d.font= Font("Segoe UI", Font.BOLD, 36)
        g2d.color = Color.ORANGE
        g2d.drawString(wordType, 60, 220)


        g2d.dispose()

        return bufferedImage
    }
}