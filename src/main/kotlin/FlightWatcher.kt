import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
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

suspend fun watchFlight(initialFlight: FlightStatus) {
    val passengerName = initialFlight.passengerName
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
    // consume flight data
    currentFlight.collect {
        println("$passengerName: $it")

    }
    println("finished tracking $passengerName's flight")
}

suspend fun fetchFligts(
    passengerName: List<String> = listOf("Madrigal", "Polarcubis")
) = passengerName.map { fetchFlight(it) }