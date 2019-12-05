package org.xpdojo.bank.tdd

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks
import org.xpdojo.bank.tdd.Account.Companion.anAccountWith
import org.xpdojo.bank.tdd.Account.Companion.anEmptyAccount
import org.xpdojo.bank.tdd.Money.Companion.anAmountOf
import java.io.PrintStream

/**
 * Requirements:
 *  I can Deposit money to accounts
 *  I can Withdraw money from accounts
 *  I can Transfer amounts between accounts (if I have the funds)
 *  I can print out an Account balance slip (date, time, balance)
 *  I can print a statement of account activity (statement)
 *  I can apply Statement filters (include just deposits, withdrawal, date)
 */
@DisplayName("With an account we can ...")
class AccountTest {

    @Mock private lateinit var mockStream: PrintStream

    @BeforeEach fun setup() {
        initMocks(this)
    }

    @Test fun `deposit an amount to increase the balance`() {
        val account = anAccountWith(anAmountOf(10.0))
        account.deposit(anAmountOf(20.0))
        assertThat(account.balance()).isEqualTo(anAmountOf(30.0))
    }

    @Test fun `not withdraw more than the balance`() {
        val account = anAccountWith(anAmountOf(10.0))
        assertThatExceptionOfType(IllegalStateException::class.java).isThrownBy {
            account.withdraw(anAmountOf(25.0))
        }
    }

    @Test fun `withdraw an amount to decrease the balance`() {
        val account = anAccountWith(anAmountOf(100.0))
        account.withdraw(anAmountOf(25.0))
        assertThat(account.balance()).isEqualTo(anAmountOf(75.0))
    }

    @Test fun `transfer funds between accounts`() {
        val sender = anAccountWith(anAmountOf(100.0))
        val receiver = anAccountWith(anAmountOf(20.0))

        sender.transfer(anAmountOf(25.0)) into receiver

        assertThat(sender.balance()).isEqualTo(anAmountOf(75.0))
        assertThat(receiver.balance()).isEqualTo(anAmountOf(45.0))
    }

    @Test fun `print out our balance`() {
        val account = anAccountWith(anAmountOf(100.0))
        account.printBalanceSlipWith(mockStream)
        verify(mockStream, times(1)).println(anyString())
    }

    @Test fun `print out a bank statement`() {
        val account = createAccountWithManyTransactions()
        account.printStatementWith(mockStream)
        verify(mockStream, Mockito.atLeast(3)).println(anyString())
        account.printStatementWith(PrintStream(System.out))
    }

    private fun createAccountWithManyTransactions(): Account{
        val account = anEmptyAccount()
        account.deposit(anAmountOf(10.0))
        account.deposit(anAmountOf(25.0))
        account.deposit(anAmountOf(35.0))
        account.withdraw(anAmountOf(29.0))
        account.deposit(anAmountOf(34.0))
        assertThat(account.balance()).isEqualTo(anAmountOf(75.0))
        assertThat(account.transactions).hasSize(6)
        return account
    }
}