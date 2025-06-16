# Civio – System Ewidencji Obywateli

## Spis treści

1. [Opis projektu](#opis-projektu)
2. [Kluczowe funkcjonalności](#kluczowe-funkcjonalności)
3. [Stos technologiczny](#stos-technologiczny)
4. [Wymagania wstępne](#wymagania-wstępne)
5. [Instalacja i uruchomienie](#instalacja-i-uruchomienie)
6. [Dokumentacja API](#dokumentacja-api)
7. [Przykłady użycia](#przykłady-użycia)
8. [Testy](#testy)

---

## Opis projektu

**Civio** to pełnoprawna aplikacja webowa umożliwiająca **ewidencję obywateli** oraz zarządzanie podstawowymi danymi osobowymi.  
Projekt składa się z dwóch głównych części:

- **Backend** – aplikacja w Java 17 oparta na frameworku **Spring Boot 3**, udostępniająca REST‑owe API, autoryzację JWT oraz integracje z zewnętrznymi serwisami (pogoda, święta państwowe, geokodowanie).
- **Frontend** – klient SPA napisany w **React 18** + **TypeScript**, budowany przy użyciu **Rsbuild**.

Domyślną bazą danych jest **MySQL 8** (uruchamiana lokalnie lub przez Docker‑compose).

---

## Kluczowe funkcjonalności

| Obszar                 | Opis                                                                           |
| ---------------------- | ------------------------------------------------------------------------------ |
| **Uwierzytelnianie**   | Rejestracja i logowanie z użyciem tokenów **JWT**.                             |
| **Role i uprawnienia** | Obsługa ról `USER` i `ADMIN` (Spring Security).                                |
| **CRUD Obywateli**     | Tworzenie, podgląd, edycja oraz usuwanie wpisów obywateli wraz z adresem.      |
| **Widżet Pogody**      | Pobiera bieżącą pogodę dla miasta zalogowanego użytkownika (API _Open‑Meteo_). |
| **Święta Państwowe**   | Lista najbliższych świąt w Polsce (API _Nager.Date_).                          |
| **Swagger / OpenAPI**  | Automatycznie generowana dokumentacja pod `/swagger-ui.html`.                  |

---

## Stos technologiczny

### Backend

- **Java 17**, **Maven**
- **Spring Boot 3**, **Spring Web**, **Spring Data JPA**, **Spring Security**
- **Hibernate**, **MySQL 8**
- **JWT (io.jsonwebtoken)**
- **springdoc‑openapi** (Swagger UI)

### Frontend

- **React 18**, **TypeScript**
- **Rsbuild** (oparty o Rspack)
- API konsumowane przy użyciu `fetch`.

### DevOps

- **Docker Compose** (usługa MySQL)
- Profil dev + test w `application.properties`

---

## Wymagania wstępne

| Narzędzie                   | Wersja minimalna |
| --------------------------- | ---------------- |
| **Java JDK**                | 17               |
| **Maven**                   | 3.9              |
| **Node.js / npm**           | 20 / 10          |
| **Docker + Docker Compose** | 24 / compose v2  |

---

## Instalacja i uruchomienie

### 1. Klonowanie repozytorium

```bash
git clone https://github.com/twoja‑nazwa‑uzytkownika/civio_project.git
cd civio_project
```

### 2. Uruchomienie bazy danych

```bash
cd docker
docker compose up -d
```

Kontener **citizen_mysql** wystawi port **3306** oraz zainicjalizuje bazę i konto `citizen_user/citizen_pass`.

### 3. Backend

```bash
# z katalogu głównego projektu
mvn spring-boot:run
# lub produkcyjnie
mvn clean package
java -jar target/civio_project-0.0.1-SNAPSHOT.jar
```

Aplikacja zostanie wystawiona na `http://localhost:8080`.

### 4. Frontend

```bash
cd frontend
npm install
# tryb developerski (hot reload na :3000)
npm run dev
# build produkcyjny
npm run build
```

### 5. Zmienne konfiguracyjne

Domyślne parametry (np. połączenie do DB) znajdują się w **`src/main/resources/application.properties`**.  
W środowisku produkcyjnym nadpisz je przez zmienne środowiskowe lub `--Dproperty=value`.

---

## Dokumentacja API

Po uruchomieniu backendu odwiedź:

```
http://localhost:8080/swagger-ui.html
```

Znajdziesz tam pełną specyfikację oraz możliwość testowania endpointów.

---

## Przykłady użycia

### Logowanie

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin"}'
```

### Pobranie listy obywateli (ADMIN)

```bash
curl -H "Authorization: Bearer <TWÓJ_JWT>" \
     http://localhost:8080/api/citizens
```

### Pogoda dla mojego miasta

```bash
curl -H "Authorization: Bearer <TWÓJ_JWT>" \
     http://localhost:8080/api/weather/my-city
```

---

## Testy

```bash
# testy jednostkowe i integracyjne backendu
mvn test
```
