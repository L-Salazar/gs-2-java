# Integrantes

- Alexsandro Macedo: RM557068
- Leonardo Faria Salazar: RM557484

## Links

Deploy: [https://gs-2-java.onrender.com/login]

# 🧠 RemoteReady
### Plataforma Inteligente para Capacitação em Trabalho Remoto e Híbrido
**Web Admin + API REST + Mobile App + Oracle Database**

---



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

## ▶️ Como Rodar o Backend

```sh
mvn spring-boot:run
```

Configure:

```
spring.datasource.*
spring.second-datasource.*
```

---

## ▶️ Como Rodar o Mobile

```sh
cd mobile
npm install
npx expo start
```

---

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

