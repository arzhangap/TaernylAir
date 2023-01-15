import java.net.URL

const val BASE_URL = "http://kotlin-book.bignerdranch.com/2e"
const val FLIGHT_ENDPOINT = "$BASE_URL/flight"

fun main() {
    println("Started")
    val flight = fetchFLight()
    println(flight)
    println("Finished")
}

fun fetchFLight() : String = URL(FLIGHT_ENDPOINT).readText()