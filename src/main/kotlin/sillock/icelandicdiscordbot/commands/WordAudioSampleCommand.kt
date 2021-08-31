package sillock.icelandicdiscordbot.commands

import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionType
import org.openqa.selenium.By
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.helpers.Failure
import sillock.icelandicdiscordbot.services.WebScrapingService
import java.net.URL

@Component
class WordAudioSampleCommand(private val webScrapingService: WebScrapingService): ICommand {
    override val name: String
        get() = "say"
    override val description: String
        get() = "Get audio file for word"
    override val options: List<SlashCommandOption>
        get() = listOf(
            SlashCommandOption.create(SlashCommandOptionType.STRING, "word", "Word to search by", true)
        )

    override fun execute(event: SlashCommandInteraction) {
        event.respondLater().join()
        val wordParam = event.firstOptionStringValue.get()

        val options = FirefoxOptions()
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors", "--silent")
        val driver = FirefoxDriver(options)

        val searchResult = webScrapingService.getWordPage(driver, wordParam)

        if(searchResult is Failure){
            event.createFollowupMessageBuilder().setContent("Word does not exist").send()
            driver.quit()
            return
        }
        WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.ByCssSelector("div[class*='dictionary-entry card flex-grow-1']")))

        val examples = driver.findElements(By.ByCssSelector("source[type='audio/ogg']"))

        if (examples.isEmpty()) {
            event.createFollowupMessageBuilder().setContent("No audio sample available").send()
            driver.quit()
            return
        }
        val file = examples.first().getAttribute("src")
        event.createFollowupMessageBuilder().addAttachment(URL(file)).send()
        driver.quit()

    }
}