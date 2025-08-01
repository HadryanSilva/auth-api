# Auth API

Implementation of Authentication using Spring Boot + Spring Session + Redis

## 📖 About

This project is a REST API for authentication built with Spring Boot that implements session-based authentication using Redis as the session store. The API provides secure user registration, login, and logout functionality with session management.

### Key Features

- **Session-based Authentication**: Uses Spring Session with Redis for distributed session management
- **User Registration**: Create new user accounts with validation
- **Secure Login/Logout**: Authentication with session management
- **Password Encryption**: BCrypt password encoding
- **Session Security**: HTTP-only cookies with configurable security settings
- **Input Validation**: Bean validation for request data
- **RESTful Design**: Clean REST API endpoints

## 🛠️ Technologies

- **Java 21**
- **Spring Boot 3.5.4**
- **Spring Security**
- **Spring Session Data Redis**
- **Spring Data JPA**
- **PostgreSQL**
- **Redis**
- **MapStruct** (for object mapping)
- **Maven** (build tool)
- **Docker Compose** (for local development)

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

- Java 21 or higher
- Maven 3.6+
- Docker and Docker Compose
- Git

## 🚀 Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/HadryanSilva/auth-api.git
   cd auth-api
   ```

2. **Start the required services (PostgreSQL and Redis)**
   ```bash
   docker-compose up -d
   ```

3. **Build the project**
   ```bash
   ./mvnw clean compile
   ```

4. **Run the application**
   ```bash
   ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
   ```

The application will start on `http://localhost:8080`

## ⚙️ Configuration

### Database Configuration

The application uses PostgreSQL as the main database. Configuration is in `application-dev.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/postgres?currentSchema=auth_db
    username: postgres
    password: postgres
```

### Redis Configuration

Redis is used for session storage:

```yaml
spring:
  data:
    redis:
      host: localhost
      port: 6379
      database: 0
```

### Session Configuration

Session settings for security and timeout:

```yaml
server:
  servlet:
    session:
      cookie:
        http-only: true
        secure: false  # Set to true in production
        same-site: lax
        max-age: 1800
      timeout: 30m
```

## 🔗 API Endpoints

### Authentication Endpoints

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/api/v1/auth/login` | User login | Public |
| GET | `/api/v1/auth/logout` | User logout | Authenticated |

### User Endpoints

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/api/v1/users/create` | Create new user | Public |
| GET | `/api/v1/users/{id}` | Get user by ID | Authenticated |

## 🏗️ Architecture

### Project Structure

```
src/main/java/br/com/hadryan/api/
├── config/                 # Security and configuration classes
│   ├── SecurityConfig.java
│   └── SessionAuthenticationFilter.java
├── controller/             # REST controllers
│   ├── AuthController.java
│   └── UserController.java
├── mapper/                 # DTOs and mappers
│   ├── UserMapper.java
│   ├── request/
│   └── response/
├── model/                  # Entity classes
│   ├── User.java
│   ├── AuthUser.java
│   └── Role.java
├── repository/             # Data access layer
│   └── UserRepository.java
└── service/               # Business logic
    ├── SessionService.java
    ├── UserService.java
    └── CustomUserDetailsService.java
```

### Security Architecture

1. **SessionAuthenticationFilter**: Custom filter that validates sessions and reloads user details
2. **SecurityConfig**: Main security configuration with session management
3. **CustomUserDetailsService**: Loads user details from database
4. **SessionService**: Manages authentication and session lifecycle

### Session Management

- Sessions are stored in Redis for scalability
- Session cookies are HTTP-only for security
- Maximum session timeout: 30 minutes
- Session validation on each request

## 📝 Usage Examples

### 1. Create a New User

```bash
curl -X POST http://localhost:8080/api/v1/users/create \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "password": "securePassword123"
  }'
```

### 2. Login

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -c cookies.txt \
  -d '{
    "email": "john.doe@example.com",
    "password": "securePassword123"
  }'
```

### 3. Access Protected Resource

```bash
curl -X GET http://localhost:8080/api/v1/users/{user-id} \
  -H "Content-Type: application/json" \
  -b cookies.txt
```

### 4. Logout

```bash
curl -X GET http://localhost:8080/api/v1/auth/logout \
  -b cookies.txt
```

## 🗃️ Database Schema

The application automatically creates the following schema:

### Users Table
```sql
CREATE TABLE users (
    id UUID PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);
```

## 🔒 Security Features

- **Password Encryption**: BCrypt with strength 12
- **Session Security**: HTTP-only cookies prevent XSS attacks
- **CSRF Protection**: Disabled for API usage (enable for web applications)
- **Input Validation**: Bean validation on all request DTOs
- **Session Timeout**: Automatic session expiration
- **User Session Validation**: Real-time validation of user status

## 🐳 Docker Support

The project includes a `compose.yml` file for easy local development:

```bash
# Start PostgreSQL and Redis
docker-compose up -d

# Stop services
docker-compose down

# View logs
docker-compose logs -f
```

## 🚀 Production Considerations

Before deploying to production:

1. **Enable HTTPS**: Set `server.servlet.session.cookie.secure=true`
2. **Environment Variables**: Use environment variables for sensitive configuration
3. **Database Migration**: Consider using Flyway or Liquibase
4. **Monitoring**: Add application monitoring and health checks
5. **Rate Limiting**: Implement rate limiting for authentication endpoints
6. **CORS Configuration**: Configure CORS for frontend applications

## 👨‍💻 Author

**Hadryan Silva**

---