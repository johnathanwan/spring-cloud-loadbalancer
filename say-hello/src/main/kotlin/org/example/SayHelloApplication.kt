package org.example

import mu.*
import org.springframework.boot.*
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.random.*

@RestController
@SpringBootApplication
class SayHelloApplication {
    companion object {
        val logger = KotlinLogging.logger {}
    }

    @GetMapping("/greeting")
    fun greet(): String {
        logger.info { "Access /greeting" }

        val greetings = listOf("Hi there", "Greetings", "Salutations")
        return greetings[Random.nextInt(greetings.size)]
    }

    @GetMapping("/")
    fun home(): String {
        logger.info { "Access /" }
        return "Hi!"
    }
}

fun main(args: Array<String>) {
    runApplication<SayHelloApplication>(*args)
}