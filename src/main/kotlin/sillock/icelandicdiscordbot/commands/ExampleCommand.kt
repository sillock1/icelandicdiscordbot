package sillock.icelandicdiscordbot.commands

import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionType
import org.openqa.selenium.By
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.helpers.Failure
import sillock.icelandicdiscordbot.services.WebScrapingService

@Component
class ExampleCommand(private val webScrapingService: WebScrapingService): ICommand {
    override val name: String
        get() = "example"
    override val description: String
        get() = "Get usage of word"
    override val options: List<SlashCommandOption>
        get() = listOf(
            SlashCommandOption.create(SlashCommandOptionType.STRING, "word", "Word to search by", true),
            SlashCommandOption.create(SlashCommandOptionType.INTEGER, "amount", "Amount of examples", false)
        )
    private val defaultExampleCount: Int = 6


    override fun execute(event: SlashCommandInteraction) {
        event.respondLater().join()
        val wordParam = event.firstOptionStringValue.get()
        val exampleCount = if (!event.secondOptionIntValue.isEmpty) event.secondOptionIntValue.get() else defaultExampleCount

        val options = FirefoxOptions()
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors", "--silent")
        val driver = FirefoxDriver(options)

        val results = webScrapingService.getWordPage(driver, wordParam)
        if(results is Failure){
            event.createFollowupMessageBuilder().setContent(results.reason).send()
            driver.quit()
        }
        else{
            val examples = driver.findElements(By.ByCssSelector("div[class*='dict-item dict-daemi']"))

            if(examples.isEmpty()){
                event.createFollowupMessageBuilder().setContent("No examples available").send()
                driver.quit()
                return
            }
            var result = ""
            examples.take(exampleCount).forEach { x ->
                result += x.text + "\n"
            }
            event.createFollowupMessageBuilder().setContent(result).send()
            driver.quit()
        }
    }
}