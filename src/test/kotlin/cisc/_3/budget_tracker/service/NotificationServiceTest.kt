package cisc._3.budget_tracker.service

import io.mockk.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import java.util.logging.Logger

@ExtendWith(MockitoExtension::class)
class NotificationServiceTest {

    private val log: Logger = mockk()
    private val notificationService = NotificationService(log)

    @Test
    fun `should log message when sending notification`() {
        val email = "test@example.com"

        every { log.info("Sending notification to $email") } just runs
        notificationService.sendNotification(email)

        verify { log.info("Sending notification to test@example.com") }
    }
}