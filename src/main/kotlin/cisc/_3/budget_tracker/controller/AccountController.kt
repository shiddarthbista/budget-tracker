package cisc._3.budget_tracker.controller

import cisc._3.budget_tracker.model.Account
import cisc._3.budget_tracker.service.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/accounts")
class AccountController(
    private val accountService: AccountService
) {

    @GetMapping("/{accountId}")
    fun findAccount(@PathVariable accountId: UUID): Account = accountService.findAccountByAccountNumber(accountId)

    @DeleteMapping("/{accountId}")
    fun deleteAccount(@PathVariable accountId: UUID): ResponseEntity<String> {
        accountService.deleteAccountByAccountNumber(accountId)
        return ResponseEntity.ok().body("Account $accountId has been deleted successfully.")
    }

    @PostMapping("/createNewAccount")
    fun addNewAccount(@RequestBody account: Account): ResponseEntity<String> {
        val accountNumber = accountService.addNewAccount(account)
        return ResponseEntity.ok().body("New Account with account number $accountNumber created successfully.")
    }

    @PutMapping("/{accountId}")
    fun editAccount(@RequestBody account: Account, @PathVariable accountId: UUID): ResponseEntity<String> {
        val accountNumber = accountService.editAccount(account)
        return ResponseEntity.ok().body("Successfully updated Account with account number $accountId successfully.")
    }
}