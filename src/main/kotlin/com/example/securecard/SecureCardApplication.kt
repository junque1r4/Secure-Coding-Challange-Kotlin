package com.example.securecard

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SecureCardApplication
// Admin user: admin@securecards.com
// Admin password: admin123
fun main(args: Array<String>) {
    runApplication<SecureCardApplication>(*args)
}

