# 🎯 Sri Lanka Lottery Result Scraper

Automated web scraper built with Spring Boot and Jsoup that extracts Sri Lanka lottery results from official NLB website and stores them in a MySQL database.

---

## 🛠️ Tech Stack

| Technology | Version |
|------------|---------|
| Java | 17 |
| Spring Boot | 3.2.0 |
| Spring Data JPA | 3.2.0 |
| Hibernate | 6.4.0 |
| MySQL | 8.0 |
| Jsoup | 1.17.2 |
| Lombok | Latest |

---

## 📁 Project Structure

```
src/
├── main/
│   ├── java/edu/test/LotteryResultManager/
│   │   ├── LotteryResultManagerApplication.java
│   │   ├── controller/
│   │   │   └── LotteryController.java
│   │   ├── entity/
│   │   │   └── LotteryResult.java
│   │   ├── repository/
│   │   │   └── LotteryResultRepository.java
│   │   └── service/
│   │       ├── LotteryScraperService.java
│   │       └── Impl/
│   │           └── LotteryScraperServiceImpl.java
│   └── resources/
│       └── application.yaml
```

---

## ⚙️ Configuration

`src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://YOUR_DB_IP:3306/a1
    username: YOUR_USERNAME
    password: YOUR_PASSWORD
```

---

## 🚀 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/lottery/all` | Get all results from DB |
| GET | `/api/lottery/scrape/govisetha` | Scrape & save Govisetha |
| GET | `/api/lottery/scrape/jayoda` | Scrape & save Jayoda |
| GET | `/api/lottery/scrape/all` | Scrape & save all lotteries |
| GET | `/api/lottery/by-name/{name}` | Get results by lottery name |

---

## 🗄️ Database Table

Table `lottery_results` auto-created by Hibernate:

| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary Key |
| lottery_name | VARCHAR | Lottery name |
| draw_number | VARCHAR | Draw number |
| draw_date | VARCHAR | Draw date |
| winning_numbers | VARCHAR | Winning numbers |
| super_number | VARCHAR | Super number |
| scraped_at | DATETIME | Scraped timestamp |

---

## ▶️ Run the Project

```bash
./mvnw spring-boot:run
```

---

## 🌿 Branch Strategy

```
main          ← Production
└── develop   ← Integration
    ├── feature/lottery-entity
    ├── feature/repository-layer
    ├── feature/service-layer
    ├── feature/rest-api
    └── feature/app-config
```

---

## 👨‍💻 Author

**Navod Rajapaksha**
GitHub: [@NavodRajapaksha](https://github.com/NavodRajapaksha)
