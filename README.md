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
docker run --name meu-mysql -e MYSQL_ROOT_PASSWORD=senha -e MYSQL_DATABASE=meubanco -e MYSQL_USER=usuario -e MYSQL_PASSWORD=senha -p 3306:3306 -d mysql
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



