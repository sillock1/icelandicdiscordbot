package sillock.icelandicdiscordbot.services

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import sillock.icelandicdiscordbot.configuration.ApiProperties
import sillock.icelandicdiscordbot.helpers.HttpResponseHandler
import sillock.icelandicdiscordbot.models.serialisations.Word

@Service
class DmiiCoreService (private val apiProperties: ApiProperties){

    var httpClient: HttpClient = HttpClient(CIO)
    var httpResponseHandler: HttpResponseHandler = HttpResponseHandler()

    fun getHeadword(headword: String): List<Word> = runBlocking {
        val response: HttpResponse = httpClient.get("${apiProperties.endpoint}/ord/${headword}")
        httpResponseHandler.handleResponse(response)
    }

    fun getVerbConjugation(verb: String): List<Word> = runBlocking {
        val response: HttpResponse = httpClient.get("${apiProperties.endpoint}/ord/so/${verb}")
        httpResponseHandler.handleResponse(response)
    }

    fun getDeclensions(wordType: String, word: String) : List<Word> = runBlocking {
        val response: HttpResponse = httpClient.get("${apiProperties.endpoint}/beygingarmynd/${wordType}/${word}")
        httpResponseHandler.handleResponse(response)
    }

    fun getDeclensionsByGuid(guid: String) : List<Word> = runBlocking {
        val response: HttpResponse = httpClient.get("${apiProperties.endpoint}/ord/${guid}")
        httpResponseHandler.handleResponse(response)
    }
}