package cisc._3.budget_tracker.service

import cisc._3.budget_tracker.exception.AccountNotFoundException
import cisc._3.budget_tracker.exception.InsufficientFundsException
import cisc._3.budget_tracker.model.Account
import cisc._3.budget_tracker.model.Goal
import cisc._3.budget_tracker.repository.AccountRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class GoalsServiceTest {

    private val accountRepository: AccountRepository = mockk()
    private val notificationService: NotificationService = mockk()
    private val goalsService = GoalsService(accountRepository, notificationService)

    @Test
    fun `When a user wants to add goals, add it to the database`() {
        val goalsToAdd = listOf(
            Goal(goalName = "TV", goalPrice = 200.00, currentAmount = 20.00)
        )

        every { accountRepository.findByAccountNumber(any()) } returns account
        every { accountRepository.save(any()) } returns Unit

        goalsService.addGoals(validUUID, goalsToAdd)

        verify { accountRepository.save(any()) }
    }

    @Test
    fun `When a user wants to add goals using invalid accountId,throw AccountNotFoundException`() {
        val goalsToAdd = listOf(
            Goal(goalName = "TV", goalPrice = 200.00, currentAmount = 20.00)
        )

        every { accountRepository.findByAccountNumber(any()) } returns null
        every { accountRepository.save(any()) } returns Unit

        assertThrows<AccountNotFoundException> { goalsService.addGoals(invalidUUID, goalsToAdd) }
    }

    @Test
    fun `When a user wants to add goals exceeding his balance,throw InsufficientFundsException`() {
        val goalsToAdd = listOf(
            Goal(goalName = "TV", goalPrice = 500.00, currentAmount = 420.00)
        )

        every { accountRepository.findByAccountNumber(any()) } returns account
        every { notificationService.sendNotification(any(),any()) } returns Unit
        every { accountRepository.save(any()) } returns Unit

        assertThrows<InsufficientFundsException> { goalsService.addGoals(validUUID, goalsToAdd) }
    }

    @Test
    fun `Given insufficient funds, verify notification is sent to the correct email`() {
        val goalsToAdd = listOf(
            Goal(goalName = "TV", goalPrice = 500.00, currentAmount = 420.00)
        )

        every { accountRepository.findByAccountNumber(any()) } returns account
        every { notificationService.sendNotification(any(),any()) } returns Unit
        every { accountRepository.save(any()) } returns Unit

        assertThrows<InsufficientFundsException> { goalsService.addGoals(validUUID, goalsToAdd) }

        verify { notificationService.sendNotification("Test@test.com","Insufficient funds") }
    }

    @Test
    fun `When a user wants to track his goal , return the goal completion details`() {
        val accountWithGoals = account.copy(goals = listOf(
            Goal(goalName = "TV", goalPrice = 200.00, currentAmount = 40.00),
            Goal(goalName = "XBox", goalPrice = 300.00, currentAmount = 120.00),
        ))

        every { accountRepository.findByAccountNumber(any()) } returns accountWithGoals

        val expected = goalsService.trackGoals(validUUID)

        assertThat(expected).hasSize(2)
        assertThat(expected[0].goalName).isEqualTo("TV")
        assertThat(expected[0].goalPercentReached).isEqualTo(20.00)
        assertThat(expected[0].progressBar).isEqualTo("██--------")

        assertThat(expected[1].goalName).isEqualTo("XBox")
        assertThat(expected[1].goalPercentReached).isEqualTo(40.00)
        assertThat(expected[1].progressBar).isEqualTo("████------")
    }

    @Test
    fun `When a user wants to track his goal ,but no goals has been entered, send notification`() {
        every { accountRepository.findByAccountNumber(any()) } returns account
        every { notificationService.sendNotification(any(),any()) } returns Unit

        val expected = goalsService.trackGoals(validUUID)

        assertThat(expected).isEmpty()
        verify { notificationService.sendNotification(any(),any()) }
    }

    @Test
    fun `When a user has saved more than he needs , cap the goal percent to 100`() {
        val accountWithGoals = account.copy(goals = listOf(
            Goal(goalName = "TV", goalPrice = 20.00, currentAmount = 40.00),
            Goal(goalName = "XBox", goalPrice = 300.00, currentAmount = 120.00),
        ))

        every { accountRepository.findByAccountNumber(any()) } returns accountWithGoals

        val expected = goalsService.trackGoals(validUUID)

        assertThat(expected[0].goalName).isEqualTo("TV")
        assertThat(expected[0].goalPercentReached).isEqualTo(100.00)
        assertThat(expected[0].progressBar).isEqualTo("██████████")
    }

    @Test
    fun `When a user wants to track using invalid accountId,throw AccountNotFoundException`() {
        every { accountRepository.findByAccountNumber(any()) } returns null
        every { accountRepository.save(any()) } returns Unit

        assertThrows<AccountNotFoundException> { goalsService.trackGoals(validUUID)
        }
    }

    companion object {
        val validUUID: UUID = UUID.fromString("75612b14-1bf9-44cd-9a6b-b47bc6303fdd")
        val invalidUUID: UUID = UUID.fromString("75612b14-1bf9-44cd-9a6b-b47bc6303fde")


        val account = Account(
            accountId = AccountServiceTest.validUUID,
            accountHolderName = "Test Account Holder",
            transactions = listOf(),
            accountBalance = 300.00,
            budget = 50.00,
            goals = listOf(),
            email = "Test@test.com"
        )

    }
}