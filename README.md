# 🎰 Lottery Result Manager

A **Spring Boot REST API** that performs web scraping on Sri Lanka's National Lottery Board (NLB) website using **HtmlUnit** to extract, process, and store lottery results in a **MySQL** database.

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=flat-square&logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-8.x-blue?style=flat-square&logo=mysql)
![HtmlUnit](https://img.shields.io/badge/HtmlUnit-Web%20Scraper-lightgrey?style=flat-square)

---

## 📌 Features

- 🔍 Scrapes live lottery results from [nlb.lk](https://www.nlb.lk)
- 💾 Persists results to a MySQL database
- 🔄 Prevents duplicate entries using draw number + lottery name check
- 🌐 RESTful API with CORS support for frontend integration
- ⚙️ Supports JavaScript-rendered pages via HtmlUnit

---

## 🏗️ Tech Stack

| Layer | Technology |
|---|---|
| Framework | Spring Boot |
| Database | MySQL |
| ORM | Spring Data JPA / Hibernate |
| Web Scraper | HtmlUnit |
| Utilities | Lombok |

---

## 📁 Project Structure

```
src/
├── main/
│   ├── java/edu/test/LotteryResultManager/
│   │   ├── controller/        # REST Controllers
│   │   ├── entity/            # JPA Entities
│   │   ├── repository/        # Spring Data Repositories
│   │   └── service/           # Service Layer (Interface + Impl)
│   └── resources/
│       └── application.yaml   # App Configuration
└── test/
```

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- MySQL 8.x

### Setup

1. **Clone the repository**

```bash
git clone https://github.com/your-username/LotteryResultManager.git
cd LotteryResultManager
```

2. **Configure the database**

Edit `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/lottery_db
    username: your_username
    password: your_password
```

3. **Run the application**

```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

---

## 📡 API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/lottery/all` | Fetch all saved results from DB |
| `GET` | `/api/lottery/scrape/govisetha` | Scrape & save Govisetha results |
| `GET` | `/api/lottery/scrape/jayoda` | Scrape & save Jayoda results |
| `GET` | `/api/lottery/scrape/all` | Scrape & save all lotteries |
| `GET` | `/api/lottery/by-name/{name}` | Filter results by lottery name |

### Example Response

```json
[
  {
    "id": 1,
    "lotteryName": "Govisetha",
    "drawNumber": "1500",
    "drawDate": "2025-01-01",
    "winningNumbers": "12 25 34 47 56",
    "superNumber": "07",
    "scrapedAt": "2025-01-01T10:00:00"
  }
]
```

---

## 🗃️ Database Schema

**Table: `lottery_results`**

| Column | Type | Description |
|---|---|---|
| `id` | INT (PK) | Auto-generated ID |
| `lottery_name` | VARCHAR | e.g., Govisetha, Jayoda |
| `draw_number` | VARCHAR | Unique draw identifier |
| `draw_date` | VARCHAR | Date of the draw |
| `winning_numbers` | VARCHAR(500) | Winning number combination |
| `super_number` | VARCHAR | Bonus/super number |
| `scraped_at` | DATETIME | Timestamp when record was saved |

---

## 🎯 Supported Lotteries

| Lottery | Source |
|---|---|
| Govisetha | [nlb.lk/results/govisetha](https://www.nlb.lk/results/govisetha) |
| Jayoda | [nlb.lk/results/jayoda](https://www.nlb.lk/results/jayoda) |

---

## ⚠️ Notes

- `ddl-auto` is set to `create` — change to `update` before deploying to production to avoid data loss on restart.
- Store database credentials in environment variables rather than hardcoding them in `application.yaml`.

---

## 📄 License

This project is for educational purposes only.
