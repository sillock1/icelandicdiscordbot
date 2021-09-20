package sillock.icelandicdiscordbot.creators.imagecreators

import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.models.enums.VerbImageCreatorType
import sillock.icelandicdiscordbot.models.inflectedforms.VerbForm
import sillock.icelandicdiscordbot.models.serialisations.DmiiWord
import java.awt.Color
import java.awt.Font
import java.awt.RenderingHints
import java.awt.image.BufferedImage

@Component
class ImperativeVerbImageCreator(private val tableDrawingCreator: TableDrawingCreator,
                                 private val tablePopulator: TablePopulator): IVerbImageCreator {
    override val verbImageCreatorType: VerbImageCreatorType
        get() = VerbImageCreatorType.Imperative

    override fun create(dmiiWord: DmiiWord, verbFormList: List<VerbForm?>): List<BufferedImage> {
        var width = 650
        val height = 700
        val backgroundColor = Color(54, 57, 63) //Discord embed colour

        val grouped = verbFormList.groupBy { it?.grammaticalNumber }

        var tableXOffset = 60

        val bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        val g2d = bufferedImage.createGraphics()

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)

        g2d.color = backgroundColor
        g2d.fillRect(0,0, width, height)

        g2d.font= Font("Segoe UI", Font.BOLD, 36)
        g2d.color = Color.WHITE
        g2d.drawString("Imperative Form (Boðháttur)", 60, 180)


        val subHeadingList = mutableListOf("Person", "Active Voice", "Middle Voice")

        val imageDataList : MutableList<MutableList<String>> = mutableListOf()
        imageDataList.add(subHeadingList)

        for(grammaticalPerson in grouped){
            val rowData : MutableList<String> = mutableListOf()
            rowData.add(grammaticalPerson.value.getOrNull(0)?.let{x -> if(x.clipped) "Clipped" else x.grammaticalNumber.toString()}!!)
            rowData.add(grammaticalPerson.value.getOrNull(0)?.conjugatedString ?: "--")
            rowData.add(grammaticalPerson.value.getOrNull(1)?.conjugatedString ?: "--")
            imageDataList.add(rowData)
        }

        tableDrawingCreator.drawTable(g2d, 4, tableXOffset, 260, 550, 70)
        tablePopulator.populateTable(g2d, tableXOffset, 260, 24, 70, 60, "", imageDataList)
        tableXOffset+=520

        g2d.color = Color.WHITE
        g2d.font= Font("Segoe UI", Font.BOLD, 64)
        g2d.drawString(dmiiWord.baseWordForm, 60, 100)

        g2d.font= Font("Segoe UI", Font.BOLD, 36)
        g2d.color = Color.ORANGE
        g2d.drawString("Verb (Sagnorð)", dmiiWord.baseWordForm.length*55, 100)

        g2d.dispose()

        return listOf(bufferedImage)
    }

}