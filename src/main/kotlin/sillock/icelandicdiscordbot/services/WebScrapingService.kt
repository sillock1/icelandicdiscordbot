package sillock.icelandicdiscordbot.services

import org.openqa.selenium.By
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

        driver.findElement(By.name("searchBoxInput")).sendKeys(wordParam)
        driver.findElement(By.cssSelector("button[class*='btn btn-primary']")).click()

        if(driver.currentUrl.contains("/ord/")){
            return Success(driver)
        }

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.ByCssSelector("table[class*='list-group mb-4 dictionary-list']")))
        val tableElements = driver.findElements(By.ByCssSelector("a[href^='/ord']"))
        if(tableElements.isNotEmpty()) {
            tableElements.first().click()
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.ByClassName("dict-view")))
            return Success(driver)
        }
        return Failure("Word page does not exist")
    }
}