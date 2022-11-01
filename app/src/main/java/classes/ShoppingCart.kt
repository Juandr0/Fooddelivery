package classes

// The list is set to private so that only the singleton object can modify it directly.
object ShoppingCart {
    var userItems = mutableListOf<OrderItem>()
        private set
    fun addItem (orderItem: OrderItem){
        userItems.add(orderItem)
    }

    fun removeItem (orderItem: OrderItem){
        userItems.remove(orderItem)
    }

    fun clearItems() {
        userItems.clear()
    }
}