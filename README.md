# 🏡 Roomily — Online Accommodation Marketplace

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue)
![Docker](https://img.shields.io/badge/Docker-Ready-blue)
![License: MIT](https://img.shields.io/badge/License-MIT-yellow)

**Roomily** is a seamless online marketplace for discovering and booking short- and long-term accommodations — designed for **students and travelers**.  
It connects **hosts** who have available spaces with **guests** looking for comfortable and affordable stays, similar to Airbnb.

---

## ✨ Features
- 🏠 Browse and book accommodations for short or long stays  
- 👤 Register and manage profiles for hosts and guests  
- 🏷️ Create, update, and manage property listings  
- 💳 Secure booking and payment workflow *(planned)*  
- 🔍 Search by location, price, or property type  
- 📅 Real-time availability and booking confirmation  
- 🔒 Authentication and authorization with Spring Security or Keycloak  

---

## 🧩 Tech Stack
- **Language:** Java 17  
- **Framework:** Spring Boot 3  
- **Database:** PostgreSQL  
- **Security:** Spring Security / Keycloak  
- **Build Tool:** Maven  
- **Deployment:** Docker & Docker Compose  
- **Testing:** JUnit, Mockito  

---

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
