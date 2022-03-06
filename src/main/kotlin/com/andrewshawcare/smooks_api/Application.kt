package com.andrewshawcare.smooks_api

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
open class Application {
    @Bean
    open fun unEdifactInterchangeFactoryStrategy(): UNEdifactInterchangeFactoryStrategy = UNEdifactInterchangeFactoryStrategy()

    @Bean
    open fun unEdifactMessageService (
        unEdifactInterchangeFactoryStrategy: UNEdifactInterchangeFactoryStrategy,
        kafkaTemplate: KafkaTemplate<String, String>
    ): UNEdifactMessageService = UNEdifactMessageService(unEdifactInterchangeFactoryStrategy, kafkaTemplate)

    @Bean
    open fun addCorsConfig(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(corsRegistry: CorsRegistry) {
                corsRegistry.addMapping("/**")
                    .allowedOrigins(CorsConfiguration.ALL)
            }
        }
    }

    @Bean
    open fun newTopic(): NewTopic = TopicBuilder.name("events").build()
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}