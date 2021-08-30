package sillock.icelandicdiscordbot.commands

import org.javacord.api.entity.message.MessageBuilder
import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionType
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class ExampleCommand: ICommand {
    override val name: String
        get() = "example"
    override val description: String
        get() = "Get usage of word"
    override val options: List<SlashCommandOption>
        get() = listOf(
            SlashCommandOption.create(SlashCommandOptionType.STRING, "word", "Word to search by", true)
        )


    override fun execute(event: SlashCommandInteraction) {

        event.createImmediateResponder().setContent("Examples:").respond()
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\jpres\\Downloads\\geckodriver-v0.29.1-win64\\geckodriver.exe")
        val driver = FirefoxDriver()
        val wait = WebDriverWait(driver, 10)
        driver.get("https://islenskordabok.arnastofnun.is")
        driver.findElement(By.name("searchBoxInput")).sendKeys(event.firstOptionStringValue.get() + Keys.ENTER)
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS)
      //  wait.until(ExpectedConditions.presenceOfElementLocated(By.ByCssSelector("table[class*='list-group.mb-4.dictionary-list']")))
        driver.findElement(By.ByCssSelector("a[href*='ord']")).click()
        val firstResult = driver.findElement(By.ByCssSelector("div[class*='dict-item dict-section']"))
        val examples = firstResult.findElements(By.ByCssSelector("div[class*='dict-item dict-daemi']"))

        //firstResult.findElements(By.tagName("tr")).first().findElements(By.tagName("td")).first().findElements(By.tagName("a href")).first()
        var messageBuilder = MessageBuilder()
        var result: String = ""
        examples.forEach { x ->
            result += x.text + "\n"
        }
        messageBuilder.setContent(result).send(event.channel.get())
    }
}