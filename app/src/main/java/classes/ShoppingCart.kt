package classes

// The list is set to private so that only the singleton object can modify it directly.
object ShoppingCart {
    var userItems = mutableListOf<OrderItem>()
        private set
    fun addItemToCart (orderItem: OrderItem){
        userItems.add(orderItem)
    }

    fun removeItemFromCart (orderItem: OrderItem){
        userItems.remove(orderItem)
    }

    fun clearItemsFromCart() {
        userItems.clear()
    }

}


