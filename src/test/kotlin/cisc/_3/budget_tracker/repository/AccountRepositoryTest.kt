package cisc._3.budget_tracker.repository

import cisc._3.budget_tracker.exception.AccountNotFoundException
import cisc._3.budget_tracker.model.Account
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import org.junit.jupiter.api.assertThrows


class AccountRepositoryTest {
    private lateinit var accountRepository: AccountRepository
    private lateinit var testAccountId: UUID

    @BeforeEach
    fun setup() {
        accountRepository = AccountRepository()
        testAccountId = UUID.fromString("28400685-d516-42d9-816f-e4e483089016")
    }

    @Test
    fun `findByAccountNumber should return account when found`() {
        val account = accountRepository.findByAccountNumber(testAccountId)
        assertNotNull(account)
        assertEquals("Test Account", account?.accountHolderName)
    }

    @Test
    fun `findByAccountNumber should return null when account not found`() {
        val randomId = UUID.randomUUID()
        val account = accountRepository.findByAccountNumber(randomId)
        assertNull(account)
    }

    @Test
    fun `findByAccountNumber should throw accountNotFoundException when account not found`() {
        val randomId = UUID.randomUUID()
        assertThrows<AccountNotFoundException> {
            accountRepository.findTransactions(randomId)
        }

    }

    @Test
    fun `save should add a new account`() {
        val newAccountId = UUID.randomUUID()
        val newAccount = Account(
            accountId = newAccountId,
            accountHolderName = "New Account",
            transactions = mutableListOf(),
            accountBalance = 1000.00,
            email = "new_account@gmail.com"
        )

        accountRepository.save(newAccount)

        val retrievedAccount = accountRepository.findByAccountNumber(newAccountId)
        assertNotNull(retrievedAccount)
        assertEquals("New Account", retrievedAccount?.accountHolderName)
    }

    @Test
    fun `delete should remove an existing account`() {
        accountRepository.delete(testAccountId)
        val account = accountRepository.findByAccountNumber(testAccountId)
        assertNull(account)
    }

    @Test
    fun `findTransaction should return transaction when found`() {
        val testTransactionId = UUID.fromString("be98e378-ef8f-4967-a270-e2a4ffe68cd4")
        val transaction = accountRepository.findTransaction(testAccountId, testTransactionId)
        assertNotNull(transaction)
    }

    @Test
    fun `findTransaction should throw exception when transaction not found`() {
        val nonExistentTransactionId = UUID.randomUUID()
        val exception = assertThrows<AccountNotFoundException> {
            accountRepository.findTransaction(testAccountId, nonExistentTransactionId)
        }
        assertEquals("Transaction $nonExistentTransactionId not found.", exception.message)
    }

    @Test
    fun `findTransaction should throw exception when account not found`() {
        val randomAccountId = UUID.randomUUID()
        val testTransactionId = UUID.randomUUID()

        val exception = assertThrows<AccountNotFoundException> {
            accountRepository.findTransaction(randomAccountId, testTransactionId)
        }
          assertEquals("Account number $randomAccountId not found.", exception.message)
    }

    @Test
    fun `findTransactions should return all transactions for account`() {
        val transactions = accountRepository.findTransactions(testAccountId)
        assertEquals(1, transactions.size)
        assertEquals("Global Inc.", transactions[0].transactionName)
    }

    @Test
    fun `findTransactions should return empty list when no transactions exist`() {
        val newAccountId = UUID.randomUUID()
        val newAccount = Account(
            accountId = newAccountId,
            accountHolderName = "Empty Account",
            transactions = mutableListOf(),
            accountBalance = 500.00,
            email = "empty_account@gmail.com"
        )
        accountRepository.save(newAccount)

        val transactions = accountRepository.findTransactions(newAccountId)
        assertTrue(transactions.isEmpty())
    }
}