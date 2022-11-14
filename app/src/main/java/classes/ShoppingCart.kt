package classes

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.util.Log
import com.example.fooddeliveryproject.R
import com.example.fooddeliveryproject.UserInterfaceActivity

// The list is set to private so that only the singleton object can modify it directly.
object ShoppingCart {
    var currentOrderList = mutableListOf<OrderItem>()
        private set

    var isTotalZero = true


    fun addItemToCart(orderItem: OrderItem, context : Context) {
        if (currentOrderList.isNotEmpty()) {

            var currentRestaurant = currentOrderList[0].restaurantName
            var itemRestaurant = orderItem.restaurantName


            if (itemRestaurant == currentRestaurant) {
                currentOrderList.add(orderItem)
            } else if (itemRestaurant != currentRestaurant) {
                orderFromDifferentRestaurant(orderItem, context)
            }

        } else {
            currentOrderList.add(orderItem)
        }

    }


    fun orderFromDifferentRestaurant(orderItem: OrderItem, context : Context) {
        var alertDialogBuilder = AlertDialog.Builder(context)
            .setTitle("Warning")
            .setMessage("Test")
           alertDialogBuilder.show()


        currentOrderList.clear()
        currentOrderList.add(orderItem)

    }

        fun removeItemFromCart(position: Int) {
            currentOrderList.removeAt(position)
        }

        fun clearItemsFromCart() {
            currentOrderList.clear()
        }

        fun calculateTotalPrice(): Int {
            var counter = 0
            var deliveryfee = 0
            var totalPrice = 0

            for (OrderItem in currentOrderList) {
                totalPrice += currentOrderList[counter].price
                deliveryfee = currentOrderList[counter].deliveryFee
                counter++
            }
            isTotalZero = totalPrice == 0
            totalPrice += deliveryfee
            return totalPrice
        }

        fun getRestaurantName(): String {
            var currentRestaurantName = ""
            if (currentOrderList.size != 0) {
                currentRestaurantName = currentOrderList[0].restaurantName
            }
            return currentRestaurantName
        }

        fun getDeliveryFee(): Int {
            var deliveryFee = 0
            if (currentOrderList.size != 0) {
                deliveryFee = currentOrderList[0].deliveryFee
            }
            return deliveryFee
        }

}


