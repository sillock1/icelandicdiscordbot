package sillock.icelandicdiscordbot.creators.imagecreators

import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.models.imagegeneration.NounDeclensionForm
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import kotlin.math.roundToInt

@Component
class NounDeclensionImageCreator(private val tableDrawingCreator: TableDrawingCreator){

    /*
        @param g2d The graphics canvas for drawing
        @param offsetX The initial offset from the left side
        @param offsetY The initial offset from the top side
        @param rowSpacing The spacing difference between each row in the table
        @param columnSpacing The spacing difference between each column in the table
        @param tableHeader The title of the table
        @param subHeadingList Each column subheading
        @param contentList A Triplet object list that contains each row's data to print
                           grammatical case, the article form of the noun, and the non-article form of the noun
     */
    fun populateTable(g2d: Graphics2D, offsetX: Int, offsetY: Int, rowSpacing: Int, columnSpacing: Int, tableHeader: String, subHeadingList: List<String>, contentList: MutableList<MutableList<String>>){

        g2d.color = Color.BLACK
        g2d.font= Font("Segoe UI", Font.BOLD, 18)
        g2d.drawString(tableHeader, offsetX + 10, offsetY + (rowSpacing*0.5).roundToInt())
        g2d.color = Color.WHITE
        var offsetXSpacing = columnSpacing
        var offsetYSpacing = (offsetY+(rowSpacing*1.5)).roundToInt()
        for(subHeading in subHeadingList) {
            g2d.drawString(subHeading, (offsetX)+offsetXSpacing, offsetYSpacing)
            offsetXSpacing += columnSpacing * 2
        }

        offsetYSpacing = (offsetY+(rowSpacing*2.5).roundToInt())
        for(row in contentList){
            g2d.drawString(row.elementAt(0).substring(0, 3) + ".", offsetX + 10, offsetYSpacing)
            g2d.drawString(row.elementAt(1), offsetX+(columnSpacing), offsetYSpacing)
            g2d.drawString(row.elementAt(2), offsetX+(columnSpacing*3), offsetYSpacing)
            offsetYSpacing+=rowSpacing
        }
    }

    fun create(title: String, subTitle: String, nounDeclensionFormList: List<NounDeclensionForm>): BufferedImage {
        var width = 500
        val height = 700
        val backgroundColor = Color(44, 47, 51) //Discord embed colour

        val grouped = nounDeclensionFormList.groupBy { it.grammaticalNumber }.mapValues { (_, v) -> v.groupBy { it.grammaticalForm } }
        width = (width * grouped.size)

        var tableXOffset = 60

        val bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        val g2d = bufferedImage.createGraphics()

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2d.color = backgroundColor
        g2d.fillRect(0,0,width, height)

        val subHeadingList = listOf("No Article", "With article")



        for(grammaticalNum in grouped){
            val imageDataList : MutableList<MutableList<String>> = mutableListOf()
            for(grammaticalForm in grammaticalNum.value){
                var rowData : MutableList<String> = mutableListOf()
                val nounDeclensionForms = grammaticalForm.value
                rowData.add(grammaticalForm.key.toString())
                rowData.add(nounDeclensionForms.getOrNull(0)?.inflectedString ?: "Undefined")
                rowData.add(nounDeclensionForms.getOrNull(1)?.inflectedString ?: "Undefined")
                imageDataList.add(rowData)
            }
            tableDrawingCreator.drawTable(g2d, 5, tableXOffset, 260, 400, 70, grammaticalNum.key.toString(),  subHeadingList, imageDataList, ::populateTable)
            tableXOffset+=480
        }

        g2d.color = Color.WHITE
        g2d.font= Font("Segoe UI", Font.BOLD, 64)
        g2d.drawString("Declension", 60, 100)

        g2d.font= Font("Segoe UI", Font.BOLD, 36)
        g2d.drawString(title, 60, 160)

        g2d.font= Font("Segoe UI", Font.BOLD, 24)
        g2d.color = Color.ORANGE
        g2d.drawString(subTitle, 60, 220)

        g2d.dispose()

        return bufferedImage
    }
}