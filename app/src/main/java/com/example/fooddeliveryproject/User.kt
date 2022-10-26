package com.example.fooddeliveryproject

data class User (var name : String? = null,
                 var email : String? = null,
                 var address : String? = null,
                 var phoneNumber : String? = null,
                 var type : String? = "user",
                 var uID : String? = null,
                 var lastOrderRestaurant : String? = "Restaurant",
                 var lastOrder : String? = "No recent orders"
                 )
