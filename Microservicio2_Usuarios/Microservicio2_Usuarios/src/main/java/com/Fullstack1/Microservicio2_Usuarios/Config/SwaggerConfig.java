package com.Fullstack1.Microservicio2_Usuarios.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI usuariosOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Microservicio de Usuarios y Notificaciones API")
                .description("Documentación de los endpoints para la gestión de usuarios y alertas del sistema.")
                .version("1.0.0"));
    }
}
