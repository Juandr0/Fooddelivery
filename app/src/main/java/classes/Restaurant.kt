package classes

data class Restaurant(var image: String, var name: String, var rating: Double, var deliveryFee: Int) {
    constructor():this("","",0.0,0)
}