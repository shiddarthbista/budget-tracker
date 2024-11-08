package cisc._3.budget_tracker.model

import java.time.LocalDateTime
import java.util.UUID

data class Transaction(
    val transactionId: UUID,
    val transactionName: String,
    val amount: Double,
    val transactionType: TransactionType,
    val date: LocalDateTime = LocalDateTime.now(),
    val category: String
    )

enum class TransactionType {
    INCOME, EXPENSES
}
