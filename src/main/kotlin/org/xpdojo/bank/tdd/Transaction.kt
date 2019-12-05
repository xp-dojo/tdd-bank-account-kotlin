package org.xpdojo.bank.tdd

import org.xpdojo.bank.tdd.Direction.CREDIT
import org.xpdojo.bank.tdd.Direction.DEBIT
import java.time.LocalDateTime

data class Transaction(val amount: Money, val description: String, val direction: Direction, val dateTime: LocalDateTime) {

    fun balanceImpact(): Money {
        return when (direction) {
            DEBIT -> amount.negative()
            CREDIT -> amount
        }
    }
}

enum class Direction { DEBIT, CREDIT }
