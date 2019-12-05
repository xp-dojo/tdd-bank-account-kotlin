package org.xpdojo.bank.tdd

/**
 * Class to represent a monetary amount.  Should treat this an immutable class.
 * Hint: should this be a data class.
 */
data class Money(val amount: Double) {

    companion object {
        fun anAmountOf(amount: Double) = Money(amount)
    }

    infix fun plus(anAmount: Money): Money {
        return Money(amount + anAmount.amount)
    }

    infix fun less(anAmount: Money): Money {
        return Money(amount - anAmount.amount)
    }

    infix fun isGreaterThan(anotherAmount: Money): Boolean {
        return amount > anotherAmount.amount
    }

    fun negative(): Money {
        return Money(amount * -1)
    }

}