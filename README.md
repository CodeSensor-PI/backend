# 📌 API do Sistema

Este repositório contém a API do sistema, desenvolvida em **Java Spring**. Ele é responsável pela lógica de negócios, persistência de dados e exposição de endpoints para o frontend.

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **Spring Data JPA**
- **MySQL**
- **Docker**
- **Swagger (OpenAPI)**
- **Maven**

## 📦 Instalação e Configuração

### 🔧 Pré-requisitos
Antes de começar, certifique-se de ter instalado em sua máquina:
- **Java 21** ou superior
- **Maven**
- **Docker**
- **Mysql**
- **Postman** ou **Insomnia** (opcional)
- **IDE** (IntelliJ IDEA, Eclipse, etc)

### 🔥 Configuração do Banco de Dados
A API utiliza um banco de dados **Mysql**. Você pode rodá-lo localmente ou via Docker:

```sh
docker run --name meu-mysql -e MYSQL_ROOT_PASSWORD=senha -e MYSQL_DATABASE=meubanco -e MYSQL_USER=paciente -e MYSQL_PASSWORD=senha -p 3306:3306 -d mysql
```

### ▶️ Rodando a Aplicação

1. Clone o repositório:
   ```sh
   git clone https://github.com/CodeSensor-PI/backend
   ```
2. Acesse o diretório do projeto:
   ```sh
   cd backend
   ```
3. Configure o arquivo **application.properties** ou **application.yml** com as credenciais do banco de dados.


4. Execute a aplicação:
   ```sh
   mvn spring-boot:run
   ```

---

## ✉️ psiRizerio-email-service (RabbitMQ + SMTP)

Este módulo consome mensagens do RabbitMQ e envia e-mails. Ele está preparado para rodar em dois modos:
- Modo Dev (padrão): envio de e-mail No-Op (simulado), sem exigir SMTP.
- Modo Real: envio via SMTP (Gmail, Outlook, etc.).

### 1) Subir o RabbitMQ (local)
Use o docker-compose na raiz do projeto (já inclui RabbitMQ com UI):

```sh
# na pasta raiz do repositório
docker compose up -d rabbitmq
```

- AMQP: amqp://guest:guest@localhost:5672
- UI: http://localhost:15672 (guest/guest)

Se precisar reiniciar: `docker compose restart rabbitmq` ou `docker compose down && docker compose up -d rabbitmq`.

### 2) Variáveis de ambiente principais
Já existem defaults em psiRizerio-email-service/src/main/resources/application.properties.
- RabbitMQ (opcional, pois os defaults já apontam para localhost:5672)
  - RABBITMQ_HOST, RABBITMQ_PORT, RABBITMQ_USERNAME, RABBITMQ_PASSWORD
  - EMAIL_QUEUE, EMAIL_EXCHANGE, EMAIL_ROUTING_KEY (defaults: emailQueue, email-exchange, email.send)
- SMTP
  - APP_MAIL_ENABLED: true para envio real; false (padrão) para No-Op
  - MAIL_HOST, MAIL_PORT, MAIL_USERNAME, MAIL_PASSWORD
  - MAIL_SMTP_AUTH=true
  - MAIL_STARTTLS=true (para TLS na porta 587)
  - Para SSL na porta 465: MAIL_STARTTLS=false e SPRING_MAIL_PROPERTIES_MAIL_SMTP_SSL_ENABLE=true
  - Opcional: APP_MAIL_FROM (remetente; se ausente, usa spring.mail.username)

Observações importantes:
- O email-service carrega automaticamente .env da raiz e do próprio módulo: `spring.config.import=optional:file:.env,optional:file:../.env`.
- Se os logs mostrarem `SMTP config: host=, port=0...`, as variáveis não foram carregadas: verifique o caminho do `.env` e a execução via IDE/terminal.

### 2.1) Arquivo .env (raiz do projeto)
Um arquivo `.env` foi criado na raiz do repositório com valores padrão para desenvolvimento. O Docker Compose carrega esse arquivo automaticamente.

Principais variáveis no `.env`:
- RabbitMQ: RABBITMQ_HOST, RABBITMQ_PORT, RABBITMQ_USERNAME, RABBITMQ_PASSWORD, EMAIL_QUEUE, EMAIL_EXCHANGE, EMAIL_ROUTING_KEY
- SMTP: APP_MAIL_ENABLED, APP_MAIL_FROM, MAIL_HOST, MAIL_PORT, MAIL_USERNAME, MAIL_PASSWORD, MAIL_SMTP_AUTH, MAIL_STARTTLS, MAIL_TIMEOUT_MS, SPRING_MAIL_PROPERTIES_MAIL_SMTP_SSL_ENABLE (opcional)
- MySQL (para o compose): MYSQL_ROOT_PASSWORD, MYSQL_DATABASE, MYSQL_USER, MYSQL_PASSWORD

Como usar:
- Docker Compose: apenas edite o `.env` e rode `docker compose up -d rabbitmq`.
- Rodando local (fora do Docker): exporte as variáveis do `.env` no seu terminal antes de `mvn spring-boot:run`.
  - PowerShell (exemplo): `$env:APP_MAIL_ENABLED='true'` etc. (veja exemplos abaixo em SMTP).

Exemplo (Windows PowerShell) – TLS 587 (Gmail/Outlook):
```powershell
$env:APP_MAIL_ENABLED='true'
$env:APP_MAIL_FROM='seu_email@dominio.com'

$env:MAIL_HOST='smtp.gmail.com'     # ou smtp.office365.com
$env:MAIL_PORT='587'
$env:MAIL_USERNAME='seu_email@dominio.com'
$env:MAIL_PASSWORD='SENHA_OU_APP_PASSWORD'
$env:MAIL_SMTP_AUTH='true'
$env:MAIL_STARTTLS='true'
```
SSL 465:
```powershell
$env:APP_MAIL_ENABLED='true'
$env:APP_MAIL_FROM='seu_email@dominio.com'

$env:MAIL_HOST='smtp.seuprovedor.com'
$env:MAIL_PORT='465'
$env:MAIL_USERNAME='seu_email@dominio.com'
$env:MAIL_PASSWORD='SENHA_OU_APP_PASSWORD'
$env:MAIL_SMTP_AUTH='true'
$env:MAIL_STARTTLS='false'
$env:SPRING_MAIL_PROPERTIES_MAIL_SMTP_SSL_ENABLE='true'
```

Dicas por provedor:
- Gmail: ative 2FA e use App Password (16 dígitos); “apps menos seguros” não é suportado.
- Outlook/Office 365: normalmente TLS 587; App Password pode ser necessário.
- Muitos provedores exigem que o “from” seja igual ao username.

### 3) Rodar o serviço de e-mail
```sh
cd psiRizerio-email-service
mvn spring-boot:run
```

- Em Dev (APP_MAIL_ENABLED=false), verá logs [NO-OP] ao “enviar” e-mails.
- Em produção (APP_MAIL_ENABLED=true) com SMTP válido, o e-mail é enviado de fato.

### 4) Rodar os dois serviços ao mesmo tempo
Abra dois terminais:
- Terminal A (serviço principal):
  ```sh
  cd psiRizerio-services
  mvn spring-boot:run
  ```
- Terminal B (email-service):
  ```sh
  cd psiRizerio-email-service
  mvn spring-boot:run
  ```

Dica Maven (alternativa): você pode direcionar por módulo usando o reactor:
```sh
mvn -pl psiRizerio-services spring-boot:run
mvn -pl psiRizerio-email-service spring-boot:run
```

### 5) Publicar uma mensagem de teste no RabbitMQ
Via UI (http://localhost:15672):
- Exchanges > email-exchange > Publish message
- Routing key: email.send
- Payload (JSON):
```
{"to":"destinatario@exemplo.com","subject":"Teste","body":"Olá!"}
```

### 6) Contrato de mensagem
- Formato: JSON
- Campos obrigatórios: `to`, `subject`, `body`
- Content-Type no RabbitMQ é opcional; o consumidor usa conversor Jackson no corpo.

### 7) Troubleshooting rápido
- Connection refused (RabbitMQ): suba o broker (`docker compose up -d rabbitmq`) e verifique as portas 5672/15672; confirme RABBITMQ_HOST/PORT.
- Failed to convert message: o payload não é JSON válido (ex.: começa com parêntese). Envie exatamente o JSON do exemplo.
- MailAuthenticationException: `failed to connect, no password specified?`
  - Defina `MAIL_PASSWORD` (ou `APP_SMTP_PASSWORD`) e `MAIL_USERNAME`.
  - Garanta `APP_MAIL_ENABLED=true` para envio real.
  - Para Gmail, use App Password (2FA habilitado) e `MAIL_STARTTLS=true` (porta 587).
  - Se usar 465, desative STARTTLS e habilite `SPRING_MAIL_PROPERTIES_MAIL_SMTP_SSL_ENABLE=true`.
- Variáveis não carregadas: confira se o `.env` está na raiz do repositório (ou um `.env` no diretório do email-service). Rodando via IDE, configure as env vars na Run Configuration.
- Debug de autoconfiguração: rode com `--debug` para ver o Condition Evaluation Report.

---
