package sillock.icelandicdiscordbot.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.util.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import sillock.icelandicdiscordbot.models.serialisations.WordData
import sillock.icelandicdiscordbot.models.serialisations.WordResult

@Service
class OrdabokService {
    private var httpClient: HttpClient = HttpClient(CIO)
    private val json = Json {
        ignoreUnknownKeys = true
    }

    @Cacheable("wordId")
    fun getWordId(word: String): WordResult = runBlocking {
        val response: HttpResponse = httpClient.get("https://islenskordabok.arnastofnun.is/django/api/es/flettur/?simple=true&leit=${word}")
        json.decodeFromString(response.receive())
    }

    @Cacheable("wordData")
    fun getDataByWordId(wordId: Int): WordData = runBlocking {
        val response: HttpResponse = httpClient.get("https://islenskordabok.arnastofnun.is/django/api/es/fletta/${wordId}")
        json.decodeFromString(response.receive())
    }
    @Cacheable("audioFile")
    fun getAudioFile(trimmedWordId: String, audioId: Int): ByteArray = runBlocking {
        val response: HttpResponse = httpClient.get("https://islenskordabok.arnastofnun.is/islex-files/audio/${trimmedWordId}/${audioId}.ogg")
        response.receive<HttpResponse>().content.toByteArray()
    }
    @Cacheable("similar")
    fun getSimilarWordsByWordId(wordId: Int): WordResult = runBlocking {
        val response: HttpResponse = httpClient.get("https://islex.arnastofnun.is/django/api/es/similar/${wordId}")
        json.decodeFromString(response.receive())
    }
}