package sillock.icelandicdiscordbot.creators.imagecreators

import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.mappers.WordTypeMapper
import sillock.icelandicdiscordbot.models.enums.InflectionType
import sillock.icelandicdiscordbot.models.inflectedforms.AdjectiveForm
import sillock.icelandicdiscordbot.models.inflectedforms.InflectedForm
import sillock.icelandicdiscordbot.models.serialisations.DmiiWord
import java.awt.Color
import java.awt.Font
import java.awt.RenderingHints
import java.awt.image.BufferedImage

@Component
class AdjectiveImageCreator(private val tableDrawingCreator: TableDrawingCreator,
                           private val tablePopulator: TablePopulator,
                           private val wordTypeMapper: WordTypeMapper): IImageCreator<InflectedForm> {
    override val inflectionType: InflectionType
        get() = InflectionType.Adjective

    override fun create(dmiiWord: DmiiWord, inflectionalFormList: List<InflectedForm?>): List<BufferedImage> {
        var width = 450
        var height = 800
        val backgroundColor = Color(54, 57, 63) //Discord embed colour

        val grouped = (inflectionalFormList as List<AdjectiveForm>).groupBy{ it.grammaticalDegree }.mapValues { (_, v) ->
            v.groupBy { it.strength }.mapValues { (_, v) ->
                v.groupBy { it.grammaticalNumber }.mapValues { (_, v) ->
                    v.groupBy { it.grammaticalForm } } } }

        var bufferedImageList: MutableList<BufferedImage> = mutableListOf()
        for(degree in grouped){
            var tableXOffset = 60
            var tableYOffset = 360
            val imageWidth = (width * grouped.size)
            val imageHeight = (height * degree.value.size)
            var bufferedImage = BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB)
            var g2d = bufferedImage.createGraphics()
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)

            g2d.color = backgroundColor
            g2d.fillRect(0,0, imageWidth, imageHeight)

            val subHeadingList = mutableListOf("Form", "Male", "Female", "Neuter")

            var imageDataList : MutableList<MutableList<String>> = mutableListOf()
            imageDataList.add(subHeadingList)
            g2d.color = Color.WHITE
            g2d.font= Font("Segoe UI", Font.BOLD, 48)

            g2d.drawString(degree.key.toString(), 60, tableYOffset-150)
            for(strength in degree.value){
                g2d.color = Color.WHITE
                g2d.font= Font("Segoe UI", Font.BOLD, 42)
                g2d.drawString(strength.key.toString(), 60, tableYOffset-50)
                for(number in strength.value){
                    for(grammaticalForm in number.value){
                        val rowData : MutableList<String> = mutableListOf()
                        val genderedForms = grammaticalForm.value
                        rowData.add(grammaticalForm.key.toString().substring(0, 3) + ".")
                        rowData.add(genderedForms.getOrNull(0)?.inflectedString ?: "Undefined")
                        rowData.add(genderedForms.getOrNull(1)?.inflectedString ?: "Undefined")
                        rowData.add(genderedForms.getOrNull(2)?.inflectedString ?: "Undefined")
                        imageDataList.add(rowData)
                    }
                    tableDrawingCreator.drawTable(g2d, 5, tableXOffset, tableYOffset, 600, 70)
                    tablePopulator.populateTable(g2d, tableXOffset, tableYOffset, 24, 70, 50, number.key.toString(),  imageDataList)
                    tableXOffset+=650
                    imageDataList = mutableListOf()
                    imageDataList.add(subHeadingList)
                }
                tableXOffset = 60
                tableYOffset += 550
            }
            g2d.color = Color.WHITE
            g2d.font= Font("Segoe UI", Font.BOLD, 64)
            g2d.drawString(dmiiWord.baseWordForm, 60, 100)

            val wordType = wordTypeMapper.map(dmiiWord.shortHandWordClass)

            g2d.font= Font("Segoe UI", Font.BOLD, 64)
            g2d.color = Color.ORANGE
            g2d.drawString(wordType, 300, 100)
            g2d.dispose()
            bufferedImageList.add(bufferedImage)
        }
        return bufferedImageList
    }
}