import kotlinx.coroutines.*
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get

const val BASE_URL = "http://kotlin-book.bignerdranch.com/2e"
const val FLIGHT_ENDPOINT = "$BASE_URL/flight"
const val LOYALITY_ENDPOINT = "$BASE_URL/loyality"

fun main() {
    runBlocking {
        println("Started")
        launch {
            println(fetchFlight())
        }
        println("Finished")
    }
}

suspend fun fetchFlight() : String {
    val client = HttpClient(CIO)
    return client.get<String>(FLIGHT_ENDPOINT)
}