package cisc._3.budget_tracker.service

import cisc._3.budget_tracker.exception.AccountNotFoundException
import cisc._3.budget_tracker.exception.InsufficientFundsException
import cisc._3.budget_tracker.model.Goal
import cisc._3.budget_tracker.model.GoalTracker
import cisc._3.budget_tracker.repository.AccountRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class GoalsService(
    private val accountRepository: AccountRepository,
    private val notificationService: NotificationService
) {

    fun addGoals(accountNumber: UUID, goals: List<Goal>) {
        val account = accountRepository.findByAccountNumber(accountNumber)

        account?.let {
            val updatedAccount = it.copy(
                goals = it.goals + goals
            )

            val totalGoalAmount = goals.sumOf { goal -> goal.currentAmount }
            if (totalGoalAmount > account.accountBalance) {
                notificationService.sendNotification(account.email,"Insufficient funds")
                throw InsufficientFundsException("You do not have sufficient funds to put towards your goals")
            }

            accountRepository.save(updatedAccount)
        } ?: throw AccountNotFoundException("Account number $accountNumber not found.")
    }

    fun trackGoals(accountNumber: UUID): List<GoalTracker> {
        val account = accountRepository.findByAccountNumber(accountNumber)
            ?: throw AccountNotFoundException("Account number $accountNumber not found.")

        if (account.goals.isEmpty()) {
            notificationService.sendNotification(account.email,"No goals entered")
            return emptyList()
        }

        val goalTracker = account.goals.map {
            GoalTracker(
                goalName = it.goalName,
                goalPrice = it.goalPrice,
                currentAmount = it.currentAmount,
                goalPercentReached = ((it.currentAmount / it.goalPrice) * 100).coerceAtMost(100.0)
            )
        } ?: throw AccountNotFoundException("Account number $accountNumber not found.")
        return goalTracker
    }
}