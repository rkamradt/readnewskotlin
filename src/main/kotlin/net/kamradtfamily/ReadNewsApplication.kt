package net.kamradtfamily

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class ReadNewsApplication

fun main(args: Array<String>) {
    SpringApplication.run(ReadNewsApplication::class.java, *args)
}
