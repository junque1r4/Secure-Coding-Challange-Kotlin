package com.example.securecard.exceptions
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

class NotFoundException(message: String) : RuntimeException(message)

@ControllerAdvice
class ExceptionHandler {
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: NotFoundException): String = e.message ?: "Not Found"
}
