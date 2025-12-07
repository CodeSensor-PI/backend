package br.com.backend.PsiRizerio.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@ConditionalOnProperty(name = "aws.s3.enabled", havingValue = "true", matchIfMissing = false)
public class S3Config {

    private static final Logger log = LoggerFactory.getLogger(S3Config.class);

    @Value("${aws.accessKeyId}")
    private String accessKeyId;

    @Value("${aws.secretAccessKey}")
    private String secretAccessKey;

    @Value("${aws.sessionToken:}")
    private String sessionToken;

    @Value("${aws.region}")
    private String region;

    @Bean
    public S3Client s3Client() {
        log.info("🔧 Configurando S3Client...");
        log.info("   📍 Região: {}", region);
        log.info("   🔑 Access Key ID: {}... ({})",
            accessKeyId != null && accessKeyId.length() > 10 ? accessKeyId.substring(0, 10) : "VAZIO",
            accessKeyId != null ? accessKeyId.length() + " caracteres" : "0");
        log.info("   🔐 Secret Access Key: {}... ({})",
            secretAccessKey != null && secretAccessKey.length() > 10 ? secretAccessKey.substring(0, 10) : "VAZIO",
            secretAccessKey != null ? secretAccessKey.length() + " caracteres" : "0");
        log.info("   🎫 Session Token: {}",
            sessionToken != null && !sessionToken.isEmpty() ? "PRESENTE (" + sessionToken.length() + " caracteres)" : "AUSENTE");

        // Validar credenciais antes de tentar criar o client
        if (accessKeyId == null || accessKeyId.trim().isEmpty()) {
            log.error("❌ AWS_ACCESS_KEY_ID está vazio ou nulo!");
            log.error("   💡 Verifique o arquivo .env e certifique-se que está carregando corretamente");
            throw new IllegalStateException("AWS_ACCESS_KEY_ID não pode ser vazio. Configure no .env");
        }

        if (secretAccessKey == null || secretAccessKey.trim().isEmpty()) {
            log.error("❌ AWS_SECRET_ACCESS_KEY está vazio ou nulo!");
            throw new IllegalStateException("AWS_SECRET_ACCESS_KEY não pode ser vazio. Configure no .env");
        }

        try {
            // Verifica se tem session token (conta de estudante AWS Academy)
            if (sessionToken != null && !sessionToken.isEmpty()) {
                log.info("   📚 Tipo de credencial: AWS Academy/Learner Lab (Session Credentials)");

                AwsSessionCredentials awsCredentials = AwsSessionCredentials.create(
                    accessKeyId.trim(),
                    secretAccessKey.trim(),
                    sessionToken.trim()
                );

                S3Client client = S3Client.builder()
                        .region(Region.of(region))
                        .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                        .build();

                log.info("✅ S3Client configurado com sucesso! (Com Session Token)");
                log.info("   ⚠️ Lembre-se: Credenciais do Learner Lab expiram em 4 horas!");
                return client;
            } else {
                log.info("   🏢 Tipo de credencial: Conta Normal (Basic Credentials)");
                log.warn("   ⚠️ Você tem uma conta de estudante? Não esqueça do AWS_SESSION_TOKEN!");

                AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(
                    accessKeyId.trim(),
                    secretAccessKey.trim()
                );

                S3Client client = S3Client.builder()
                        .region(Region.of(region))
                        .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                        .build();

                log.info("✅ S3Client configurado com sucesso! (Sem Session Token)");
                return client;
            }
        } catch (Exception e) {
            log.error("❌ ERRO FATAL ao configurar S3Client:");
            log.error("   🔴 Tipo de erro: {}", e.getClass().getSimpleName());
            log.error("   💬 Mensagem: {}", e.getMessage());
            log.error("   🔍 Stack trace:");
            e.printStackTrace();

            // Mensagens de ajuda específicas
            if (e.getMessage() != null) {
                if (e.getMessage().contains("cannot be blank") || e.getMessage().contains("cannot be null")) {
                    log.error("");
                    log.error("   💡 SOLUÇÃO: Verifique o arquivo .env");
                    log.error("      - AWS_ACCESS_KEY_ID está preenchido?");
                    log.error("      - AWS_SECRET_ACCESS_KEY está preenchido?");
                    log.error("      - AWS_SESSION_TOKEN está preenchido? (contas estudante)");
                }
            }

            throw new RuntimeException("Falha ao configurar S3Client. Verifique as credenciais no .env", e);
        }
    }
}

