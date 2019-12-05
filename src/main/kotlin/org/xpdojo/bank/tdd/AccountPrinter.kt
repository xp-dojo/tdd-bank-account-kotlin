package org.xpdojo.bank.tdd

import java.io.PrintStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AccountPrinter(private val stream: PrintStream) {

    private val dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")

    fun printBalanceOf(account: Account, dateTime: LocalDateTime) {
        stream.println("""
            **************************************
            **             Dojo Bank            **
            **           Balance Slip           **
            **************************************
               Date:    ${dateTime.format(dateFormat)} 
               Balance: ${account.balance().amount}
            **************************************
            **   Thank you for using Dojo Bank  **
            ************************************** """)
    }

    fun printStatementFor(account: Account, dateTime: LocalDateTime) {
        stream.println("""
            ***************************************************
            **                    Dojo Bank                  **
            **               Account Statement               **
            ***************************************************
               Date: ${dateTime.format(dateFormat)}
               Balance: ${account.balance().amount}
            ***************************************************
               
               Transaction History:""")

        account.transactions.forEach { printStatementLineFor(it) }

        stream.println("""
            ***************************************************
            **         Thank you for using Dojo Bank         **
            *************************************************** """)
    }

    private fun printStatementLineFor(transaction: Transaction) {
        stream.println("                  ${transaction.dateTime.format(dateFormat)} ${transaction.description.padEnd(15)} ${transaction.balanceImpact().amount.toString().padStart(6)}")
    }


}