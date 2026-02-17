package orders

/**
 * Applies a percentage discount to every product in the order.
 *
 * Hint: use [products] to read the current list, [removeProductById] and
 * [addProduct] to replace each product with a discounted copy.
 * Use [Product.copy] to create a new product with a modified price.
 *
 * @param discountPercent discount percentage (e.g. 10 means 10%)
 * @param logger optional callback invoked with a log message for each product
 */
fun Order.applyDiscount(
    discountPercent: Int,
    logger: ((String) -> Unit)? = null
) {
    require(discountPercent in 0..100) { "Discount percent must be between 0 and 100" }

    // Получаем копию списка продуктов для итерации (чтобы избежать ConcurrentModificationException)
    val currentProducts = products.toList()

    currentProducts.forEach { product ->
        // Рассчитываем новую цену со скидкой
        val discountedPrice = (product.price * (100 - discountPercent)) / 100

        // Создаем новый продукт со скидкой через copy
        val discountedProduct = product.copy(price = discountedPrice)

        // Используем scoped functions для замены продукта
        run {
            // Удаляем старый продукт
            removeProductById(product.id)

            // Добавляем новый со скидкой
            addProduct(discountedProduct)
        }.also {
            // Логируем действие через callback, если он предоставлен
            logger?.invoke("Product '${product.name}' discounted from ${product.price} to $discountedPrice")
        }
    }

    // Дополнительное логирование итогов
    logger?.invoke("Discount of $discountPercent% applied to all ${currentProducts.size} products")
}