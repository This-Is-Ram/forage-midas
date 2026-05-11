````markdown
# Midas Core – JPMorganChase Advanced Software Engineering Virtual Experience

Midas Core is a Spring Boot–based backend service developed as part of the JPMorganChase Advanced Software Engineering Virtual Experience Program on Forage. The project simulates a financial transaction processing system capable of consuming transactions through Kafka, validating and persisting data with a relational database, integrating with an external Incentive API, and exposing REST endpoints for balance queries.

## Features

- Kafka-based transaction ingestion
- Spring Boot microservice architecture
- H2 in-memory SQL database integration
- Spring Data JPA entity modeling and persistence
- Transaction validation and balance management
- REST API integration using `RestTemplate`
- Incentive calculation workflow integration
- REST endpoint for user balance retrieval
- Maven-based automated test execution

---

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 17 | Core programming language |
| Spring Boot | Backend framework |
| Apache Kafka | Message ingestion |
| Spring Kafka | Kafka integration |
| Spring Data JPA | Database abstraction |
| H2 Database | In-memory SQL database |
| Maven | Build and dependency management |
| REST APIs | External service communication |

---

## Project Architecture

The system processes financial transactions using the following workflow:

1. Transactions are published to a Kafka topic.
2. `KafkaTransactionListener` consumes and deserializes transaction messages.
3. Transactions are validated against user balances and account existence.
4. Valid transactions are persisted in the H2 database.
5. Incentive information is fetched from an external REST API.
6. User balances are updated accordingly.
7. A REST controller exposes balance information through a `/balance` endpoint.

---

## Project Structure

```text
src/main/java/com/jpmc/midascore
│
├── component
│   └── DatabaseConduit.java
│
├── controller
│   └── BalanceController.java
│
├── entity
│   ├── TransactionRecord.java
│   └── UserRecord.java
│
├── foundation
│   ├── Balance.java
│   ├── Incentive.java
│   └── Transaction.java
│
├── repository
│   ├── TransactionRepository.java
│   └── UserRepository.java
│
└── KafkaTransactionListener.java
````

---

## Setup Instructions

### Prerequisites

* Java 17
* Maven 3.9+
* Git

### Clone the Repository

```bash
git clone https://github.com/This-Is-Ram/forage-midas.git
cd forage-midas
```

### Run the Incentive API

Navigate to the `services` folder and start the provided Incentive API:

```bash
cd services
java -jar transaction-incentive-api.jar
```

### Run the Application Tests

```bash
mvn clean install
```

Run individual task tests:

```bash
mvn -Dtest=TaskTwoTests test
mvn -Dtest=TaskThreeTests test
mvn -Dtest=TaskFourTests test
mvn -Dtest=TaskFiveTests test
```

---

## REST API

### Get User Balance

**Endpoint**

```http
GET /balance?userId={id}
```

**Example**

```http
GET http://localhost:33400/balance?userId=1
```

**Sample Response**

```json
{
  "amount": 1326.98
}
```

---

## Learning Outcomes

Through this project, the following backend engineering concepts were implemented and practiced:

* Event-driven architecture using Kafka
* Spring Boot dependency injection and configuration
* Database persistence using JPA and relational modeling
* REST client/server communication
* Microservice integration patterns
* Financial transaction validation workflows
* Automated testing and debugging in enterprise applications

---

## Author

**Durga Ram**

* GitHub: [https://github.com/This-Is-Ram](https://github.com/This-Is-Ram)
* Repository: [https://github.com/This-Is-Ram/forage-midas](https://github.com/This-Is-Ram/forage-midas)

---

## Acknowledgements

This project was completed as part of the.

**JPMorganChase Advanced Software Engineering Virtual Experience Program**
Hosted on Forage

The simulation provided hands-on experience with real-world backend engineering workflows and enterprise software development practices.

```
```
