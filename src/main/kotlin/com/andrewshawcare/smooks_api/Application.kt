package com.andrewshawcare.smooks_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
open class Application {
    @Bean
    open fun addCorsConfig(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(corsRegistry: CorsRegistry) {
                corsRegistry.addMapping("/**")
                    .allowedOrigins(CorsConfiguration.ALL)
            }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}