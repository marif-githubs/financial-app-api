# 📘 Finance Dashboard API Documentation

## 🚀 Setup & Run Guide 
**Make sure the following tools are installed:**      
### 🧰 Required Tools
* Java 17
* Maven (3.8+)
* IntelliJ IDEA (Community Edition) Recommended

###📦 Dependencies Used
* Spring Boot (Web, JPA, Security)
* H2 Database (in-memory)
* JWT (jwt)
* Lombok
* Validation (Jakarta Validation)
* Swagger (springdoc-openapi)

###📥 Clone Repository
```bash
git clone https://github.com/marif-githubs/financial-app-api.git
cd finance-dashboard 
```

### ▶️ Run the Application
Option 1: Using Maven
mvn spring-boot:run
Option 2: From IDE
Open project in IntelliJ
Locate main class:
FinanceDashboardApplication.java
Click Run ▶

### Go to Swagger-ui
```bash
http://localhost:8080/swagger-ui/index.html
```
Swagger UI supports JWT authentication via the Authorize button using Bearer tokens.

### API Flow
Login → Get token → Append in Bearer → Perform actions 

🔗 Base URL
```http://localhost:8080```

🔐 Authentication
Sample Credentials for Login
```
----------------------------------------------
|Role	      |Email	          |Password      |
|Admin	    |admin@test.com   |	1234@Admin   |
|Analyst	  |analyst@test.com |	1234@Analyst |
|Viewer	    |viewer@test.com	| 1234@Viewer  |
----------------------------------------------
```
Login Endpoint
```POST /auth/login```

cURL for admin
```bash
curl -X POST http://localhost:8080/auth/login \
-H "Content-Type: application/json" \
-d '{
  "username": "admin",
  "password": "admin123"
}'
```

cURL for analyst
```bash
curl -X POST http://localhost:8080/auth/login \
-H "Content-Type: application/json" \
-d '{
  "username": "analyst",
  "password": "analyst123"
}'
```

cURL for viewer
```bash
curl -X POST http://localhost:8080/auth/login \
-H "Content-Type: application/json" \
-d '{
  "username": "viewer",
  "password": "viewer123"
}'
```

Response
{
  "token": "eyJhbGciOiJIUzI1NiIs..."
}

### 🔑 Use Token in Requests
Authorization: Bearer <your_token>

### 👤 User APIs (Admin Only)
Create User
Endpoint
```POST /users```
cURL
```bash
curl -X POST http://localhost:8080/users \
-H "Authorization: Bearer <token>" \
-H "Content-Type: application/json" \
-d '{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "gshtr@A1sdgd",
  "role": "ANALYST",
  "active": "false"
}'
```

Get All Users
```bash
curl -X GET http://localhost:8080/users \
-H "Authorization: Bearer <token>"
```

Update User
```bash
curl -X PUT http://localhost:8080/users/<userId> \
-H "Authorization: Bearer <token>" \
-H "Content-Type: application/json" \
-d '{
  "name": "Updated Name",
  "email": "updated@email.com",
  "role": "ADMIN"
}'
```

Delete User
```bash
curl -X DELETE http://localhost:8080/users/1 \
-H "Authorization: Bearer <token>"
```

Activate User
```bash
curl -X PATCH http://localhost:8080/users/1/activate \
-H "Authorization: Bearer <token>"
```

Deactivate User
```bash
curl -X PATCH http://localhost:8080/users/1/deactivate \
-H "Authorization: Bearer <token>"
```

### 💰 Financial Records APIs
Create Record (Admin Only)
Endpoint
```POST /records?userId=<userId>```
cURL
```bash
curl -X POST "http://localhost:8080/records?userId=<userId return by "POST http://localhost:8080/users" api>" \
-H "Authorization: Bearer <token>" \
-H "Content-Type: application/json" \
-d '{
  "amount": 5000,
  "type": "INCOME",
  "category": "Salary",
  "notes": "Monthly salary"
}'
```

Get Records (All Roles)
Basic
```bash
curl -X GET http://localhost:8080/records \
-H "Authorization: Bearer <token>"
```

Filtering Examples
By Type
```bash
curl "http://localhost:8080/records?type=INCOME" \
-H "Authorization: Bearer <token>"
```
By Category
```bash
curl "http://localhost:8080/records?category=Food" \
-H "Authorization: Bearer <token>"
```
By Date Range
```bash
curl "http://localhost:8080/records?startDate=2026-04-01&endDate=2026-04-30" \
-H "Authorization: Bearer <token>"
```

Update Record
```bash
curl -X PUT http://localhost:8080/records/<recordId> \
-H "Authorization: Bearer <token>" \
-H "Content-Type: application/json" \
-d '{
  "amount": 6000,
  "category": "Salary",
  "notes": "Updated salary"
}'
```

Delete Record
```bash
curl -X DELETE http://localhost:8080/records/<recordId> \
-H "Authorization: Bearer <token>"
```

### 📊 Dashboard APIs
Summary (Admin + Analyst)
Endpoint
```GET /dashboard/summary```
cURL
```bash 
curl -X GET http://localhost:8080/dashboard/summary \
-H "Authorization: Bearer <token>"
```
Expected Response
{
  "totalIncome": 10000,
  "totalExpense": 4000,
  "netBalance": 6000
}
```
| API            | Viewer  | Analyst   | Admin |
| -------------- | --------| --------- | ----- |
| View Records   | ✅      | ✅       | ✅     |
| Create Records | ❌      | ❌       | ✅     |
| Dashboard      | ❌      | ✅       | ✅     |
| Manage Users   | ❌      | ❌       | ✅     |
```

## Features
* JWT used for stateless authentication
* Role-based access enforced via Spring Security
* Aggregation queries handled at h2-DB level for performance 
* DTOs used to separate API layer from persistence
* Clean layered architecture (Controller → Service → Repository)


## Swagger-ui url
```bash
http://localhost:8080/swagger-ui/index.html
```

postman import json files
```bash
finance app postman export
    |-dash api.postman_collection
    |-finance app auth api.postman_collection
    |-financial record api.postman_collection
    |-user api.postman_collection
```

h2-db console
```bash
http://localhost:8080/h2-console
```
Driver Class:	org.h2.Driver
JDBC URL:	jdbc:h2:mem:testdb
User Name:	sa
Password:	
