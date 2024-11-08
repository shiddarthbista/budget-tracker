package cisc._3.budget_tracker.service


import cisc._3.budget_tracker.exception.AccountNotFoundException
import cisc._3.budget_tracker.model.Account
import cisc._3.budget_tracker.model.Transaction
import cisc._3.budget_tracker.model.TransactionType
import cisc._3.budget_tracker.repository.AccountRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import java.util.*

class TransactionServiceTest {

    private val accountRepository: AccountRepository = mockk()
    private val notificationService: NotificationService = mockk()
    private val transactionService = TransactionService(accountRepository, notificationService)

    @Test
    fun `When user has a list of transactions to add, save the transactions`() {
        every { accountRepository.save(any()) } returns Unit
        every { accountRepository.findByAccountNumber(validUUID) } returns account

        transactionService.addTransactions(validUUID, transactionList)

        verify { accountRepository.save(any()) }
    }

    @Test
    fun `When user has a list of transactions to add, but it exceeds budget, send notifications for negative budgets`() {
        every { accountRepository.save(any()) } returns Unit
        every { accountRepository.findByAccountNumber(validUUID) } returns account
        every { notificationService.sendNotification(any(),any()) } returns Unit

        val transactionWithHighExpense = Transaction(
            transactionId = transaction1UUID,
            transactionName = "Tesla.",
            amount = 200000.00,
            transactionType = TransactionType.EXPENSES,
            date = LocalDateTime.now(),
            category = "Transportation"
        )

        val transactionList3 = listOf(transactionWithHighExpense, transaction1, transaction2)
        transactionService.addTransactions(validUUID, transactionList3)

        verify { accountRepository.save(any()) }
        verify { notificationService.sendNotification(any(),any()) }
    }

    @Test
    fun `Given invalid account number when trying to add transactions, return AccountNotFoundException`() {
        every { accountRepository.save(any()) } returns Unit
        every { accountRepository.findByAccountNumber(invalidUUID) } returns null

        assertThrows<AccountNotFoundException> {
            transactionService.addTransactions(invalidUUID, transactionList)
        }
    }

    @Test
    fun `Given client gives valid account number and transaction Id , return the details for requested transactionId`() {
        every { accountRepository.findTransaction(validUUID, transaction1UUID) } returns transaction1

        val expected = transactionService.findTransactionByTransactionId(validUUID, transaction1UUID)

        assertThat(expected.transactionName).isEqualTo("Global Inc.")
        assertThat(expected.transactionId).isEqualTo(UUID.fromString("14cfc67a-b833-4783-a159-d9da3624fe7e"))
        assertThat(expected.transactionType).isEqualTo(TransactionType.INCOME)
        assertThat(expected.amount).isEqualTo(200.0)
        assertThat(expected.category).isEqualTo("Salary")
    }

    @Test
    fun `When user provides valid account number return all the transactions`() {
        every { accountRepository.findTransactions(validUUID) } returns transactionList

        val expected = transactionService.findTransactions(validUUID)

        assertThat(expected).containsExactlyElementsOf(transactionList)
        assertThat(expected).size().isEqualTo(2)

        assertThat(expected[0].transactionName).isEqualTo("Global Inc.")
        assertThat(expected[0].transactionId).isEqualTo(UUID.fromString("14cfc67a-b833-4783-a159-d9da3624fe7e"))
        assertThat(expected[0].transactionType).isEqualTo(TransactionType.INCOME)
        assertThat(expected[0].amount).isEqualTo(200.0)
        assertThat(expected[0].category).isEqualTo("Salary")
    }

    @Test
    fun `When the expenses exceeds budget, send notification to the user`() {
        every { notificationService.sendNotification(any(),any()) } returns Unit

        transactionService.sendNotificationForNegativeBudget("email@address.com","Negative Budget")

        verify { notificationService.sendNotification("email@address.com", "Negative Budget: Negative Budget") }
    }

    companion object {
        val validUUID: UUID = UUID.fromString("75612b14-1bf9-44cd-9a6b-b47bc6303fdd")
        val invalidUUID: UUID = UUID.fromString("75612b14-1bf9-44cd-9a6b-b47bc6303fde")
        private val transaction1UUID: UUID = UUID.fromString("14cfc67a-b833-4783-a159-d9da3624fe7e")
        private val transaction2UUID: UUID = UUID.fromString("be98e378-ef8f-4967-a270-e2a4ffe68cd4")

        private val transaction1 = Transaction(
            transactionId = transaction1UUID,
            transactionName = "Global Inc.",
            amount = 200.00,
            transactionType = TransactionType.INCOME,
            date = LocalDateTime.now(),
            category = "Salary"
        )

        private val transaction2 = Transaction(
            transactionId = transaction2UUID,
            transactionName = "Netflix",
            amount = 100.00,
            transactionType = TransactionType.EXPENSES,
            category = "Entertainment"
        )
        val transactionList = mutableListOf(
            transaction1,
            transaction2,
        )

        val account = Account(
            accountId = validUUID,
            accountHolderName = "Test Account Holder",
            transactions = transactionList,
            accountBalance = 300.00,
            budget = 5000.00,
            goals = listOf(),
            email = "Test@test.com"
        )
    }
}