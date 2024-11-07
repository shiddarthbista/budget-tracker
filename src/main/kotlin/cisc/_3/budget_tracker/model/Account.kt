package cisc._3.budget_tracker.model

import java.util.*

data class Account(
    val accountId: UUID = UUID.randomUUID(),
    val accountHolderName: String,
    val transactions: List<Transaction>,
    val accountBalance: Double,
    val budget: Double = 5000.00,
)