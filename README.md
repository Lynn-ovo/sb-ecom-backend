# 🛒 E-Commerce Backend

A full-stack e-commerce application built with **Spring Boot** and **React**, featuring secure authentication, online payments, AI-powered product recommendations, Docker deployment, and Redis caching.

This project was developed to explore enterprise-level backend development practices, including RESTful APIs, authentication, caching, payment integration, and AI services.

---

## 🚀 Features

- 🔐 JWT Authentication & Authorization
- 👥 Role-based Access Control (Admin / Seller / User)
- 🛍️ Product Management
- 🛒 Shopping Cart
- 📦 Order Management
- 💳 Stripe Payment Integration
- 🤖 AI Product Recommendation (OpenAI API)
- 🖼️ AI Image Search for Products
- ⚡ Redis Caching
- 🔒 Redisson Distributed Lock
- 🐳 Dockerized Development Environment
- 📄 RESTful API Design

---

## 🛠️ Tech Stack

### Backend

- Java 21
- Spring Boot
- Spring Security
- JWT Authentication
- Hibernate / JPA
- PostgreSQL
- Redis
- Redisson
- Maven

### Frontend

- React
- Redux
- Axios
- React Router

### AI & Payment

- OpenAI API
- Stripe API

### DevOps

- Docker
- Docker Compose
- Git
- GitHub

---

## 📂 Project Structure

```
sb-ecom-backend
│
├── src
│   ├── controller
│   ├── service
│   ├── repository
│   ├── model
│   ├── security
│   ├── config
│   └── exception
│
├── docker-compose.yml
├── Dockerfile
├── pom.xml
└── README.md
```

---

## 🐳 Run with Docker

Clone the project

```bash
git clone https://github.com/Lynn-ovo/sb-ecom-backend.git
```

Start services

```bash
docker compose up --build
```

The application will start with:

- Spring Boot
- PostgreSQL
- Redis

---

## 🔧 Local Development

Requirements

- Java 21
- Maven
- PostgreSQL
- Redis

Run

```bash
./mvnw spring-boot:run
```

---

## 📌 Core Technologies

- RESTful API Design
- JWT Authentication
- Spring Security
- Database Design
- Redis Cache
- Distributed Lock (Redisson)
- Docker Deployment
- AI Integration
- Stripe Payment

---

## 📸 Screenshots

> Screenshots will be added soon.

- Login
- Product List
- Shopping Cart
- Checkout
- AI Recommendation
- AI Image Search

---

## 📖 Future Improvements

- RabbitMQ
- Elasticsearch
- Microservices (Spring Cloud)
- GitHub Actions CI/CD
- Unit & Integration Testing
- Kubernetes Deployment

---

## 👩‍💻 Author

**Yan Liu**

MSc Software Design & Development

University of Galway

GitHub:
https://github.com/Lynn-ovo
