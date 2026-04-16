package com.github.gaskapiotr.eventhub.gateway.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "EventHub Ticketing API",
                version = "1.0",
                description = "Core API for the EventHub Tech Meetup platform. Demonstrates a strictly decoupled Spring Modulith architecture.",
                contact = @Contact(name = "Piotr Gąska", url = "https://github.com/GaskaPiotr")
        )
)
public class OpenApiConfig {
}
