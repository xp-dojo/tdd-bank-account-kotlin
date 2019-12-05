package org.xpdojo.bank.tdd

import org.xpdojo.bank.tdd.Direction.CREDIT
import org.xpdojo.bank.tdd.Direction.DEBIT
import java.io.PrintStream
import java.time.LocalDateTime.now

/**
 * Represents a bank account.  You can do things to this class like deposit, withdraw and transfer.
 */
class Account(openingBalance: Money = Money(0.0)) {

    val transactions: MutableList<Transaction> = ArrayList()

    companion object {
        fun anAccountWith(openingBalance: Money) = Account(openingBalance)
        fun anEmptyAccount() = Account()
    }

    init {
        transactions.add(Transaction(openingBalance,  "Opening balance", CREDIT, now()))
    }

    fun deposit(amount: Money) {
        transactions.add(Transaction(amount, "Deposit", CREDIT, now()))
    }

    fun withdraw(amount: Money) {
        if (amount isGreaterThan balance()) {
            throw IllegalStateException("You are trying to withdraw [$amount] more than your balance [${balance()}]")
        }
        transactions.add(Transaction(amount, "Withdrawal", DEBIT, now()))
    }

    fun balance(): Money {
        return transactions.map { it.balanceImpact() }.reduce { acc, money -> acc plus money }
    }

    fun transfer(anAmount: Money): MoneyCourier {
        return MoneyCourier(this, anAmount)
    }

    fun printBalanceSlipWith(stream: PrintStream) {
        val accountPrinter = AccountPrinter(stream)
        accountPrinter.printBalanceOf(this, now())
    }

    fun printStatementWith(stream: PrintStream) {
        val accountPrinter = AccountPrinter(stream)
        accountPrinter.printStatementFor(this, now())
    }


    class MoneyCourier(private val sender: Account, private val anAmount: Money) {
        infix fun into(receiver: Account) {
            sender.withdraw(anAmount)
            receiver.deposit(anAmount)
        }

    }
}