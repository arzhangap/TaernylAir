import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        println("Getting the latest flights info...")
        val flights = fetchFligts()
        val flightDescriptions = flights.joinToString {
            "${it.passengerName} (${it.flightNumber})"
        }
        println("Found flights for $flightDescriptions")

        flights.forEach {
            watchFlight(it)
        }
    }
}

fun watchFlight(initialFlight: FlightStatus) {
    val currentFlight: Flow<FlightStatus> = flow {
        var flight = initialFlight
        repeat(5) {
            emit(flight)
            delay(1000)
            flight = flight.copy(
                departureTimeInMinutes = flight.departureTimeInMinutes - 1
            )
        }
    }
}

suspend fun fetchFligts(
    passengerName: List<String> = listOf("Madrigal", "Polarcubis")
) = passengerName.map { fetchFlight(it) }