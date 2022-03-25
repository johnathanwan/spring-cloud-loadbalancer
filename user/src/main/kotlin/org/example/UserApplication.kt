package org.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.*
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@SpringBootApplication
@RestController
class UserApplication {

    @Autowired
    private val loadBalancedWebClientBuilder: WebClient.Builder? = null

    @Autowired
    private val lbFunction: ReactorLoadBalancerExchangeFilterFunction? = null

    @RequestMapping("/hi")
    fun hi(@RequestParam(value = "name", defaultValue = "Mary") name: String): Mono<String> {
        return loadBalancedWebClientBuilder!!.build().get().uri("http://say-hello/greeting")
            .retrieve().bodyToMono(String::class.java)
            .map { "$it, $name" }
    }

    @RequestMapping("/hello")
    fun hello(@RequestParam(value = "name", defaultValue = "John") name: String): Mono<String> {
        return WebClient.builder().filter(lbFunction!!).build().get().uri("http://say-hello/greeting")
            .retrieve().bodyToMono(String::class.java)
            .map { "$it, $name" }
    }
}

fun main(args: Array<String>) {
    runApplication<UserApplication>(*args)
}