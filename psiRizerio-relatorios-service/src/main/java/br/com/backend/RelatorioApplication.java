package br.com.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.com.backend.Relatorio")
public class RelatorioApplication {
    public static void main(String[] args) {
        SpringApplication.run(RelatorioApplication.class, args);
    }
}