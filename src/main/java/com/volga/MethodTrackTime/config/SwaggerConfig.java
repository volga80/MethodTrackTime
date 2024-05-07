package com.volga.MethodTrackTime.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@OpenAPIDefinition(
        info = @Info(
                title = "Method Track Time",
                version = "1.0.0",
                contact = @Contact(
                        name = "Volgin Angrei"
                )
        )
)
public class SwaggerConfig implements WebMvcConfigurer {
}
