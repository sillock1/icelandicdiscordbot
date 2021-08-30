package sillock.icelandicdiscordbot.commands

import org.javacord.api.entity.message.MessageBuilder
import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionType
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.ExpectedConditions
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
            SlashCommandOption.create(SlashCommandOptionType.STRING, "word", "Word to search by", true),
            SlashCommandOption.create(SlashCommandOptionType.INTEGER, "amount", "Amount of examples", false)
        )


    override fun execute(event: SlashCommandInteraction) {
        event.createImmediateResponder().setContent("Examples:").respond()
        val exampleCount = if (!event.secondOptionIntValue.isEmpty) event.secondOptionIntValue.get() else 6
        var messageBuilder = MessageBuilder()
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\jpres\\Downloads\\geckodriver-v0.29.1-win64\\geckodriver.exe")
        val driver = FirefoxDriver()
        val wait = WebDriverWait(driver, 10)
        driver.get("https://islenskordabok.arnastofnun.is")
        driver.findElement(By.name("searchBoxInput")).sendKeys(event.firstOptionStringValue.get() + Keys.ENTER)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.ByCssSelector("a[href*='ord']")))
        val searchElement = driver.findElements(By.ByClassName("breadcrumb"))
        if(searchElement.isNotEmpty()) {
            val element = driver.findElement(By.ByCssSelector("a[href*='ord']"))
            element?.click()
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.ByClassName("dict-view")))
        }

        val examples = driver.findElements(By.ByCssSelector("div[class*='dict-item dict-daemi']"))

        if(examples.isEmpty()){
            messageBuilder.setContent("No examples available").send(event.channel.get())
            return
        }

        var result: String = ""
        examples.take(exampleCount).forEach { x ->
            result += x.text + "\n"
        }
        messageBuilder.setContent(result).send(event.channel.get())
    }
}