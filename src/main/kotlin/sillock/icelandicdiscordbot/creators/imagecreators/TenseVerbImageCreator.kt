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
class TenseVerbImageCreator(private val tableDrawingCreator: TableDrawingCreator,
                            private val tablePopulator: TablePopulator): IVerbImageCreator {
    override val verbImageCreatorType: VerbImageCreatorType
        get() = VerbImageCreatorType.Tense

    override fun create(dmiiWord: DmiiWord, verbFormList: List<VerbForm?>): List<BufferedImage> {
        var width = 1200
        val height = 800
        val backgroundColor = Color(54, 57, 63) //Discord embed colour


        val grouped = verbFormList.groupBy { it?.grammaticalVoice }.filter { it.key != null }
            .mapValues { (_,v) -> v.groupBy { it?.grammaticalUsage }.filter {it.key != null}
            .mapValues { (_,v) -> v.groupBy { it?.grammaticalMood }.filter { it.key != null }
            .mapValues { (_,v) -> v.groupBy { it?.grammaticalTense }.filter { it.key != null }
            .mapValues { (_, v) -> v.groupBy { it?.grammaticalPerson }.filter { it.key != null }}}}}



        var tableXOffset = 60



        var imageDataList : MutableList<MutableList<String>> = mutableListOf()
        var imageList: MutableList<BufferedImage> = mutableListOf()
        var tableYOffset = 330
        for(grammaticalVoice in grouped) {
            for (grammaticalUsage in grammaticalVoice.value) {
                for (grammaticalMood in grammaticalUsage.value) {
                    val bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
                    val g2d = bufferedImage.createGraphics()

                    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)

                    g2d.color = backgroundColor
                    g2d.fillRect(0,0, width, height)

                    val subHeadingList = mutableListOf("Person", "Singular", "Plural")
                    tableXOffset = 60

                    for (grammaticalTense in grammaticalMood.value) {
                        imageDataList.add(subHeadingList)
                        for (grammaticalPerson in grammaticalTense.value) {
                            val rowData: MutableList<String> = mutableListOf()
                            rowData.add(grammaticalPerson.key.toString())
                            rowData.add(grammaticalPerson.value.getOrNull(0)?.conjugatedString ?: "--")
                            rowData.add(grammaticalPerson.value.getOrNull(1)?.conjugatedString ?: "--")
                            imageDataList.add(rowData)
                        }
                        tableDrawingCreator.drawTable(g2d, imageDataList.size, tableXOffset, tableYOffset, 550, 70)
                        tablePopulator.populateTable(
                            g2d,
                            tableXOffset,
                            tableYOffset,
                            24,
                            70,
                            60,
                            grammaticalTense.key.toString(),
                            imageDataList
                        )
                        tableXOffset += 570
                        imageDataList = mutableListOf()
                    }
                    g2d.color = Color.WHITE
                    g2d.font = Font("Segoe UI", Font.BOLD, 64)
                    g2d.drawString(dmiiWord.baseWordForm, 60, 100)

                    g2d.font = Font("Segoe UI", Font.BOLD, 36)
                    g2d.color = Color.ORANGE
                    g2d.drawString("Verb (Sagnorð)", dmiiWord.baseWordForm.length*55, 100)

                    g2d.color = Color.WHITE
                    g2d.font = Font("Segoe UI", Font.BOLD, 48)
                    g2d.drawString(grammaticalVoice.key.toString(), 60, 180)
                    g2d.font = Font("Segoe UI", Font.BOLD, 36)
                    g2d.drawString(grammaticalUsage.key.toString(), 60, 250)
                    g2d.drawString(grammaticalMood.key.toString(), 60, 300)

                    g2d.dispose()
                    imageList.add(bufferedImage)
                }
            }
        }

        return imageList
    }
}