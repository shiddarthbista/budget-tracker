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
        val message = "notification message"

        every { log.info("Sending notification to $email with message $message") } just runs
        notificationService.sendNotification(email,"notification message")

        verify { log.info("Sending notification to test@example.com with message notification message") }
    }
}