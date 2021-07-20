package sillock.icelandicdiscordbot.helpers

import io.ktor.client.call.*
import io.ktor.client.statement.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class HttpResponseHandler {
    suspend inline fun <reified T>handleResponse(response: HttpResponse) : List<T>  {
        val stringBody : String = response.receive()
        return if(stringBody != "{\"0\":\"\"}") Json.decodeFromString(stringBody) else listOf()
    }
}