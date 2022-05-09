package com.example.demo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DemoController {

    @GetMapping("/hello")
    fun hello() = "Hello"

    @GetMapping("/auth-hello")
    fun authHello() = "Authed hello!"
}
