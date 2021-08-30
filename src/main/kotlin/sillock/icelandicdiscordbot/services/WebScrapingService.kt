package sillock.icelandicdiscordbot.services

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.stereotype.Service
import sillock.icelandicdiscordbot.helpers.Failure
import sillock.icelandicdiscordbot.helpers.OperationResult
import sillock.icelandicdiscordbot.helpers.Success

@Service
class WebScrapingService {

    fun getWordPage(driver: RemoteWebDriver, wordParam: String): OperationResult<RemoteWebDriver, String> {
        val wait = WebDriverWait(driver, 3)
        driver.get("https://islenskordabok.arnastofnun.is")

        driver.findElement(By.name("searchBoxInput")).sendKeys(wordParam + Keys.ENTER)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.ByClassName("breadcrumb")))
        val tableElements = driver.findElements(By.ByCssSelector("a[href*='\\/ord\\/']"))
        //TODO: Fix issue where this page isn't needed, loads straight to word page
        if(tableElements.isNotEmpty()) {
            tableElements.first().click()
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.ByClassName("dict-view")))
            return Success(driver)
        }
        return Failure("Word page does not exist")
    }
}