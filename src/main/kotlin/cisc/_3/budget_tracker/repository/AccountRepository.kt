package cisc._3.budget_tracker.repository

import cisc._3.budget_tracker.exception.AccountNotFoundException
import cisc._3.budget_tracker.model.Account
import cisc._3.budget_tracker.model.Transaction
import cisc._3.budget_tracker.model.TransactionType
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
class AccountRepository {

    private final val accounts = mutableMapOf<UUID,Account>()

    init {
        val testAccount = Account(
            accountId = UUID.fromString("28400685-d516-42d9-816f-e4e483089016"),
            accountHolderName = "Test Account",
            transactions = mutableListOf(Transaction(
                transactionId = UUID.fromString("be98e378-ef8f-4967-a270-e2a4ffe68cd4"),
                transactionName = "Global Inc.",
                amount = 200.00,
                transactionType = TransactionType.INCOME,
                date = LocalDateTime.now(),
                category = "Salary"
            )),
            accountBalance = 5000.00,
            email = "test@gmail.com",
        )
        accounts[testAccount.accountId] = testAccount

    }

    fun findByAccountNumber(accountNumber: UUID) = accounts[accountNumber]

    fun save(account: Account) {
        accounts[account.accountId] = account
    }

    fun delete(accountId: UUID) = accounts.remove(accountId)

    fun findTransaction(accountNumber: UUID, transactionId: UUID) : Transaction {
        val account = accounts[accountNumber] ?: throw AccountNotFoundException("Account number $accountNumber not found.")
        val transaction = account.transactions.find { it.transactionId == transactionId }
        return transaction ?: throw AccountNotFoundException("Transaction $transactionId not found.")
    }

    fun findTransactions(accountNumber: UUID): List<Transaction> {
        val account = accounts[accountNumber] ?: throw AccountNotFoundException("Account number $accountNumber not found.")
        return account.transactions
    }

}

