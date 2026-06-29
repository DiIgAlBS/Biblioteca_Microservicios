package com.Fullstack1.Microservicio3_Prestamos.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI prestamosOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Microservicio de Préstamos API")
                .description("Documentación de los endpoints para el control de préstamos y devoluciones de libros.")
                .version("1.0.0"));
    }
}
