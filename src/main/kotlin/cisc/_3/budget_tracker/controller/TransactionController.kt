package cisc._3.budget_tracker.controller

import cisc._3.budget_tracker.model.Goal
import cisc._3.budget_tracker.model.GoalTracker
import cisc._3.budget_tracker.model.Transaction
import cisc._3.budget_tracker.service.GoalsService
import cisc._3.budget_tracker.service.TransactionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/")
class TransactionController(
    private val transactionService: TransactionService,
    private val goalsService: GoalsService
) {

    @PostMapping("accounts/{accountId}/transaction")
    fun addTransaction(
        @PathVariable accountId: UUID,
        @RequestBody transactions: List<Transaction>
    ): ResponseEntity<String> {
        transactionService.addTransactions(accountId, transactions)
        return ResponseEntity.ok().body("Transaction added successfully for $accountId")
    }

    @GetMapping("accounts/{accountId}/transaction/{transactionId}")
    fun findTransactionByAccount(
        @PathVariable accountId: UUID,
        @PathVariable transactionId: UUID
    ): ResponseEntity<Transaction> {
        val transaction = transactionService.findTransactionByTransactionId(accountId, transactionId)
        return ResponseEntity.ok().body(transaction)
    }

    @GetMapping("accounts/{accountId}/transactions")
    fun findAllTransactionByAccount(@PathVariable accountId: UUID): ResponseEntity<List<Transaction>> {
        val transaction = transactionService.findTransactions(accountId)
        return ResponseEntity.ok().body(transaction)
    }

    @PostMapping("accounts/{accountId}/goals")
    fun addGoals(
        @PathVariable accountId: UUID,
        @RequestBody goals: List<Goal>
    ): ResponseEntity<String> {
        goalsService.addGoals(accountId, goals)
        return ResponseEntity.ok().body("Goals were added successfully for $accountId")
    }

    @GetMapping("accounts/{accountId}/goalsTracker")
    fun getGoalsTracker(
        @PathVariable accountId: UUID,
    ): List<GoalTracker> = goalsService.trackGoals(accountId)

}
