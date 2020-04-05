package com.example.hw4l4

import org.junit.Test
import kotlin.math.round

class ExampleUnitTest {

    @Test
    fun example() {

        val productsCart = Cart(mutableListOf(
            Product(price = 123.5, salePercent = 30)
        ))

        listOf(
            Product(price = 119.0, salePercent = 5),
            Product(price = 112.1, salePercent = 5),
            Product(price = 119.9, salePercent = 5),
            Product(price = 119.9)
        ).forEach { productsCart.add(it) }

        val pricePrinter: PricePrinter = ConsolePricePrinter()

        val totalCost = productsCart.calcTotalCost()
        pricePrinter.print(totalCost)

    }

}

class Product(
    /**
     * Must be positive
     */
    private val price: Double,
    private val salePercent: Int = 0
) {

    /**
     * @return price with applied discount determined by [salePercent]
     *
     * If [salePercent] is more than 100 than product will have negative price
     * If [salePercent] less than 0 product price will be increased
     */
    fun calcDiscountPrice(): Double = price * (1 - salePercent / 100.0)

}

class Cart(private val products: MutableList<Product>) {

    /**
     * Add [item] at the end of the list
     */
    fun add(item: Product) = products.add(item)

    fun calcTotalCost(): Double = products.sumByDouble { it.calcDiscountPrice() }

}

interface PricePrinter {

    /**
     * Outputs price in <PRICE>P format.
     * If price have not fractional part than it will be printed as integer
     * If price have fractional part than it will be rounded for 2 symbols after "."
     */
    fun print(price: Double)

}

class ConsolePricePrinter : PricePrinter {

    override fun print(price: Double)  = println("" +
            (if (price - price.toInt() == 0.0)
                "${price.toInt()}"
            else
                "${round(price * 100) / 100}") +
            "P")

}