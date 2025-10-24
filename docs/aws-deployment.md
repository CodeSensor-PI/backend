# Implantação na AWS

Este guia resume como preparar a infraestrutura necessária na AWS para executar os três serviços do back-end e os serviços de apoio (MySQL e RabbitMQ).

## 1. Imagens Docker

1. Use o workflow `🧱 Build and Push Spring Backend Images` para publicar cada imagem no Docker Hub ou em um registro privado.
2. Para usar a AWS, crie repositórios no **Amazon ECR** e configure `secrets.AWS_ACCESS_KEY_ID`, `secrets.AWS_SECRET_ACCESS_KEY` e `secrets.AWS_REGION` no GitHub caso deseje publicar diretamente no ECR.
3. Execute `docker tag`/`docker push` ou adapte o workflow para apontar para o ECR.

## 2. Banco de dados MySQL

1. Crie uma instância **Amazon RDS MySQL** (Multi-AZ opcional) e defina um security group permitindo conexões apenas das instâncias/serviços que vão consumir o banco.
2. Anote `endpoint`, `porta`, usuário e senha do banco. No container do `psiRizerio-services` e `psiRizerio-relatorios-service`, configure:
   - `SPRING_DATASOURCE_URL=jdbc:mysql://<endpoint>:3306/PsiRizerio`
   - `SPRING_DATASOURCE_USERNAME=<usuario>`
   - `SPRING_DATASOURCE_PASSWORD=<senha>`
3. Para migrações ou inicialização, avalie usar Flyway/Liquibase ou scripts executados via pipeline.

## 3. RabbitMQ

Você pode optar por:

- Criar uma instância do **Amazon MQ (RabbitMQ)** e configurar as variáveis `RABBITMQ_HOST`, `RABBITMQ_PORT`, `RABBITMQ_USERNAME`, `RABBITMQ_PASSWORD` com os valores gerados; ou
- Executar RabbitMQ dentro de um container (por exemplo, no mesmo cluster ECS) usando a imagem oficial, semelhante ao `docker-compose.yml`.

## 4. Serviços de aplicação

1. Utilize **AWS ECS (Fargate)** ou **EKS** para orquestrar os containers.
2. Para ECS Fargate:
   - Crie uma *task definition* para cada serviço (`psiRizerio-services`, `psiRizerio-email-service`, `psiRizerio-relatorios-service`).
   - Defina as imagens correspondentes (Docker Hub ou ECR) e configure as variáveis de ambiente listadas em `.env.example`.
   - Aponte as tasks para o mesmo VPC e sub-redes onde o RDS/Amazon MQ estão acessíveis.
   - Configure *service discovery* ou use o endpoint direto do RDS/MQ.
3. Publique os serviços atrás de um **Application Load Balancer** se precisar expor endpoints HTTP.

## 5. Segredos e variáveis de ambiente

- Armazene senhas e App Passwords no **AWS Secrets Manager** ou **SSM Parameter Store**.
- Referencie-os nas task definitions via `secrets` (ECS) para evitar expor valores no código/ci.
- Consulte `.env.example` para a lista de variáveis necessárias.

## 6. Observabilidade e manutenção

- Ative logs no **CloudWatch Logs** para cada container.
- Configure alarmes (CPU/RAM) e métricas de banco no CloudWatch.
- Utilize o `healthCheck` do load balancer para reiniciar tasks automaticamente em caso de falha.

Com essa estrutura, basta atualizar as variáveis de ambiente conforme o ambiente (produção, homologação etc.) e garantir que o workflow continue publicando as imagens atualizadas.
