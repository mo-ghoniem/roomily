# 🏡 Roomily — Online Accommodation Marketplace

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue)
![Docker](https://img.shields.io/badge/Docker-Ready-blue)

## 1. Project Overview

**Roomily** is a comprehensive monolithic Spring Boot backend application designed to revolutionize the housing search experience for university students and travelers. The platform addresses the unique challenges faced by students relocating from different governorates, cities, or countries, as well as general guests seeking temporary accommodation.

The application serves as a trusted bridge between students/guests and local hosts, offering a safe, convenient, and budget-friendly alternative to traditional rental agencies and hotels. By facilitating direct communication between tenants and property owners, Roomily creates a transparent marketplace for both short-term and long-term housing solutions.

**Core Value Proposition:**
- **🎓 For Students & Guests**: Discover verified, affordable housing near universities or desired locations, communicate directly with hosts, book viewings, secure accommodation, and manage the entire rental experience through a unified platform.
- **🏠 For Hosts**: Showcase properties to a targeted audience, verify profiles to build trust, set custom rules and pricing, and connect with both students and general guests seeking quality accommodation.
- **🔒 Platform Integrity**: Robust admin controls ensure safety, trust, compliance, and quality across all listings and user interactions.

---

## 2. Project Aims

- **👥 Dual Audience Support**: Serve both university students seeking long-term housing and general guests/travelers needing short-term stays with specialized search and filtering capabilities.
- **💰 Affordability & Trust**: Offer budget-friendly accommodation with verification systems for hosts and properties, transparent pricing, and direct communication to eliminate intermediary fees.
- **⚡ Streamlined Experience**: Centralize the entire housing journey from search to booking to payment in one platform with efficient property management and administrative oversight.

---

## 3. Project Features & Modules

Roomily is architected as a modular monolithic application with distinct functional domains:

### 👤 User Management & Authentication
- Secure user registration and JWT-based authentication
- Role-based access control: Students/Guests, Hosts, Admins
- User profile management with update capabilities
- Role assignment system allowing users to become hosts

### 🏘 Property Management
- Comprehensive property listing creation (title, location, pricing, amenities, availability)
- Property management and geo-location support
- Host-owned property portfolio management
- Property update and deletion
- Availability tracking and booking status management

### 🔍 Advanced Property Search
- Dynamic property search with multiple filter criteria:
  - Location-based search with partial matching
  - Price range filtering
  - Check-in/check-out date availability
  - Property title search
- Specification-based query building for flexible search combinations
- Optimized database queries using JPA Specifications

### 📅 Booking System
- Guest booking creation with customizable guest count
- Booking status tracking (Pending, Confirmed, Cancelled)
- Automated timestamps for booking creation/modifications
- Host-guest relationship management through bookings
- Booking cancellation and update functionality

### ⭐ Review & Rating System
- User-generated reviews with ratings and comments
- Timestamp tracking for review submissions
- Foundation for property reputation management

### 🌐 External Property Integration
- Integration with external property scraping API (FastAPI service)
- Automated property data extraction
- Bulk property import
- Location-based external property retrieval

### 🔒 Security & Authorization
- RSA-based JWT token generation and validation
- Stateless session management
- Role-based endpoint protection
- Password encryption with BCrypt
- Custom authentication filters and providers

### 📄 API Documentation
- Interactive Swagger UI for API exploration
- OpenAPI 3.0 specification
- Comprehensive endpoint documentation for developers

---

## 4. Technologies, Concepts & Tools

### 💻 Core Framework & Language
- **Java 21**
- **Spring Boot 3.4.1**
- **Maven**

### 🌱 Spring Ecosystem
- **Spring Web**: RESTful API development
- **Spring Data JPA**: ORM & database abstraction
- **Spring Security**: Authentication & authorization
- **Spring Validation**: Request validation

### 🗄 Database & Persistence
- **PostgreSQL**
- **Hibernate**
- **JPA Specifications**

### 🔑 Security & Authentication
- **JWT (JSON Web Tokens)**
- **RSA Encryption (RS256)**
- **BCrypt password hashing**
- **Custom security filters**

### 🌐 API & Integration
- **RestTemplate** for external API calls
- **FastAPI service** for property scraping
- **Custom API clients**

### 🛠 Development Tools
- **Lombok**
- **Springdoc OpenAPI**
- **Swagger UI**

### 🏗 Architectural Concepts
- **Monolithic Architecture**
- **Package-by-Feature Architecture**
- **DTO & Mapper Pattern**
- **Repository & Service Layer Patterns**
- **Specification Pattern**
- **Role-Based Access Control (RBAC)**

### ⚙️ Configuration & Deployment
- **Docker Compose**
- **Externalized Application Properties**

---

## 5. Future Work

### 🤖 AI-Powered Enhancements
- **Intelligent Recommendations**: ML algorithms suggest properties for guests based on preferences, search history, and budget; AI-driven pricing for hosts
- **User Insights**: Analyze guest behavior and generate host performance metrics

### 💬 Advanced Matching & Communication
- **Smart Matching**: Compatibility scoring with semantic search and NLP
- **Real-Time Communication**: Messaging, notifications, multi-language support, video tours, AI chatbot
- **Community Features**: Connect students with similar backgrounds, verified host badges, detailed guest profiles

---

**Roomily** – *Connecting Students and Guests with Trusted, Affordable Housing*

## ⚙️ Setup & Run

### Prerequisites
- Java 17+
- Maven 3+
- Docker & Docker Compose
- PostgreSQL (or use Docker setup below)

### Steps
```bash
# 1. Clone the repository
git clone https://github.com/yourusername/roomily.git
cd roomily

# 2. Build the project
mvn clean install

# 3. Run using Docker Compose
docker-compose up
