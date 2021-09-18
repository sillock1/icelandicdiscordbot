package sillock.icelandicdiscordbot.services

import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import sillock.icelandicdiscordbot.configuration.ApiEndpoint
import sillock.icelandicdiscordbot.helpers.HttpRequestClient
import sillock.icelandicdiscordbot.helpers.OperationResult
import sillock.icelandicdiscordbot.helpers.Success
import sillock.icelandicdiscordbot.models.serialisations.DmiiWord

@SpringBootTest
class DmiiCoreServiceTests {

    @Test
    fun headWordTest(){
        val apiEndpoint = ApiEndpoint("https://dmiistubendpoint.com", "https://ordabokstubendpoint.com", "https://islexstubendpoint.com")
        val httpRequestClient = mockk<HttpRequestClient>(relaxed = true)
        val word = "test"
        every { runBlocking {httpRequestClient.handle<String>("${apiEndpoint.dmiicore}/ord/${word}")} }

        val service = DmiiCoreService(apiEndpoint, httpRequestClient)
        val result = service.getHeadword(word)

        result.shouldBeInstanceOf<Success<List<DmiiWord>>>()

        coVerify{
            httpRequestClient.handle<String>("${apiEndpoint.dmiicore}/ord/${word}")
        }

    }
}