package cisc._3.budget_tracker.service

import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class NotificationService(val log: Logger = Logger.getLogger(NotificationService::class.java.name)) {

    fun sendNotification(email:String,message :String){
        log.info("Sending notification to $email with message $message")
    }
}