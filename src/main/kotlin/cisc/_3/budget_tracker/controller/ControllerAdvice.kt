package cisc._3.budget_tracker.controller

import cisc._3.budget_tracker.exception.AccountNotFoundException
import cisc._3.budget_tracker.exception.InsufficientFundsException
import cisc._3.budget_tracker.model.ErrorMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(AccountNotFoundException::class)
    fun handleAccountNotFoundException(e: AccountNotFoundException): ResponseEntity<ErrorMessage> {

        val errorMessage = ErrorMessage(errorCode = 100, errorMessage = e.message)
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(InsufficientFundsException::class)
    fun handleInsufficientFundsException(e: InsufficientFundsException): ResponseEntity<ErrorMessage> {

        val errorMessage = ErrorMessage(errorCode = 101, errorMessage = e.message)
        return ResponseEntity(errorMessage, HttpStatus.PRECONDITION_FAILED)
    }
}