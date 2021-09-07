package sillock.icelandicdiscordbot.services

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
import sillock.icelandicdiscordbot.models.serialisations.DmiiWord

@Service
@EnableCaching
class DmiiCoreService (private val apiEndpoint: ApiEndpoint,
                       private val httpRequestClient: HttpRequestClient){

    val json = Json {
        ignoreUnknownKeys = true
    }

    @Cacheable(value = ["headword"], unless = "#result instanceof T(sillock.icelandicdiscordbot.helpers.Failure)")
    fun getHeadword(headword: String): OperationResult<List<DmiiWord>, String> {
        return when (val requestResult = runBlocking{
            httpRequestClient.handle<String>("${apiEndpoint.dmiicore}/ord/${headword}")
        }) {
            is Failure -> {
                Failure(requestResult.reason)
            }
            is Success -> {
                val wordResult = if(requestResult.value != "{\"0\":\"\"}") json.decodeFromString<List<DmiiWord>>(requestResult.value) else listOf()
                Success(wordResult)
            }
        }
    }

    @Cacheable(value = ["verb"], unless = "#result instanceof T(sillock.icelandicdiscordbot.helpers.Failure)")
    fun getVerbConjugation(verb: String): OperationResult<List<DmiiWord>, String> {
        return when (val requestResult = runBlocking{
            httpRequestClient.handle<String>("${apiEndpoint.dmiicore}/ord/so/${verb}")
        }) {
            is Failure -> {
                Failure(requestResult.reason)
            }
            is Success -> {
                val wordResult = if(requestResult.value != "{\"0\":\"\"}") json.decodeFromString<List<DmiiWord>>(requestResult.value) else listOf()
                Success(wordResult)
            }
        }
    }

    @Cacheable(value = ["declension"], unless = "#result instanceof T(sillock.icelandicdiscordbot.helpers.Failure)")
    fun getDeclensions(wordType: String, word: String) : OperationResult<List<DmiiWord>, String> {
        return when (val requestResult = runBlocking{
            httpRequestClient.handle<String>("${apiEndpoint.dmiicore}/beygingarmynd/${wordType}/${word}")
        }) {
            is Failure -> {
                Failure(requestResult.reason)
            }
            is Success -> {
                val wordResult = if(requestResult.value != "{\"0\":\"\"}") json.decodeFromString<List<DmiiWord>>(requestResult.value) else listOf()
                Success(wordResult)
            }
        }
    }

    @Cacheable(value = ["guid"], unless = "#result instanceof T(sillock.icelandicdiscordbot.helpers.Failure)")
    fun getDeclensionsByGuid(guid: String) : OperationResult<List<DmiiWord>, String> {
        return when (val requestResult = runBlocking{
            httpRequestClient.handle<String>("${apiEndpoint.dmiicore}/ord/${guid}")
        }) {
            is Failure -> {
                Failure(requestResult.reason)
            }
            is Success -> {
                val wordResult = if(requestResult.value != "{\"0\":\"\"}") json.decodeFromString<List<DmiiWord>>(requestResult.value) else listOf()
                Success(wordResult)
            }
        }
    }
}