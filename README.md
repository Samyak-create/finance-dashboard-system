# 💰 Finance Dashboard Backend

A production-style backend system built using Spring Boot that manages financial transactions with secure authentication, role-based access control (RBAC), and analytics capabilities.

---

## 🚀 Project Overview

This project implements a **centralized financial dashboard backend** where transactions are managed at a system level and accessed based on user roles.

It focuses on real-world backend principles:

- Secure authentication  
- Role-based authorization (RBAC)  
- Clean API design  
- Data filtering & analytics  
- 📈 Financial trends analysis  

---

## 🔐 Security & Role-Based Access

Authentication is implemented using **Spring Security (Basic Auth)** with encrypted passwords (BCrypt).

### Roles

| Role    | Description             |
| ------- | ----------------------- |
| ADMIN   | Full system control     |
| ANALYST | Data access + analytics |
| VIEWER  | Read-only access        |

---

## ⚙️ Tech Stack

- Java 21  
- Spring Boot  
- Spring Security  
- Spring Data JPA  
- MySQL  
- Maven  

---

## 🧱 Architecture

Controller → Service → Repository → Model

---

## 🚀 Running the Application

mvn clean package  
mvn spring-boot:run  

---

## 🔐 Default Admin Credentials

Email: admin@test.com  
Password: 123456  

---

## 📡 API Endpoints & Access Control

---

### 👤 User Management (ADMIN ONLY)

- POST /users → Create user  
- GET /users → Fetch users  
- PUT /users/{id} → Update user  

---

### 💰 Transaction APIs

#### 🔴 ADMIN ONLY

- POST /transactions → Add transaction  
- PUT /transactions/{id} → Update transaction  
- DELETE /transactions/{id} → Delete transaction  

---

#### 🟢 ALL ROLES (ADMIN, ANALYST, VIEWER)

- GET /transactions → Paginated transactions (default page=0, size=5)  
- GET /transactions/all → Fetch all transactions  

---

#### 🟡 ADMIN & ANALYST ONLY

- GET /transactions/filter?type=INCOME  
- GET /transactions/filter?type=EXPENSE&page=0&size=5  
- GET /transactions/date?start=YYYY-MM-DD&end=YYYY-MM-DD  

##### 📊 Summary APIs

- GET /transactions/summary/income  
- GET /transactions/summary/expense  
- GET /transactions/summary/net  

---

## 📈 Trends APIs (NEW 🔥)

---

### 📅 Monthly Trends

- GET /transactions/trends/monthly  
- Access: ADMIN, ANALYST  

Features:
- Aggregates income, expense, savings  
- Covers last 12 months by default  
- Zero-filling for missing months  
- Chronological ordering  

Optional Params:
start=YYYY-MM-DD  
end=YYYY-MM-DD  

---

### 📆 Yearly Trends

- GET /transactions/trends/yearly  
- Access: ADMIN, ANALYST  

Features:
- Aggregated yearly financial data  
- Default range: last 5 years  
- SQL-based grouping  

Optional Params:
start=YYYY-MM-DD  
end=YYYY-MM-DD  

---

### 🧾 Category Trends

- GET /transactions/trends/category  
- Access: ADMIN, ANALYST  

Features:
- Groups by category  
- Separates INCOME vs EXPENSE  
- Identifies top categories  

Optional Params:
start=YYYY-MM-DD  
end=YYYY-MM-DD  

---

## 🧪 How to Test (Postman)

### Step 1: Authentication

Select Basic Auth  

Username: admin@test.com  
Password: 123456  

---

### Step 2: Test APIs

GET /transactions?page=0&size=5  
GET /transactions/filter?type=INCOME  
GET /transactions/date?start=2026-04-01&end=2026-04-10  
GET /transactions/summary/net  
GET /transactions/trends/monthly  
GET /transactions/trends/yearly  
GET /transactions/trends/category  

---

### ❌ RBAC Check

POST /transactions  

Using ANALYST → should return 403 Forbidden  

---

## ⚠️ Assumptions

- Transactions are system-level (not user-specific)  
- Basic authentication is used for simplicity  
- Designed for scalability and extension  

---

## 🔮 Future Enhancements

- Docker containerization  
- JWT-based authentication  
- Advanced analytics (charts & dashboards)  
- Logging & monitoring  

---

## 👨‍💻 Author

Samyak Bageshwar  
