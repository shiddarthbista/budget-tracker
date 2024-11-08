package cisc._3.budget_tracker.model

import java.util.*

data class Account(
    val accountId: UUID = UUID.randomUUID(),
    val accountHolderName: String,
    val transactions: List<Transaction>,
    val accountBalance: Double,
    val budget: Double = 5000.00,
    val goals: List<Goal> = listOf(),
    val email: String
)

data class Goal(
    val goalName: String,
    val goalPrice: Double,
    val currentAmount: Double
)

data class GoalTracker(
    val goalName: String,
    val goalPrice: Double,
    val currentAmount: Double,
    val goalPercentReached: Double
) {
    val progressBar = "█".repeat((goalPercentReached / 10).toInt()) + "-".repeat(10 - (goalPercentReached / 10).toInt())
}