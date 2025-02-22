# 📌 Spring Boot REST API

A simple REST API application created in **Spring Boot** that supports CRUD operations on users.

## 🛠️ Technologies

- Java 17
- Spring Boot 3.4.1
- Spring Web
- Spring Data JPA
- Spring Security
- H2 (embedded data base)
- Maven
- Mockito

## ⚙️ Requirements

Before running, make sure you have it installed:

- **Java 17** or newer (`java -version`)
- **Maven** (`mvn -version`)

## 🚀 Run the application locally

1. **Clone repository**
   ```sh
   git clone https://github.com/AgnieszkaNeu/spring-security-demo-app.git
   cd spring-security-demo-app
   ```
2. **Build app**
   ```sh
   mvn clean install
   ```
3. **Run app**
   ```sh
   mvn spring-boot:run
   ```

***Available endpoints***
| method  | Endpoint      | Description                        | Authorisation
|---------|---------------|------------------------------------|---------------|
| GET     |/authorities   | Permissions of the logged in user  | Permit all
| GET     |/              | Displays simple welcome String      | Permit all
| GET     |/users         | Displays all users | Admin
| GET     |/user/{id}     | Returns user by ID | Admin
| POST    |/user/to_regular/{id} | Changes the user role from PREMIUM_USER to USER by given ID | Admin
| POST    |/user/to_premium/{id} | Changes the user role from USER to PREMIUM_USER by given ID | Admin
| POST    |/user | Creates a new user | Admin
| DELETE  |/user/{id} | Deletes a user by given ID | Admin





