package sillock.icelandicdiscordbot.creators.imagecreators

import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.models.embedmodels.InflectedForm
import sillock.icelandicdiscordbot.models.embedmodels.NounDeclensionForm
import sillock.icelandicdiscordbot.models.enums.GrammaticalForm
import sillock.icelandicdiscordbot.models.enums.GrammaticalNumber
import java.awt.Color
import java.awt.Font
import java.awt.RenderingHints
import java.awt.image.BufferedImage

@Component
class NounDeclensionImageCreator{
    fun create(subTitle: String, nounDeclensionFormList: List<NounDeclensionForm>): BufferedImage {
        var drawingString: String
        val bufferedImage = BufferedImage(1000, 700, BufferedImage.TYPE_INT_RGB)
        val g2d = bufferedImage.createGraphics()

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.color = Color(44, 47, 51) //Discord embed colour
        g2d.fillRect(0,0,1000, 700)
        g2d.color = Color(192, 192, 192)//light grey

        g2d.drawLine(60, 260, 60, 670) //First vertical
        g2d.drawLine(460, 260, 460, 670) //second vertical
        g2d.drawLine(540, 260, 540, 670) //third vertical
        g2d.drawLine(940, 260, 940, 670) //Fourth vertical
        g2d.drawLine(60, 390, 460, 390)   //First row
        g2d.drawLine(540, 390, 940, 390)   //First row

        g2d.drawLine(60, 460, 460, 460)   //second row
        g2d.drawLine(540, 460, 940, 460)   //second row

        g2d.drawLine(60, 530, 460, 530)   //second row
        g2d.drawLine(540, 530, 940, 530)   //second row

        g2d.drawLine(60, 600, 460, 600)   //second row
        g2d.drawLine(540, 600, 940, 600)   //second row

        g2d.drawLine(60, 670, 460, 670)   //second row
        g2d.drawLine(540, 670, 940, 670)   //second row


        g2d.fillRect(60, 260, 400, 60)
        g2d.fillRect(540, 260, 400, 60)
        g2d.color = Color.WHITE
        g2d.font= Font("Segoe UI", Font.BOLD, 72)
        g2d.drawString("Noun declension", 60, 100)
        g2d.font= Font("Segoe UI", Font.BOLD, 24)


        drawingString = nounDeclensionFormList.first{x -> !x.withArticle && x.grammaticalForm == GrammaticalForm.Nominative && x.grammaticalNumber == GrammaticalNumber.Singular}.inflectedString
        g2d.drawString(drawingString, 60, 160)
        g2d.font= Font("Segoe UI", Font.BOLD, 20)
        g2d.color = Color.ORANGE

        g2d.drawString(subTitle, 60, 220)
        g2d.color = Color.BLACK
        g2d.font= Font("Segoe UI", Font.BOLD, 18)
        g2d.drawString("Singular (Eintala)", 70, 300)
        g2d.drawString("Plural (Fleirtala)", 550, 300)
        g2d.color = Color.WHITE
        g2d.drawString("án greinis", 140, 360)
        g2d.drawString("með greini", 280, 360)

        g2d.drawString("Nf.", 70, 430)
        g2d.drawString("Þf.", 70, 500)
        g2d.drawString("Þgf.", 70, 570)
        g2d.drawString("Ef.", 70, 640)

        g2d.drawString(drawingString, 140, 430)
        drawingString = nounDeclensionFormList.first{x -> x.withArticle && x.grammaticalForm == GrammaticalForm.Nominative && x.grammaticalNumber == GrammaticalNumber.Singular}.inflectedString
        g2d.drawString(drawingString, 280, 430)

        drawingString = nounDeclensionFormList.first{x -> !x.withArticle && x.grammaticalForm == GrammaticalForm.Accusative && x.grammaticalNumber == GrammaticalNumber.Singular}.inflectedString
        g2d.drawString(drawingString, 140, 500)
        drawingString = nounDeclensionFormList.first{x -> x.withArticle && x.grammaticalForm == GrammaticalForm.Accusative && x.grammaticalNumber == GrammaticalNumber.Singular}.inflectedString
        g2d.drawString(drawingString, 280, 500)

        drawingString = nounDeclensionFormList.first{x -> x.withArticle && x.grammaticalForm == GrammaticalForm.Dative && x.grammaticalNumber == GrammaticalNumber.Singular}.inflectedString
        g2d.drawString(drawingString, 140, 570)
        drawingString = nounDeclensionFormList.first{x -> !x.withArticle && x.grammaticalForm == GrammaticalForm.Dative && x.grammaticalNumber == GrammaticalNumber.Singular}.inflectedString
        g2d.drawString(drawingString, 280, 570)

        drawingString = nounDeclensionFormList.first{x -> x.withArticle && x.grammaticalForm == GrammaticalForm.Genitive && x.grammaticalNumber == GrammaticalNumber.Singular}.inflectedString
        g2d.drawString(drawingString, 140, 640)
        drawingString = nounDeclensionFormList.first{x -> !x.withArticle && x.grammaticalForm == GrammaticalForm.Genitive && x.grammaticalNumber == GrammaticalNumber.Singular}.inflectedString
        g2d.drawString(drawingString, 280, 640)

        g2d.drawString("án greinis", 620, 360)
        g2d.drawString("með greini", 760, 360)

        drawingString = nounDeclensionFormList.first{x -> x.withArticle && x.grammaticalForm == GrammaticalForm.Nominative && x.grammaticalNumber == GrammaticalNumber.Plural}.inflectedString
        g2d.drawString(drawingString, 620, 430)
        drawingString = nounDeclensionFormList.first{x -> !x.withArticle && x.grammaticalForm == GrammaticalForm.Nominative && x.grammaticalNumber == GrammaticalNumber.Plural}.inflectedString
        g2d.drawString(drawingString, 760, 430)

        drawingString = nounDeclensionFormList.first{x -> x.withArticle && x.grammaticalForm == GrammaticalForm.Accusative && x.grammaticalNumber == GrammaticalNumber.Plural}.inflectedString
        g2d.drawString(drawingString, 620, 500)
        drawingString = nounDeclensionFormList.first{x -> !x.withArticle && x.grammaticalForm == GrammaticalForm.Accusative && x.grammaticalNumber == GrammaticalNumber.Plural}.inflectedString
        g2d.drawString(drawingString, 760, 500)

        drawingString = nounDeclensionFormList.first{x -> x.withArticle && x.grammaticalForm == GrammaticalForm.Dative && x.grammaticalNumber == GrammaticalNumber.Plural}.inflectedString
        g2d.drawString(drawingString, 620, 570)
        drawingString = nounDeclensionFormList.first{x -> !x.withArticle && x.grammaticalForm == GrammaticalForm.Dative && x.grammaticalNumber == GrammaticalNumber.Plural}.inflectedString
        g2d.drawString(drawingString, 760, 570)

        drawingString = nounDeclensionFormList.first{x -> x.withArticle && x.grammaticalForm == GrammaticalForm.Genitive && x.grammaticalNumber == GrammaticalNumber.Plural}.inflectedString
        g2d.drawString(drawingString, 620, 640)
        drawingString = nounDeclensionFormList.first{x -> !x.withArticle && x.grammaticalForm == GrammaticalForm.Genitive && x.grammaticalNumber == GrammaticalNumber.Plural}.inflectedString
        g2d.drawString(drawingString, 760, 640)

        g2d.drawString("Nf.", 550, 430)
        g2d.drawString("Þf.", 550, 500)
        g2d.drawString("Þgf.", 550, 570)
        g2d.drawString("Ef.", 550, 640)

        g2d.dispose()
        return bufferedImage
    }
}