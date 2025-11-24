# Integrantes

- Alexsandro Macedo: RM557068
- Leonardo Faria Salazar: RM557484

## Links

Deploy: [https://gs-2-java.onrender.com/login]

Vide explicativo no YouTube: [https://www.youtube.com/watch?v=OZ9DWHz4LJI]

# Acessos na plataforma

**Acesso com ADMIN**

E-mail: admin@remoteready.com

Senha: 123123ab

**Acesso com OEPRADOR**

E-mail: operador@remoteready.com

Senha: 123123ab

# 🧠 RemoteReady
RemoteReady é uma plataforma moderna desenvolvida para preparar estudantes e profissionais para o modelo de trabalho remoto e híbrido, que hoje domina empresas de tecnologia, inovação e serviços digitais.
O projeto oferece uma experiência completa composta por:

A solução permite que usuários consumam conteúdos curtos sobre produtividade, carreira e boas práticas de trabalho remoto. Conforme avançam na leitura das postagens, o sistema registra automaticamente o progresso e, ao atingir 10 posts lidos, o usuário se torna apto a gerar um certificado digital, comprovando sua jornada de aprendizado.

Além disso, o RemoteReady integra um chat inteligente, onde as conversas entre o usuário e a IA são salvas em banco, permitindo histórico e recomendação de conteúdos. O sistema também disponibiliza uma área dedicada a empresas que contratam profissionais remotos ou híbridos, oferecendo uma visão real do mercado.

A administração de usuários, postagens, empresas e certificados é realizada por um painel web seguro, voltado para gestores ou equipe acadêmica.

O banco de dados Oracle centraliza toda a operação, com tabelas bem estruturadas, relacionamento claro e suporte à execução de procedures externas por meio de um segundo DataSource configurado.

# Imagens demonstrativas da aplicação

<img width="1640" height="980" alt="image" src="https://github.com/user-attachments/assets/3a5160ab-054b-48cc-8ec5-aa4607a63a4b" />
<img width="1597" height="1153" alt="image" src="https://github.com/user-attachments/assets/017ecc64-0c92-4cea-bf30-30a0dcf9101d" />
<img width="1602" height="920" alt="image" src="https://github.com/user-attachments/assets/baffc94d-11f7-4c3f-9095-a5c85c98855c" />
<img width="1787" height="957" alt="image" src="https://github.com/user-attachments/assets/3451513d-7f59-454d-a726-18923291a09c" />
<img width="1545" height="1018" alt="image" src="https://github.com/user-attachments/assets/62266c6f-ee63-45aa-806b-812cc9517c12" />
<img width="1800" height="960" alt="image" src="https://github.com/user-attachments/assets/696a23d9-d5f8-4396-838f-06ec1b695687" />
<img width="1637" height="1100" alt="image" src="https://github.com/user-attachments/assets/68e68d99-054c-48f1-8386-27120612aa39" />



## 📌 Visão Geral

RemoteReady é uma plataforma completa desenvolvida para capacitar usuários em práticas de trabalho remoto e híbrido, oferecendo:

- 📚 Trilha de conteúdos curtos sobre produtividade, carreira e tecnologias
- 🔖 Registro de progresso baseado em posts lidos
- 🏅 Geração automática de certificado ao ler 10 postagens.
- 💬 Histórico de conversas com IA salvo no banco
- 🏢 Lista de empresas contratando remoto ou híbrido
- 🖥️ Painel administrativo Web
- 📱 Aplicativo Mobile (React Native)
- 🗄️ Oracle Database como base principal

---

## 🧱 Arquitetura do Sistema

```
                 ┌───────────────────────────┐
                 │     Aplicativo Mobile      │
                 │   React Native + Expo      │
                 └───────────────┬────────────┘
                                 │
                        REST API │
                                 ▼
                    ┌──────────────────────────┐
                    │     Backend Java API      │
                    │ Spring Boot + JPA + MVC   │
                    └───────────────┬───────────┘
                                    │
                                    ▼
                    ┌───────────────────────────┐
                    │       Oracle Database      │
                    │      Tabelas TB_GS_*       │
                    └───────────────────────────┘

                    ┌──────────────────────────┐
                    │ Painel Web Admin (MVC)   │
                    │   Thymeleaf + Security   │
                    └──────────────────────────┘
```

---

## 🗂 Estrutura do Banco de Dados (Oracle)

### 🧑‍💻 **TB_GS_USUARIO**
- ID_USUARIO (IDENTITY)
- NM_USUARIO
- DS_EMAIL
- DS_PASSWORD
- TP_ROLE (USER, ADMIN, OPERADOR)
- DT_CRIACAO (DEFAULT SYSDATE)

### 📝 **TB_GS_BLOG_POST**
- ID_POST
- DS_TITULO
- DS_DESCRICAO
- DS_IMAGE_URL
- DS_TAG
- DT_CRIACAO

### 📖 **TB_GS_USER_POST** (Posts lidos)
- ID_USER_POST
- ID_USUARIO
- ID_POST
- DS_STATUS (READ)
- DT_LEITURA
- UNIQUE(ID_USUARIO, ID_POST)

### 🏢 **TB_GS_EMPRESA**
- ID_EMPRESA
- NM_EMPRESA
- DS_EMPRESA
- DS_AREA
- FL_HIRING_NOW (Y/N)
- DS_LOGO_URL
- DS_WEBSITE

### 🎓 **TB_GS_CERTIFICADO**
- ID_CERTIFICADO
- ID_USUARIO
- DS_TITULO
- DT_EMISSAO

### 💬 **TB_GS_CHAT_HISTORY**
- ID_CHAT
- ID_USUARIO
- PROMPT
- RESPONSE
- DT_CRIACAO

---

## 🔐 Autenticação & Autorização

- Spring Security 6
- Login via `/login`
- Roles: USER, ADMIN, OPERADOR
- Painel Web protegido

---

## 🌐 Endpoints da API (Mobile)

### 🔖 Posts Lidos

**Marcar como lido**
```
POST /api/user-posts/mark-read?userId={id}&postId={id}
```

**Listar posts lidos**
```
GET /api/user-posts/read-ids?userId={id}
```

**Verificar elegibilidade**
```
GET /api/user-posts/certificate-eligibility?userId={id}
```

---

### 🎓 Certificados

**Gerar certificado**
```
POST /api/certificates/generate?userId={id}
```

**Buscar último certificado**
```
GET /api/certificates/user/{id}/latest
```

---

### 💬 Histórico de Chat

**Salvar conversa**
```
POST /api/chat-history
{
  "userId": 1,
  "prompt": "...",
  "response": "..."
}
```

**Listar histórico**
```
GET /api/chat-history/user/{id}
```

---

## 👤 Login do Mobile

```
POST /api/usuarios/login
{
  "email": "...",
  "password": "..."
}
```

---

## 📱 Aplicativo Mobile

O app consome 100% da API REST e oferece:

- Login
- Lista de posts
- Leitura → “Marcar como lido”
- Perfil
- Barra de progresso
- Certificado
- Lista de empresas
- Histórico de chat

---

## 🖥️ Painel Administrativo (Web)

- Listagem de usuários
- Cadastro de posts
- Cadastro de empresas
- Visualização do progresso
- Dashboard
- Autenticação via Spring Security

---

## 🏗 Tecnologias Utilizadas

### Backend
- Java 17
- Spring Boot 3
- Spring MVC
- Spring Data JPA
- Spring Security
- Oracle DB
- JdbcTemplate

### Mobile
- React Native
- Expo
- Axios

### Web
- Thymeleaf
- HTML/CSS

---

# ▶️ Como Rodar o Backend

## ✅ 1. Pré-requisitos

- Java 17+
- Maven 3.9+
- Oracle Database (local ou remoto)
- Oracle Instant Client (opcional)

---

## ⚙️ 2. Configuração do `application.properties`

Local:
```
src/main/resources/application.properties
```

### Datasource principal (JPA)
```properties
spring.datasource.url=jdbc:oracle:thin:@<HOST>:<PORT>/<SERVICE_NAME>
spring.datasource.username=YOUR_USER
spring.datasource.password=YOUR_PASSWORD
spring.datasource.driver-class-name=oracle.jdbc.driver.OracleDriver

spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.OracleDialect
```

### Segundo datasource (procedures)
```properties
spring.datasource.proc.url=jdbc:oracle:thin:@<HOST>:<PORT>/<SERVICE_NAME>
spring.datasource.proc.username=YOUR_USER
spring.datasource.proc.password=YOUR_PASSWORD
spring.datasource.proc.driver-class-name=oracle.jdbc.driver.OracleDriver
```

---

## ▶️ 3. Rodar com Maven
```sh
mvn spring-boot:run
```

---

## ▶️ 4. Rodar pela IDE
1. Abra o projeto  
2. Localize `RemoteReadyApplication.java`  
3. Clique em **Run**

---

## ▶️ 5. Rodar via JAR
```sh
mvn clean package
java -jar target/gs2-java-0.0.1-SNAPSHOT.jar
```

---

## 🗄️ 6. Testar conexão Oracle
```sql
SELECT 'OK' FROM dual;
```

---

## 🧪 7. Endpoints úteis
- Login: `POST /api/usuarios/login`
- Posts: `GET /api/blog-posts`
- Marcar lido: `POST /api/user-posts/mark-read?userId=1&postId=2`
- Histórico chat: `GET /api/chat-history/user/1`
- Certificado: `POST /api/certificates/generate?userId=1`

---

## 🔐 8. Acessos
ADMIN  
- admin@remoteready.com  
- 123123ab  

OPERADOR  
- operador@remoteready.com  
- 123123ab  


## 🏁 Conclusão

RemoteReady é uma solução completa para ensino e capacitação em trabalho remoto, combinando:

- Web Admin
- API REST
- App Mobile
- Certificação automática
- Histórico de chat integrado
- Segurança
- Oracle Database

Perfeito para fins acadêmicos e demonstração de habilidades profissionais em **Java, APIs, Mobile e Banco Oracle**.

