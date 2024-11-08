package cisc._3.budget_tracker.service

import cisc._3.budget_tracker.exception.AccountNotFoundException
import cisc._3.budget_tracker.model.Transaction
import cisc._3.budget_tracker.model.TransactionType
import cisc._3.budget_tracker.repository.AccountRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class TransactionService(
    private val accountRepository: AccountRepository,
    private val notificationService: NotificationService
) {

    fun addTransactions(accountNumber: UUID, transactions: List<Transaction>) {
        val account = accountRepository.findByAccountNumber(accountNumber)

        account?.let { it ->
            val incomeAmount = transactions.filter { it.transactionType == TransactionType.INCOME }.sumOf { it.amount }
            val expenseAmount = transactions.filter { it.transactionType == TransactionType.EXPENSES }.sumOf { it.amount }

            val updatedAccount = it.copy(
                transactions = it.transactions + transactions,
                accountBalance = it.accountBalance + incomeAmount - expenseAmount,
                budget = it.budget - expenseAmount
            )

            if (updatedAccount.budget < 0) {
                sendNotificationForNegativeBudget(updatedAccount.email, "Negative budget of ${updatedAccount.budget}.")
            }

            accountRepository.save(updatedAccount)
        } ?: throw AccountNotFoundException("Account number $accountNumber not found.")
    }

    fun findTransactionByTransactionId(accountNumber: UUID, transactionId: UUID): Transaction =
        accountRepository.findTransaction(accountNumber, transactionId)

    fun findTransactions(accountNumber: UUID) = accountRepository.findTransactions(accountNumber)

    fun sendNotificationForNegativeBudget(emailAddress : String, message: String) {
        notificationService.sendNotification(emailAddress,"Negative Budget: $message")
    }

}