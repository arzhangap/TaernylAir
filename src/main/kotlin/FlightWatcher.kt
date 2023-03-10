import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import BoardingState.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

fun main() {
    runBlocking {
        println("Getting the latest flights info...")
        val flights = fetchFligts()
        val flightDescriptions = flights.joinToString {
            "${it.passengerName} (${it.flightNumber})"
        }
        println("Found flights for $flightDescriptions")
        val flightsAtGate = MutableStateFlow(flights.size)

        launch {
        flightsAtGate
            .takeWhile { it > 0 }
            .onCompletion { println("Finished tracking all flights") }
            .collect { flightCount ->
                println("There are $flightCount flights being tracked")
            }
    }
        launch {
            flights.forEach {
                watchFlight(it)
                flightsAtGate.value = flightsAtGate.value - 1
            }
        }
    }
}

suspend fun watchFlight(initialFlight: FlightStatus) {
    val passengerName = initialFlight.passengerName
    val currentFlight: Flow<FlightStatus> = flow {
        var flight = initialFlight
        while (flight.departureTimeInMinutes >= 0) {
            emit(flight)
            delay(500)
            flight = flight.copy(
                departureTimeInMinutes = flight.departureTimeInMinutes - 1
            )
        }
    }
    // consume flight data
    currentFlight
        .onCompletion { println("finished tracking $passengerName's flight") }
        .collect {
        val status = when (it.boardingStatus) {
            FlightCanceled -> "Your Flight was canceled"
            BoardingNotStarted -> "Boarding will start soon"
            WaitingToBoard -> "Other passengers are boarding"
            Boarding -> "You can now board the plane"
            BoardingEnded -> "The boarding doors have closed"
        } + " (Flight departs in ${it.departureTimeInMinutes} minutes)"
        println("$passengerName: $status")
    }
}

suspend fun fetchFligts(
    passengerName: List<String> = listOf("Madrigal", "Polarcubis")
) = passengerName.map { fetchFlight(it) }