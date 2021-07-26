package sillock.icelandicdiscordbot.creators.imagecreators

import org.springframework.stereotype.Component
import java.awt.Color
import java.awt.Graphics2D
import kotlin.math.roundToInt

@Component
class TableDrawingCreator {

    private val tableBorderColour = Color(192, 192, 192)

    fun drawTable(g2d: Graphics2D, numberOfRows: Int, offsetX: Int, offsetY: Int, sizeX: Int, rowSpacing: Int, tableHeader: String, subHeadingList: List<String>, contentList: MutableList<MutableList<String>>,
                  populateTable: (g2d: Graphics2D, offsetX: Int, offsetY: Int, rowSpacing: Int, columnSpacing: Int, tableHeader: String, subHeadingList: List<String>, contentList: MutableList<MutableList<String>>) -> Unit)
    : Graphics2D{
        g2d.color = tableBorderColour//light grey
        val sizeY = ((numberOfRows+1)*rowSpacing)+offsetY
        g2d.fillRect(offsetX, offsetY, sizeX, rowSpacing)
        g2d.drawLine(offsetX, offsetY, offsetX, sizeY) //First vertical
        g2d.drawLine(offsetX+sizeX, offsetY, offsetX+sizeX, sizeY) //second vertical

        for(i in 1..numberOfRows+1) {
            g2d.drawLine(offsetX, offsetY+(i*rowSpacing), offsetX+sizeX, offsetY+(i*rowSpacing))
        }
        populateTable(g2d, offsetX, offsetY, rowSpacing, (sizeX*0.2).roundToInt(), tableHeader, subHeadingList, contentList)

        return g2d
    }
}