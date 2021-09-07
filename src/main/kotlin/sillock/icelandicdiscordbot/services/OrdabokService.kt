package sillock.icelandicdiscordbot.services

import io.ktor.client.statement.*
import io.ktor.util.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.EnableCaching
import org.springframework.stereotype.Service
import sillock.icelandicdiscordbot.configuration.ApiEndpoint
import sillock.icelandicdiscordbot.helpers.Failure
import sillock.icelandicdiscordbot.helpers.HttpRequestClient
import sillock.icelandicdiscordbot.helpers.OperationResult
import sillock.icelandicdiscordbot.helpers.Success
import sillock.icelandicdiscordbot.models.serialisations.WordData
import sillock.icelandicdiscordbot.models.serialisations.WordResult

@Service
@EnableCaching
class OrdabokService(private val apiEndpoint: ApiEndpoint,
                     private val httpRequestClient: HttpRequestClient) {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    @Cacheable(value = ["wordId"], unless = "#result instanceof T(sillock.icelandicdiscordbot.helpers.Failure)")
    fun getWordId(word: String): OperationResult<WordResult, String> {
        return when (val requestResult = runBlocking {
            httpRequestClient.handle<String>("${apiEndpoint.ordabok}/django/api/es/flettur/?simple=true&leit=${word}")
        }) {
            is Failure -> {Failure(requestResult.reason)}
            is Success -> {
                val wordResult = json.decodeFromString<WordResult>(requestResult.value)
                Success(wordResult)
                }
            }
    }

    @Cacheable(value = ["wordData"], unless = "#result instanceof T(sillock.icelandicdiscordbot.helpers.Failure)")
    fun getDataByWordId(wordId: Int): OperationResult<WordData, String> {
        return when (val requestResult = runBlocking{
            httpRequestClient.handle<String>("${apiEndpoint.ordabok}/django/api/es/fletta/${wordId}")
        }) {
            is Failure -> {Failure(requestResult.reason)}
            is Success -> {
                val wordDataResult = json.decodeFromString<WordData>(requestResult.value)
                Success(wordDataResult)
            }
        }
    }

    @Cacheable(value = ["audioFile"], unless = "#result instanceof T(sillock.icelandicdiscordbot.helpers.Failure)")
    fun getAudioFile(trimmedWordId: String, audioId: Int): OperationResult<ByteArray, String>  {
        return when (val requestResult = runBlocking {
            httpRequestClient.handle<HttpResponse>("${apiEndpoint.ordabok}/islex-files/audio/${trimmedWordId}/${audioId}.ogg")
        }) {
            is Failure -> {Failure(requestResult.reason + " for audio")}
            is Success -> {
                val audioFileResult = runBlocking{ requestResult.value.content.toByteArray() }
                Success(audioFileResult)
            }
        }
    }
    @Cacheable(value = ["similar"], unless = "#result instanceof T(sillock.icelandicdiscordbot.helpers.Failure)")
    fun getSimilarWordsByWordId(wordId: Int): OperationResult<WordResult, String> {
        return when (val requestResult = runBlocking {
            httpRequestClient.handle<String>("${apiEndpoint.islex}/django/api/es/similar/${wordId}")
        }) {
            is Failure -> {Failure(requestResult.reason)}
            is Success -> {
                val wordResult = json.decodeFromString<WordResult>(requestResult.value)
                Success(wordResult)
            }
        }
    }
}