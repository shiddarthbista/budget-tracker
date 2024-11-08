package cisc._3.budget_tracker.service

import cisc._3.budget_tracker.exception.AccountNotFoundException
import cisc._3.budget_tracker.model.Account
import cisc._3.budget_tracker.repository.AccountRepository
import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class AccountServiceTest {

    private val accountRepository: AccountRepository = mockk()
    private val accountService = AccountService(accountRepository)

    @Test
    fun `Given valid account number, return account details`() {
        every { accountRepository.findByAccountNumber(any()) } returns account
        val expectedAccount = accountService.findAccountByAccountNumber(accountNumber = validUUID)

        assertThat(expectedAccount).isEqualTo(account)
    }

    @Test
    fun `Given account number not in system, throw AccountNotFoundException`() {
        every { accountRepository.findByAccountNumber(any()) } returns null

        assertThrows<AccountNotFoundException> {
            accountService.findAccountByAccountNumber(accountNumber = validUUID)
        }
    }

    @Test
    fun `Given a request to create new account, create account and return a new accountId`() {

        every { accountRepository.save(any()) } returns Unit

        val newAccountNumber = accountService.addNewAccount(account)
        assertThat(UUID.fromString(newAccountNumber)).isEqualTo(account.accountId)
    }

    @Test
    fun `Given a request to delete account, Delete account details from the database`() {
        every { accountRepository.delete(any()) } returns account

        val expectedAccount = accountService.deleteAccountByAccountNumber(validUUID)
        assertThat(expectedAccount).isEqualTo(account)
    }

    @Test
    fun `Given a request to delete account that doesnt exist,do not delete anything`() {
        every { accountRepository.delete(any()) } returns null

        val expectedAccount = accountService.deleteAccountByAccountNumber(validUUID)
        assertThat(expectedAccount).isNull()
    }

    @Test
    fun `Given a request to edit account, edit account details from the database`() {
        every { accountRepository.save(any()) } just runs

        accountService.editAccount(account)
        verify { accountRepository.save(account) }
    }

    @Test
    fun `Given a request to edit account that doesnt exist,do not edit anything`() {
        every { accountRepository.save(any()) } just runs

        accountService.editAccount(account)
        verify { accountRepository.save(account) }
    }


    companion object {
        val validUUID: UUID = UUID.fromString("75612b14-1bf9-44cd-9a6b-b47bc6303fdd")

        val account = Account(
            accountId = validUUID,
            accountHolderName = "Test Account Holder",
            transactions = listOf(),
            accountBalance = 72.908,
            budget = 51.274,
            goals = listOf(),
            email = "Test@test.com"
        )

    }
}