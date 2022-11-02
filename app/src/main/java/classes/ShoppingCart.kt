package classes

// The list is set to private so that only the singleton object can modify it directly.
object ShoppingCart {
    var currentOrderList = mutableListOf<OrderItem>()
        private set
    fun addItemToCart (orderItem: OrderItem){
        currentOrderList.add(orderItem)
    }

    fun removeItemFromCart (orderItem: OrderItem){
        currentOrderList.remove(orderItem)
    }

    fun clearItemsFromCart() {
        currentOrderList.clear()
    }

    fun calculateTotalPrice() : Int{
        var counter = 0

        var totalPrice = 0
        for (OrderItem in ShoppingCart.currentOrderList){
            totalPrice += ShoppingCart.currentOrderList[counter].price
            counter++
        }

        return totalPrice
    }


}




