# 🚌 Bus Station Backend

**Projet :** Plateforme de mise en relation entre voyageurs et agences de voyages  
**Stack :** Spring Boot WebFlux, PostgreSQL (R2DBC), Redis, Kafka, Resilience4J, Swagger/OpenAPI

---

## 📖 Description

Le backend `bus_station_backend` est une application **réactive** permettant :

- La gestion des voyages proposés par des agences de voyages.
- La réservation et l’annulation de voyages par les passagers.
- La gestion des utilisateurs (passagers, employés, chauffeurs, agences).
- La notification en temps réel (email, SMS, push) pour chaque événement.
- L’intégration d’API externes (paiement, géolocalisation, messagerie).
- Une architecture hexagonale avec séparation claire entre **domain**, **application** et **infrastructure**.

Le projet est conçu pour être **cloud-ready**, résilient et scalable grâce à :

- Kafka pour le messaging asynchrone.
- Redis pour le caching.
- Resilience4J pour la tolérance aux pannes.

---

## 🏗️ Architecture

### 1. Domain
- Entités métiers, règles métier, événements et exceptions.
- Ports sortants abstraits (repositories).

### 2. Application
- Cas d’usage (Use Cases) orchestrant les opérations métier.
- DTOs et mappers (Domain ↔ DTO).
- Ports entrants et sortants.

### 3. Infrastructure
- Adaptateurs pour REST, Kafka, Redis, PostgreSQL.
- Configurations techniques (WebFlux, Kafka, Redis, R2DBC, Resilience).
- Notification Factory et API externes.
- Exception handling global.

---

## ⚙️ Prérequis

- Java 21+
- Maven 3.9+
- PostgreSQL 17+
- Redis 7+
- Kafka 3+ (ou Confluent Platform)
- IDE : IntelliJ IDEA ou VSCode recommandé

---

## 🛠️ Installation et lancement

1. **Cloner le projet**
```bash
git clone https://github.com/ton-organisation/bus_station_backend.git
cd bus_station_backend
```

2. **Configurer les variables sensibles**

Crée un fichier .env ou exporte les variables système pour les secrets (MAIL_PASSWORD, PAYMENT_SECRET, etc.)

Configurer application.properties
Exemple :
```properties
spring.r2dbc.url=r2dbc:postgresql://localhost:5432/reservation_annulation
spring.r2dbc.username=postgres
spring.r2dbc.password=ton_mot_de_passe

spring.data.redis.host=localhost
spring.data.redis.port=6379

spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=bus-station-group
```
3. **Construire le projet**

```bash
mvn clean install
```

4. **Lancer l’application**

```bash
mvn spring-boot:run
```


5. **Swagger / API Docs**

http://localhost:8080/swagger-ui.html
http://localhost:8080/v3/api-docs

## Structure du projet
```swift
src/main/java/com/example/travelplatform/
├── domain
├── application
├── infrastructure
│   ├── config
│   ├── adapter
│   │   ├── inbound
│   │   └── outbound
```

## 📝 Auteur

Jonathan Bachelard

Projet réalisé pour la plateforme Bus Station Backend – Architecture Hexagonale + WebFlux + PostgreSQL + Redis + Kafka.


