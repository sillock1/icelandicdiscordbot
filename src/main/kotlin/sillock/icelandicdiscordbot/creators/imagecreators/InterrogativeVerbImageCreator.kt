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
class InterrogativeVerbImageCreator(private val tableDrawingCreator: TableDrawingCreator,
                                    private val tablePopulator: TablePopulator): IVerbImageCreator {
    override val verbImageCreatorType: VerbImageCreatorType
        get() = VerbImageCreatorType.Interrogative

    override fun create(dmiiWord: DmiiWord, verbFormList: List<VerbForm?>): List<BufferedImage> {
        var width = 1000
        val height = 1200//800
        val backgroundColor = Color(54, 57, 63) //Discord embed colour

        val grouped = verbFormList.groupBy { it?.grammaticalTense }
            .mapValues { (_, v) -> v.groupBy { it?.grammaticalPerson }
                .mapValues{ (_, v) -> v.groupBy {(Pair(it?.grammaticalVoice, it?.grammaticalMood))}}}

        //width = width * grouped.size
        var tableXOffset = 60

        val bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        val g2d = bufferedImage.createGraphics()

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)

        g2d.color = backgroundColor
        g2d.fillRect(0,0, width, height)

        g2d.font= Font("Segoe UI", Font.BOLD, 36)
        g2d.color = Color.WHITE
        g2d.drawString("Interrogative Form", 60, 180)

        val subHeadingList = mutableListOf("", "Singular", "Plural")

        var imageDataList : MutableList<MutableList<String>> = mutableListOf()
        imageDataList.add(subHeadingList)

        var tableYOffset = 200
        for(grammaticalTense in grouped){
            for(grammaticalPerson in grammaticalTense.value){
                for(voiceAndMood in grammaticalPerson.value){
                    val rowData: MutableList<String> = mutableListOf()
                    rowData.add(voiceAndMood.key.first.toString() + " & " + voiceAndMood.key.second.toString())
                    rowData.add(voiceAndMood.value.getOrNull(0)?.conjugatedString ?: "--")
                    rowData.add(voiceAndMood.value.getOrNull(1)?.conjugatedString ?: "--")
                    imageDataList.add(rowData)
                }
            }
            tableDrawingCreator.drawTable(g2d, imageDataList.size, tableXOffset, tableYOffset, 800, 70)
            tablePopulator.populateTable(g2d, tableXOffset, tableYOffset, 24, 70, 105, grammaticalTense.key.toString(), imageDataList)
            tableYOffset+=450
            imageDataList = mutableListOf()
            imageDataList.add(subHeadingList)
        }

        g2d.color = Color.WHITE
        g2d.font= Font("Segoe UI", Font.BOLD, 64)
        g2d.drawString(dmiiWord.baseWordForm, 60, 100)

        g2d.font= Font("Segoe UI", Font.BOLD, 36)
        g2d.color = Color.ORANGE
        g2d.drawString("Verb (Sagnorð)", dmiiWord.baseWordForm.length*50, 100)

        g2d.dispose()

        return listOf(bufferedImage)
    }

}