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

}


