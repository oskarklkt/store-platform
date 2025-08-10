# Store Platform – Microservices Demo

A Spring Boot **microservices** project demonstrating service-to-service communication, centralized configuration, service discovery, API gateway routing, and circuit breaker patterns.

This project is based on a sample **product dataset** ([JCPenney products from Kaggle](https://www.kaggle.com/PromptCloudHQ/all-jc-penny-products)) and simulates an **online store** with product catalog, inventory, and product aggregation services.

---


Supporting infrastructure:

- **Registry Service**: Eureka server for service discovery
- **Config Server**: Centralized configuration using Spring Cloud Config
- **Gateway**: API Gateway for routing client requests to Product Service

---

## Modules

| Module         | Description |
|----------------|-------------|
| `registry`     | Eureka server for service discovery |
| `config-server`| Centralized configuration server (native file system backend) |
| `gateway`      | API Gateway for routing client requests to Product Service |
| `catalog`      | Loads product data from Kaggle dataset into memory and serves it via REST |
| `inventory`    | Generates random availability status for products and serves via REST |
| `product`      | Aggregates Catalog + Inventory data and exposes only available products |
| `config-repo`  | Stores YAML config files for all services (used by Config Server) |

---

## Technologies Used

- **Java 17**
- **Spring Boot 3.3.x**
- **Spring Cloud 2023.x**
    - Spring Cloud Gateway
    - Spring Cloud Config
    - Spring Cloud Netflix Eureka
    - OpenFeign
- **Resilience4j** (Circuit Breaker, Timeouts, Fallbacks)
- **Jackson CSV / OpenCSV** for dataset parsing
- **Maven** (multi-module)

---

## Key Features

- **Service Discovery** via Eureka
- **Centralized Config** via Config Server
- **API Gateway** routing all client calls
- **Inter-service communication** with OpenFeign
- **Circuit Breaker** protection using Resilience4j
- **Synthetic latency** to simulate slow downstreams
- **Filtering** to return only available products

---

## How to Run Locally

### 1. Clone the repo
```bash
git clone https://github.com/<your-username>/store-platform.git
cd store-platform
```

### 2. get the dataset and put it in file
catalog/src/main/resources/products.csv

### 3. run services

## Registry (Eureka)
mvn -pl registry spring-boot:run

## Config Server
mvn -pl config-server spring-boot:run

## Catalog
mvn -pl catalog spring-boot:run

## Inventory
mvn -pl inventory spring-boot:run

## Product
mvn -pl product spring-boot:run

## Gateway
mvn -pl gateway spring-boot:run

---

# Endpoints

## Catalog Service

GET api/v1/products/{sku} - get all products with given sku
returns 200 ok with body

GET api/v1/product/{uniq_id} - get product with given uniq_id
returns 200 ok with body or 404 when not found

## Inventory Service

GET api/v1/availability

request:
Content-Type: application/json

takes list of products' uniqIds and returns their availability, 

### sample request:

[

"b6c0b6bea69c722939585baeac73c13d",

"93e5272c51d8cce02597e3ce67b7ad0a",

"013e320f2f2ec0cf5b3ff5418d688528"

]

### sample response:

HTTP/1.1 200

Content-Type: application/json

Transfer-Encoding: chunked

Date: Sun, 10 Aug 2025 17:31:38 GMT


[

{
"uniqId": "b6c0b6bea69c722939585baeac73c13d",
"available": 6
},

{
"uniqId": "93e5272c51d8cce02597e3ce67b7ad0a",
"available": 21
},

{
"uniqId": "013e320f2f2ec0cf5b3ff5418d688528",
"available": 18
}

]
