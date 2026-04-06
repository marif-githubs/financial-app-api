📘 Finance Dashboard API Documentation

API Flow
Login → Get token → Append in Bearer → Perform actions 

🔗 Base URL
http://localhost:8080

🔐 Authentication
Login
Endpoint
POST /auth/login

cURL for admin
curl -X POST http://localhost:8080/auth/login \
-H "Content-Type: application/json" \
-d '{
  "username": "admin",
  "password": "admin123"
}'

cURL for analyst
curl -X POST http://localhost:8080/auth/login \
-H "Content-Type: application/json" \
-d '{
  "username": "analyst",
  "password": "analyst123"
}'

cURL for viewer
curl -X POST http://localhost:8080/auth/login \
-H "Content-Type: application/json" \
-d '{
  "username": "viewer",
  "password": "viewer123"
}'

Response
{
  "token": "eyJhbGciOiJIUzI1NiIs..."
}

🔑 Use Token in Requests
Authorization: Bearer <your_token>

👤 User APIs (Admin Only)
Create User
Endpoint
POST /users
cURL
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

Get All Users
curl -X GET http://localhost:8080/users \
-H "Authorization: Bearer <token>"

Update User
curl -X PUT http://localhost:8080/users/<userId> \
-H "Authorization: Bearer <token>" \
-H "Content-Type: application/json" \
-d '{
  "name": "Updated Name",
  "email": "updated@email.com",
  "role": "ADMIN"
}'

Delete User
curl -X DELETE http://localhost:8080/users/1 \
-H "Authorization: Bearer <token>"

Activate User
curl -X PATCH http://localhost:8080/users/1/activate \
-H "Authorization: Bearer <token>"

Deactivate User
curl -X PATCH http://localhost:8080/users/1/deactivate \
-H "Authorization: Bearer <token>"


💰 Financial Records APIs
Create Record (Admin Only)
Endpoint
POST /records?userId=<userId>
cURL
curl -X POST "http://localhost:8080/records?userId=<userId return by "POST http://localhost:8080/users" api>" \
-H "Authorization: Bearer <token>" \
-H "Content-Type: application/json" \
-d '{
  "amount": 5000,
  "type": "INCOME",
  "category": "Salary",
  "notes": "Monthly salary"
}'

Get Records (All Roles)
Basic
curl -X GET http://localhost:8080/records \
-H "Authorization: Bearer <token>"

Filtering Examples
By Type
curl "http://localhost:8080/records?type=INCOME" \
-H "Authorization: Bearer <token>"
By Category
curl "http://localhost:8080/records?category=Food" \
-H "Authorization: Bearer <token>"
By Date Range
curl "http://localhost:8080/records?startDate=2026-04-01&endDate=2026-04-30" \
-H "Authorization: Bearer <token>"

Update Record
curl -X PUT http://localhost:8080/records/<recordId> \
-H "Authorization: Bearer <token>" \
-H "Content-Type: application/json" \
-d '{
  "amount": 6000,
  "category": "Salary",
  "notes": "Updated salary"
}'
Delete Record
curl -X DELETE http://localhost:8080/records/<recordId> \
-H "Authorization: Bearer <token>"

📊 Dashboard APIs
Summary (Admin + Analyst)
Endpoint
GET /dashboard/summary
cURL
curl -X GET http://localhost:8080/dashboard/summary \
-H "Authorization: Bearer <token>"
Expected Response
{
  "totalIncome": 10000,
  "totalExpense": 4000,
  "netBalance": 6000
}

| API            | Viewer | Analyst | Admin |
| -------------- | ------ | ------- | ----- |
| View Records   | ✅      | ✅       | ✅     |
| Create Records | ❌      | ❌       | ✅     |
| Dashboard      | ❌      | ✅       | ✅     |
| Manage Users   | ❌      | ❌       | ✅     |

Features
-JWT used for stateless authentication
-Role-based access enforced via Spring Security
-Aggregation queries handled at h2-DB level for performance
-DTOs used to separate API layer from persistence
-Clean layered architecture (Controller → Service → Repository)

Swagger-ui url
http://localhost:8080/swagger-ui/index.html

postman import json files
finance app postman export
    |-dash api.postman_collection
    |-finance app auth api.postman_collection
    |-financial record api.postman_collection
    |-user api.postman_collection
    
h2-db console
http://localhost:8080/h2-console
Driver Class:	org.h2.Driver
JDBC URL:	jdbc:h2:mem:testdb
User Name:	sa
Password:	
