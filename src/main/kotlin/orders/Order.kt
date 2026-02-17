package orders

class Order(
    val id: Int
) : PriceCalculator {

    private val _products: MutableList<Product> = mutableListOf()

    /** Read-only view of products in the order. */
    val products: List<Product> get() = _products.toList()

    var status: OrderStatus = OrderStatus.Created
        private set

    /**
     * Adds a product to the order.
     * If the product is null, it should be ignored.
     */
    fun addProduct(product: Product?) {
        product?.let {
            _products.add(it)
        }
    }

    /**
     * Removes the first product matching [productId].
     */
    fun removeProductById(productId: Int) {
        _products.indexOfFirst { it.id == productId }
            .takeIf { it >= 0 }
            ?.let { index ->
                _products.removeAt(index)
            }
    }

    /**
     * Returns the total price of all products in the order.
     */
    override fun calculateTotal(): Int {
        return _products.sumOf { it.price }
    }

    /**
     * Marks the order as paid.
     * Throws [IllegalStateException] if the order has no products.
     */
    fun pay() {
        if (_products.isEmpty()) {
            throw IllegalStateException("Cannot pay for an empty order")
        }
        status = OrderStatus.Paid
    }

    /**
     * Cancels the order with the given reason.
     * If [reason] is null, use "Unknown reason".
     */
    fun cancel(reason: String?) {
        val finalReason = reason ?: "Unknown reason"
        status = OrderStatus.Cancelled(finalReason)
    }

    override fun toString(): String {
        return "Order(id=$id, status=$status, products=${_products.size})"
    }
}