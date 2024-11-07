package cisc._3.budget_tracker.service

import cisc._3.budget_tracker.exception.AccountNotFoundException
import cisc._3.budget_tracker.model.Account
import cisc._3.budget_tracker.repository.AccountRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccountService(private val accountRepository: AccountRepository) {

    fun findAccountByAccountNumber(accountNumber: UUID): Account = accountRepository.findByAccountNumber(accountNumber)
        ?: throw AccountNotFoundException("Account number $accountNumber not found.")

    fun addNewAccount(account: Account): String {
        accountRepository.save(account)
        return account.accountId.toString()
    }

    fun deleteAccountByAccountNumber(accountId: UUID) = accountRepository.delete(accountId)

    fun editAccount(account: Account) = accountRepository.save(account)

}