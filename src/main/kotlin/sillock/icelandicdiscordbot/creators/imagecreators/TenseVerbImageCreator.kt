package sillock.icelandicdiscordbot.creators.imagecreators

import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.models.enums.VerbImageCreatorType
import sillock.icelandicdiscordbot.models.inflectedforms.VerbForm
import sillock.icelandicdiscordbot.models.serialisations.Word
import java.awt.Color
import java.awt.Font
import java.awt.RenderingHints
import java.awt.image.BufferedImage

@Component
class TenseVerbImageCreator(private val tableDrawingCreator: TableDrawingCreator,
                            private val tablePopulator: TablePopulator): IVerbImageCreator {
    override val verbImageCreatorType: VerbImageCreatorType
        get() = VerbImageCreatorType.Tense

    override fun create(word: Word, verbFormList: List<VerbForm?>): List<BufferedImage> {
        var width = 600
        val height = 800
        val backgroundColor = Color(54, 57, 63) //Discord embed colour


        val grouped = verbFormList.groupBy { it?.grammaticalTense }
                            .mapValues { (_, v) -> v.groupBy { it?.grammaticalPerson }}

        width = width * grouped.size
        var tableXOffset = 60

        val bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        val g2d = bufferedImage.createGraphics()

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)

        g2d.color = backgroundColor
        g2d.fillRect(0,0, width, height)

        val subHeadingList = mutableListOf("Person", "Singular", "Plural")

        var imageDataList : MutableList<MutableList<String>> = mutableListOf()
        imageDataList.add(subHeadingList)

        var tableYOffset = 360
        for(grammaticalTense in grouped){
                for(grammaticalPerson in grammaticalTense.value){
                    val rowData : MutableList<String> = mutableListOf()
                    rowData.add(grammaticalPerson.key.toString())
                    rowData.add(grammaticalPerson.value.getOrNull(0)?.conjugatedString ?: "Undefined")
                    rowData.add(grammaticalPerson.value.getOrNull(1)?.conjugatedString ?: "Undefined")
                    imageDataList.add(rowData)
                }
                tableDrawingCreator.drawTable(g2d, 4, tableXOffset, tableYOffset, 550, 70)
                tablePopulator.populateTable(g2d, tableXOffset, tableYOffset, 24, 70, 60, grammaticalTense.key.toString(), imageDataList)
                tableXOffset+=570
                imageDataList = mutableListOf()
                imageDataList.add(subHeadingList)
        }

        g2d.color = Color.WHITE
        g2d.font= Font("Segoe UI", Font.BOLD, 64)
        g2d.drawString(word.baseWordForm, 60, 100)

        g2d.font= Font("Segoe UI", Font.BOLD, 36)
        g2d.color = Color.ORANGE
        g2d.drawString("Verb (Sagnorð)", 60, 220)

        g2d.dispose()

        return listOf(bufferedImage)
    }
}