import kotlinx.coroutines.*
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get

const val BASE_URL = "http://kotlin-book.bignerdranch.com/2e"
const val FLIGHT_ENDPOINT = "$BASE_URL/flight"
const val LOYALITY_ENDPOINT = "$BASE_URL/loyalty"

fun main() {
    runBlocking {
        println("Started")
        launch {
            println(fetchFlight("Arzhang"))
        }
        println("Finished")
    }
}

suspend fun fetchFlight(passengerName: String) : FlightStatus {
    val client = HttpClient(CIO)
    val flightResponse = client.get<String>(FLIGHT_ENDPOINT)
    val loyaltyResponse = client.get<String>(LOYALITY_ENDPOINT)

    return FlightStatus.parse(
        passengerName = passengerName,
        flightResponse = flightResponse,
        loyaltyResponse = loyaltyResponse
    )
}