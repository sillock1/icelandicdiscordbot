package sillock.icelandicdiscordbot.helpers

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class HttpRequestClient {

    var httpClient: HttpClient = HttpClient(CIO){
        expectSuccess = false
        install(HttpTimeout) {
            requestTimeoutMillis = 30000
        }
    }

    final suspend inline fun <reified T> handle(endpoint: String): OperationResult<T, String>{
        val logger: Logger = LoggerFactory.getLogger(this::class.java)
        try {
            val response: HttpResponse = httpClient.get(endpoint)

            if (response.status != HttpStatusCode.OK) {
                return Failure("Failed to fetch data ${response.status.value}")
            }
            val content = response.receive<T>()
            return Success(content)
        }
        catch (e: HttpRequestTimeoutException) {
            logger.error(e.message.toString())
            return Failure("Timed out")
        }
    }
}