# 📚 School System API

Uma API RESTful desenvolvida com Spring Boot para gerenciar **escolas**, **alunos** e **eventos escolares**. A aplicação possui autenticação segura via **JWT** e controle de acesso baseado em **roles (perfis)**, como ADMIN e USER.

---

## 🧪 Funcionalidades

### 👩‍🏫 Escola (ADMIN)
- ✅ Cadastro e login de escolas com autenticação JWT
- ✅ Atualizar dados da escola autenticada
- ✅ Criar eventos escolares
- ✅ Listar todos os eventos cadastrados
- ✅ Visualizar alunos inscritos nos eventos

### 🧑‍🎓 Aluno (USER)
- ✅ Cadastro e login de alunos
- ✅ Visualizar eventos disponíveis
- ✅ Inscrever-se em eventos
- ✅ Listar eventos em que está inscrito

---

## 🔐 Autenticação e Autorização

- A autenticação é feita com **JWT (JSON Web Token)**.
- As permissões são controladas por **roles**:
  - `ROLE_ADMIN` para escolas
  - `ROLE_USER` para alunos
- As rotas são protegidas com `@PreAuthorize` e filtros de segurança personalizados.

---

## 🏗️ Estrutura do Projeto

```
src
├── config            # Configurações de segurança e JWT
├── controller        # Controllers REST (público e protegido)
├── dtos              # Objetos de transferência de dados
├── exception         # Tratamento global de exceções
├── model             # Entidades JPA (Escola, Evento, Aluno, Endereço)
├── repository        # Interfaces JPA
├── service           # Regras de negócio
├── security           # Spring Security/JWT
└── SchoolSystemApiApplication.java
```

---

## 📁 Endpoints Principais

### 🔑 Autenticação
| Método | Endpoint            | Acesso | Descrição                      |
|--------|----------------------|--------|-------------------------------|
| POST   | `/auth/register/escola`     | Público| Registro de escola |
| POST   | `/auth/register/aluno`     | Público| Registro de aluno |
| POST   | `/auth/login`        | Público| Autenticação e geração do JWT |
| POST   | `/auth/me`        | ADMIN & USER| Informações do perfil autenticado |

### 🏫 Escola
| Método | Endpoint             | Acesso     | Descrição                    |
|--------|----------------------|------------|------------------------------|
| GET    | `/escolas`      | Público      | Listar todas as escolas   |
| PUT    | `/escolas/me`        | ADMIN      | Atualizar dados da escola autenticada |

### 📅 Eventos
| Método | Endpoint                      | Acesso | Descrição                        |
|--------|-------------------------------|--------|----------------------------------|
| GET    | `/eventos`                    | ADMIN & USER| Listar todos os eventos |
| GET   | `/eventos/por-escola?` | USER | Buscar evento por id ou email da escola |
| POST   | `/eventos`                    | ADMIN  | Criar novo evento                |
| POST   | `/eventos/{id}/inscrever/{alunoId}` | USER | Inscrever aluno em evento |
| PUT   | `/eventos/{id}` | ADMIN | Inscrever aluno em evento |


---

## 🔧 Como Executar Localmente

1. Clone o projeto:
```bash
git clone https://github.com/Wesleyweb30/school-system-api.git
cd school-system-api
```

2. Configure seu banco de dados no `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/school_system
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

3. Execute a aplicação:
```bash
./mvnw spring-boot:run
```

---

## 🧪 Testes e Segurança

- JWT é validado com filtro customizado
- Erros são tratados com `@ControllerAdvice`
- Dados sensíveis como senhas são criptografados com `BCrypt`

---

## 🧑‍💻 Autores

Desenvolvido pela equipe  [Faculdade Alpha]  
🔗 [github.com/WesleyWeb30](https://github.com/Wesleyweb30) <br/>
🔗 [github.com/HenriqueSa](https://github.com/ohenriquesa) <br/>
🔗 [github.com/Vivianelf](https://github.com/Vivianelf) <br/>
🔗 [github.com/Alexyaxs](https://github.com/Alexyaxs)

---

## 📝 Licença

Este projeto está licenciado sob a Licença MIT. Veja o arquivo LICENSE para mais detalhes.
