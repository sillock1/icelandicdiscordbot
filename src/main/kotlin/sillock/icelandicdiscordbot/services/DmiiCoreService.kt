package sillock.icelandicdiscordbot.services

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import sillock.icelandicdiscordbot.configuration.ApiProperties
import sillock.icelandicdiscordbot.helpers.HttpResponseHandler
import sillock.icelandicdiscordbot.models.serialisations.DmiiWord

@Service
class DmiiCoreService (private val apiProperties: ApiProperties){

    var httpClient: HttpClient = HttpClient(CIO)
    var httpResponseHandler: HttpResponseHandler = HttpResponseHandler()

    @Cacheable("headword")
    fun getHeadword(headword: String): List<DmiiWord> = runBlocking {
        val response: HttpResponse = httpClient.get("${apiProperties.endpoint}/ord/${headword}")
        httpResponseHandler.handleResponse(response)
    }

    @Cacheable("verb")
    fun getVerbConjugation(verb: String): List<DmiiWord> = runBlocking {
        val response: HttpResponse = httpClient.get("${apiProperties.endpoint}/ord/so/${verb}")
        httpResponseHandler.handleResponse(response)
    }

    @Cacheable("declension")
    fun getDeclensions(wordType: String, word: String) : List<DmiiWord> = runBlocking {
        val response: HttpResponse = httpClient.get("${apiProperties.endpoint}/beygingarmynd/${wordType}/${word}")
        httpResponseHandler.handleResponse(response)
    }

    @Cacheable("guid")
    fun getDeclensionsByGuid(guid: String) : List<DmiiWord> = runBlocking {
        val response: HttpResponse = httpClient.get("${apiProperties.endpoint}/ord/${guid}")
        httpResponseHandler.handleResponse(response)
    }
}