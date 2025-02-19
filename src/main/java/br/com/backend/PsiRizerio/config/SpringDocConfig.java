package br.com.backend.PsiRizerio.config;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Título da API")
                        .description("Descrição da API")
                        .version("v1.0")
                        .license(new License().name("Licença da API").url("URL da licença")))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentação Externa")
                        .url("https://teste.com.br"));
    }
}
