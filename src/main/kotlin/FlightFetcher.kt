import kotlinx.coroutines.*
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get

const val BASE_URL = "http://kotlin-book.bignerdranch.com/2e"
const val FLIGHT_ENDPOINT = "$BASE_URL/flight"
const val LOYALITY_ENDPOINT = "$BASE_URL/loyalty"

suspend fun fetchFlight(passengerName: String) : FlightStatus = coroutineScope {
    val client = HttpClient(CIO)

    val flightResponse = async {
       println("Started fetching Flight info.")
        var res: String
        do {
            res = client.get<String>(FLIGHT_ENDPOINT)
        } while ((!FlightStatus.isCanceled(res)))
        res.also {println("Finished fetching Flight info.") }
    }

    val loyaltyResponse = async {
        println("Started fetching Loyalty info.")
        client.get<String>(LOYALITY_ENDPOINT).also { println("Finished fetching Loyalty info.") }

    }

    delay(500)
    println("Combining flight data")

    FlightStatus.parse(
        passengerName = passengerName,
        flightResponse = flightResponse.await(),
        loyaltyResponse = loyaltyResponse.await()
    )
}
