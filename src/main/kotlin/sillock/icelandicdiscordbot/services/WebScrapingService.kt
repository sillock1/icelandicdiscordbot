package sillock.icelandicdiscordbot.services

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import org.openqa.selenium.By
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import sillock.icelandicdiscordbot.helpers.Failure
import sillock.icelandicdiscordbot.helpers.HttpResponseHandler
import sillock.icelandicdiscordbot.helpers.OperationResult
import sillock.icelandicdiscordbot.helpers.Success
import sillock.icelandicdiscordbot.models.serialisations.Word

@Service
class WebScrapingService {
    var httpClient: HttpClient = HttpClient(CIO)
    var httpResponseHandler: HttpResponseHandler = HttpResponseHandler()

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


    @Cacheable("wordId")
    fun getWordId(word: String): List<Word> = runBlocking {
        val response: HttpResponse = httpClient.get("https://islenskordabok.arnastofnun.is/django/api/es/flettur/?simple=true&leit=${word}")
        httpResponseHandler.handleResponse(response)
    }
    @Cacheable("wordData")
    fun getDataByWordId(wordId: Int): List<Word> = runBlocking {
        val response: HttpResponse = httpClient.get("https://islenskordabok.arnastofnun.is/django/api/es/fletta/${wordId}")
        httpResponseHandler.handleResponse(response)
    }
    @Cacheable("audioFile")
    fun getAudioFile(trimmedWordId: Int, audioId: Int): List<Word> = runBlocking {
        val response: HttpResponse = httpClient.get("https://islenskordabok.arnastofnun.is/islex-files/audio/${trimmedWordId}/${audioId}.ogg")
        httpResponseHandler.handleResponse(response)
    }
    @Cacheable("similar")
    fun getSimilarWordsByWordId(wordId: Int): List<Word> = runBlocking {
        val response: HttpResponse = httpClient.get("https://islex.arnastofnun.is/django/api/es/similar/${wordId}")
        httpResponseHandler.handleResponse(response)
    }
}