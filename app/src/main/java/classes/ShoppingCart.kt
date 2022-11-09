package classes

import android.content.res.Resources
import com.example.fooddeliveryproject.R

// The list is set to private so that only the singleton object can modify it directly.
object ShoppingCart {
    var currentOrderList = mutableListOf<OrderItem>()
        private set
    fun addItemToCart (orderItem: OrderItem){
        currentOrderList.add(orderItem)
    }

    fun removeItemFromCart (position : Int){
        currentOrderList.removeAt(position)
    }

    fun clearItemsFromCart() {
        currentOrderList.clear()
    }

    fun calculateTotalPrice() : Int{
        var counter = 0
        var deliveryfee = 0
        var totalPrice = 0

        for (OrderItem in currentOrderList){
            totalPrice += currentOrderList[counter].price
            deliveryfee = currentOrderList[counter].deliveryFee
            counter++
        }

        totalPrice += deliveryfee
        return totalPrice
    }

    fun getRestaurantName() : String{
        var currentRestaurantName = ""
        if (currentOrderList.size != 0){
            currentRestaurantName= currentOrderList[0].restaurantName
        }
        return currentRestaurantName
    }

    fun getDeliveryFee() : Int {
        var deliveryFee = 0
        if (currentOrderList.size != 0) {
            deliveryFee = currentOrderList[0].deliveryFee
        }
        return deliveryFee
    }

}




