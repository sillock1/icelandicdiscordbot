package sillock.icelandicdiscordbot.creators.imagecreators

import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.models.embedmodels.InflectedForm
import sillock.icelandicdiscordbot.models.embedmodels.NounDeclensionForm
import sillock.icelandicdiscordbot.models.enums.GrammaticalForm
import sillock.icelandicdiscordbot.models.enums.GrammaticalNumber
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import kotlin.math.roundToInt
import kotlin.math.sin

@Component
class NounDeclensionImageCreator{
    val width = 1000
    val height = 700
    val backgroundColor = Color(44, 47, 51) //Discord embed colour
    val tableBorderColour = Color(192, 192, 192)

    fun populateTable(g2d: Graphics2D, offsetX: Int, offsetY: Int, rowSpacing: Int, columnSpacing: Int, tableHeader: String, subHeadingList: List<String>, contentList: List<Triple<String, String, String>>){

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
            g2d.drawString(row.first.substring(0, 3) + ".", offsetX + 10, offsetYSpacing)
            g2d.drawString(row.second, offsetX+(columnSpacing), offsetYSpacing)
            g2d.drawString(row.third, offsetX+(columnSpacing*3), offsetYSpacing)
            offsetYSpacing+=rowSpacing
        }
    }

    fun drawTable(g2d: Graphics2D, numberOfRows: Int, offsetX: Int, offsetY: Int, sizeX: Int, rowSpacing: Int, tableHeader: String, subHeadingList: List<String>, contentList: List<Triple<String, String, String>>,
                  populateTable: (g2d: Graphics2D, offsetX: Int, offsetY: Int, rowSpacing: Int, columnSpacing: Int, tableHeader: String, subHeadingList: List<String>, contentList: List<Triple<String, String, String>>) -> Unit){
        g2d.color = tableBorderColour//light grey
        val sizeY = ((numberOfRows+1)*rowSpacing)+offsetY
        g2d.fillRect(offsetX, offsetY, sizeX, rowSpacing)
        g2d.drawLine(offsetX, offsetY, offsetX, sizeY) //First vertical
        g2d.drawLine(offsetX+sizeX, offsetY, offsetX+sizeX, sizeY) //second vertical

        for(i in 1..numberOfRows+1) {
            g2d.drawLine(offsetX, offsetY+(i*rowSpacing), offsetX+sizeX, offsetY+(i*rowSpacing))
        }
        populateTable(g2d, offsetX, offsetY, rowSpacing, (sizeX*0.2).roundToInt(), tableHeader, subHeadingList, contentList)
    }

    fun create(subTitle: String, nounDeclensionFormList: List<NounDeclensionForm>): BufferedImage {
        var drawingString: String
        val bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        val g2d = bufferedImage.createGraphics()

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2d.color = backgroundColor
        g2d.fillRect(0,0,width, height)

        val subHeadingList = listOf("No Article", "With article")
        val singleList = nounDeclensionFormList.filter{x -> x.grammaticalNumber == GrammaticalNumber.Singular}

        var grouped = singleList.groupBy { it.grammaticalForm }

        var tripleList : MutableList<Triple<String, String, String>> = mutableListOf()
        for(group in grouped) {
            val forms = grouped.getValue(group.key)
            tripleList.add(Triple(group.key.toString(), forms.getOrNull(0)?.inflectedString ?: "Undefined", forms.getOrNull(1)?.inflectedString ?: "Undefined"))
        }

        drawTable(g2d, 5, 60, 260, 400, 70, "Singular",  subHeadingList, tripleList, ::populateTable)

        val pluralList = nounDeclensionFormList.filter{x -> x.grammaticalNumber == GrammaticalNumber.Plural}
        tripleList = mutableListOf()
        grouped = pluralList.groupBy({it.grammaticalForm})
        for(group in grouped) {
            val forms = grouped.getValue(group.key)
            tripleList.add(Triple(group.key.toString(), forms.getOrNull(0)?.inflectedString ?: "Undefined", forms.getOrNull(1)?.inflectedString ?: "Undefined"))
        }

        drawTable(g2d, 5, 540, 260, 400, 70, "Plural",  subHeadingList, tripleList, ::populateTable)


        g2d.color = Color.WHITE
        g2d.font= Font("Segoe UI", Font.BOLD, 72)
        g2d.drawString("Noun declension", 60, 100)
        g2d.font= Font("Segoe UI", Font.BOLD, 24)
        g2d.color = Color.ORANGE
        g2d.drawString(subTitle, 60, 220)

        g2d.dispose()

        return bufferedImage
    }
}