package orders


/**
 * Returns a human-readable description of the order status.
 * Use a `when` expression on [order].status:
 *   - Created  -> "Order {id} is new"
 *   - Paid     -> "Order {id} is paid"
 *   - Cancelled -> "Order {id} is cancelled: {reason}"
 */
fun processOrder(order: Order): String {
    return when (val status = order.status) {
        OrderStatus.Created -> "Order ${order.id} is new"
        OrderStatus.Paid -> "Order ${order.id} is paid"
        is OrderStatus.Cancelled -> "Order ${order.id} is cancelled: ${status.reason}"
    }
}