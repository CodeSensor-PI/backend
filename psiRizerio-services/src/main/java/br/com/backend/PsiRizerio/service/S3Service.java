package br.com.backend.PsiRizerio.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "aws.s3.enabled", havingValue = "true", matchIfMissing = false)
public class S3Service {

    private static final Logger log = LoggerFactory.getLogger(S3Service.class);
    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    /**
     * Faz upload de uma imagem para o S3
     * @param file Arquivo a ser enviado
     * @param folder Pasta no S3 (ex: "pacientes", "psicologos")
     * @return URL pública da imagem
     */
    public String uploadImage(MultipartFile file, String folder) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Arquivo vazio");
        }

        // Valida se é uma imagem
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Arquivo deve ser uma imagem");
        }

        // Gera nome único para o arquivo
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String fileName = folder + "/" + UUID.randomUUID() + extension;

        try {
            // Faz upload para o S3
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(contentType)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            // Retorna a URL pública
            String imageUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, fileName);
            log.info("Imagem enviada com sucesso para o S3: {}", imageUrl);
            return imageUrl;

        } catch (Exception e) {
            log.error("Erro ao fazer upload da imagem para o S3", e);
            throw new RuntimeException("Erro ao fazer upload da imagem: " + e.getMessage());
        }
    }

    /**
     * Deleta uma imagem do S3
     * @param imageUrl URL da imagem a ser deletada
     */
    public void deleteImage(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return;
        }

        try {
            // Extrai o nome do arquivo da URL
            String fileName = imageUrl.substring(imageUrl.lastIndexOf(".com/") + 5);

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
            log.info("Imagem deletada com sucesso do S3: {}", fileName);

        } catch (Exception e) {
            log.error("Erro ao deletar imagem do S3: {}", imageUrl, e);
            // Não lança exceção para não falhar a operação principal
        }
    }
}

