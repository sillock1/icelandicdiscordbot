package sillock.icelandicdiscordbot.creators.imagecreators

import org.springframework.stereotype.Component
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import kotlin.math.roundToInt

@Component
class TablePopulator {
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
    fun populateTable(g2d: Graphics2D, offsetX: Int, offsetY: Int, rowSpacing: Int, columnSpacing: Int, tableHeader: String, contentList: MutableList<MutableList<String>>) : Graphics2D{

        g2d.color = Color.BLACK
        g2d.font= Font("Segoe UI", Font.BOLD, 18)
        g2d.drawString(tableHeader, offsetX + 10, offsetY + (rowSpacing*0.5).roundToInt())
        g2d.color = Color.WHITE
        var offsetYSpacing = (offsetY+(rowSpacing*1.5).roundToInt())
        var offsetXSpacing = 10
        for(row in contentList){
            for(elem in row){
                g2d.drawString(elem, (offsetX)+offsetXSpacing, offsetYSpacing)
                offsetXSpacing += columnSpacing * 2
            }
            offsetXSpacing = 10
            offsetYSpacing+=rowSpacing
        }



        return g2d
    }
}