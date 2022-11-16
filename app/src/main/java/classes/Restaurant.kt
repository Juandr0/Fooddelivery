package classes

data class Restaurant(var image: String, var name: String, var rating: Double, var deliveryFee: Int, var totalVotes : Int = 0, var totalRating : Double = 0.0) {
    constructor():this("","",0.0,0)
}