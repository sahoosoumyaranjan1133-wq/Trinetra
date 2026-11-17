# 🔱 Trinetra – The Three-Eyed One

### See Beyond. Search Smarter.

Trinetra is an independent search engine project designed to explore how modern search technology works. It combines Java, Spring Boot, Apache Lucene and Jsoup to build a foundation for indexing and searching web content.

## ✨ Current Features

- Modern, responsive search homepage
- Light and dark themes
- REST API for search requests
- Apache Lucene-powered keyword search
- Document indexing and retrieval
- Sample documents for testing
- Basic same-host web crawler prototype

## 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| Java 21 | Backend programming |
| Spring Boot | Application framework |
| Spring Web MVC | REST API |
| Apache Lucene | Search indexing and retrieval |
| Jsoup | HTML parsing |
| Maven | Build and dependency management |
| HTML, CSS, JavaScript | Frontend |

## 🏗️ Project Structure

```text
src/
└── main/
    ├── java/com/novasearch/
    │   ├── controller/
    │   ├── crawler/
    │   ├── service/
    │   └── NovasearchApplication.java
    └── resources/
        ├── application.properties
        └── static/index.html

data/                  # Local Lucene index (not committed)
pom.xml
mvnw
README.md
```

## 🚀 Run Locally

### Requirements

- Java 21 or later
- Git
- Internet connection for the first Maven dependency download

### Clone the repository

```bash
git clone https://github.com/sahoosoumyaranjan1133-wq/Trinetra.git
cd Trinetra
```

### Start the application

On Linux / WSL:

```bash
chmod +x mvnw
./mvnw spring-boot:run
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Open:

http://localhost:8080

## 🔎 Search API

Endpoint:

```http
GET /api/search?q=Java
```

Example:

```bash
curl "http://localhost:8080/api/search?q=Java"
```

The API returns the query, matching results, result count and status.

## ⚠️ Current Project Status

Trinetra is an early-stage development project.

- Search currently uses documents stored in a local Lucene index.
- The sample documents are for testing.
- The crawler is a basic prototype, not production-ready.
- Full-web search, AI-powered search and advanced ranking are future goals.

Do not use the current crawler on arbitrary websites until robots.txt handling, redirect validation, private-network protections and crawl limits are implemented.

## 🔮 Future Development

- Safer web crawling and automatic link discovery
- Larger document indexing pipeline
- Search result snippets and relevance ranking
- Improved search suggestions
- AI-powered search
- Performance and scalability improvements

## 📜 License

License to be decided.

---

**Trinetra – See Beyond. Search Smarter.**
