package sillock.icelandicdiscordbot

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import sillock.icelandicdiscordbot.configuration.ApiProperties
import sillock.icelandicdiscordbot.models.Word

@Service
class DmiiCoreService (private val apiProperties: ApiProperties){
    var httpClient: HttpClient = HttpClient(CIO)

    fun getHeadword(headword: String): List<Word> = runBlocking {
        val response: HttpResponse = httpClient.get("${apiProperties.endpoint}/ord/${headword}")
        val stringBody: String = response.receive()
        if(stringBody != "{\"0\":\"\"}") Json.decodeFromString(stringBody) else listOf()
    }

    fun getVerbConjugation(verb: String): List<Word> = runBlocking {
        val response: HttpResponse = httpClient.get("${apiProperties.endpoint}/ord/so/${verb}")
        val stringBody : String = response.receive()
        if(stringBody != "{\"0\":\"\"}") Json.decodeFromString(stringBody) else listOf()
    }

    fun getDeclensions(wordType: String, word: String) : List<Word> = runBlocking {
        val response: HttpResponse = httpClient.get("${apiProperties.endpoint}/beygingarmynd/${wordType}/${word}")
        val stringBody : String = response.receive()
        if(stringBody != "{\"0\":\"\"}") Json.decodeFromString(stringBody) else listOf()
    }
}