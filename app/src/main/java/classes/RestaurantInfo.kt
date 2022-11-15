package classes

data class RestaurantInfo(var address: String? = "",
                          var contact: String? = "",
                          var deliveryFee: Int? = 0,
                          var image: String? = "",
                          var name: String? = "",
                          var openingTime: String? = "",
                          var rating: Double = 0.0,
                          )
{
}