package sillock.icelandicdiscordbot.creators.imagecreators

import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.mappers.NounGenderMapper
import sillock.icelandicdiscordbot.mappers.WordTypeMapper
import sillock.icelandicdiscordbot.models.serialisations.Word
import sillock.icelandicdiscordbot.models.enums.InflectionType
import sillock.icelandicdiscordbot.models.inflectedforms.InflectedForm
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import kotlin.math.roundToInt

@Component
class NounDeclensionImageCreator(private val tableDrawingCreator: TableDrawingCreator,
                                 private val nounGenderMapper: NounGenderMapper,
                                 private val tablePopulator: TablePopulator) : IImageCreator{
    override val inflectionType: InflectionType
        get() = InflectionType.Article

    override fun create(word: Word, inflectionalFormList: List<InflectedForm?>): BufferedImage {
        var width = 500
        val height = 700
        val backgroundColor = Color(54, 57, 63) //Discord embed colour

        val grouped = inflectionalFormList.groupBy { it?.grammaticalNumber }.mapValues { (_, v) -> v.groupBy { it?.grammaticalForm } }
        width = (width * grouped.size)

        var tableXOffset = 60

        val bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        val g2d = bufferedImage.createGraphics()

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)

        g2d.color = backgroundColor
        g2d.fillRect(0,0, width, height)

        val subHeadingList = mutableListOf("Form", "No Article", "With article")
        val gender = nounGenderMapper.map(word.kyn)
        for(grammaticalNum in grouped){
            val imageDataList : MutableList<MutableList<String>> = mutableListOf()
            imageDataList.add(subHeadingList)
            for(grammaticalForm in grammaticalNum.value){
                val rowData : MutableList<String> = mutableListOf()
                val nounDeclensionForms = grammaticalForm.value
                rowData.add(grammaticalForm.key.toString().substring(0, 3) + ".")
                rowData.add(nounDeclensionForms.getOrNull(0)?.inflectedString ?: "Undefined")
                rowData.add(nounDeclensionForms.getOrNull(1)?.inflectedString ?: "Undefined")
                imageDataList.add(rowData)
            }
            tableDrawingCreator.drawTable(g2d, 5, tableXOffset, 260, 400, 70)
            tablePopulator.populateTable(g2d, tableXOffset, 260, 70, 60, grammaticalNum.key.toString(),  subHeadingList, imageDataList)
            tableXOffset+=480
        }

        g2d.color = Color.WHITE
        g2d.font= Font("Segoe UI", Font.BOLD, 64)
        g2d.drawString(word.ord, 60, 100)

        g2d.font= Font("Segoe UI", Font.BOLD, 36)
        g2d.color = Color.ORANGE
        g2d.drawString(gender, 60, 220)


        g2d.dispose()

        return bufferedImage
    }
}