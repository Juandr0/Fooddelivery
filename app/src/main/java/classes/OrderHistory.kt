package classes

import java.util.Date

class OrderHistory (var restaurantName : String? = null,
                    var dateOfPurchase : Date? = null,
                    var orderItem : List<String>? = null,
                    var price : Int? = null
)