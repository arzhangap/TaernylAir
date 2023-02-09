data class FlightStatus (
    val flightNumber: String,
    val passengerName: String,
    val passengerLoyaltyTier: String,
    val originAirport: String,
    val destinationAirport: String,
    val status: String,
    val departureTimeInMinutes: Int
) {

    companion object {
        fun parse(
            flightResponse: String,
            loyaltyResponse: String,
            passengerName: String
        ) : FlightStatus {
            val (flightNumber, originAirport, destinationAirport, status, departureTimeInMinutes) = flightResponse.split(",")
            val (loyaltyTierName, milesFlown, milesToNextTier) = loyaltyResponse.split(",")

            return FlightStatus(
                flightNumber = flightNumber,
                passengerName = passengerName,
                passengerLoyaltyTier = loyaltyTierName,
                originAirport = originAirport,
                destinationAirport = destinationAirport,
                status = status,
                departureTimeInMinutes = departureTimeInMinutes.toInt()
            )
        }

        fun isCanceled(
            flightResponse: String
        ) : Boolean {
            val (_, _, _, status, _) = flightResponse.split(",")
            return status != "Canceled"
        }
    }
}

enum class LotyaltyTier(
    val tierName: String,
    val boardingWindowStart: Int
) {
    Bronze("Bronze",25),
    Silver("Bronze",25),
    Gold("Bronze",30),
    Platinum("Bronze",35),
    Titanium("Bronze",40),
    Diamond("Bronze",45),
    DiamondPlus("Bronze",50),
    DiamondPlusPlus("Bronze",60)
}

enum class BoardingState{
    FlightCanceled,
    BoardingNotStarted,
    WaitingToBoard,
    Boarding,
    BoardingEnded
}
