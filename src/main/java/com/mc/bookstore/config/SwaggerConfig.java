package com.mc.bookstore.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class SwaggerConfig {

  @Value("${spring.profiles.active:dev}")
  private String activeProfile;

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Robert's Book Store")
                .description("A book store application")
                .version("1.0.0")
                .contact(
                    new Contact().name("Robert Mc Intosh").email("r.mcintosh@rocketmail.com")));
  }
}
