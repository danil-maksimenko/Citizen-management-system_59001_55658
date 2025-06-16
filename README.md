# Civio - System Ewidencji Obywateli

## Opis Projektu

Civio to w pełni funkcjonalna aplikacja internetowa, składająca się z REST API oraz interfejsu użytkownika (frontend), stworzona w celu demonstracji zasad budowy nowoczesnych serwisów internetowych. System umożliwia efektywne zarządzanie danymi obywateli, oferując niezawodny mechanizm uwierzytelniania i autoryzacji oparty na rolach.

Aplikacja została zaimplementowana z użyciem **Java** z **Spring Boot** na backendzie oraz **React** z **TypeScript** na froncie. Dodatkowo, integruje dwa zewnętrzne API, wzbogacając prezentowane dane i podnosząc wartość użytkową systemu.

## Funkcjonalność Aplikacji

### Uwierzytelnianie i Rejestracja
- Użytkownicy mogą tworzyć konta za pomocą intuicyjnego formularza rejestracji.
- Podczas rejestracji automatycznie generowany jest powiązany rekord obywatela.
- Logowanie odbywa się za pomocą nazwy użytkownika i hasła.
- Po zalogowaniu użytkownik otrzymuje token **JWT**, używany do autoryzacji kolejnych żądań.

### Model Ról (Autoryzacja)
System obsługuje dwie role z różnymi poziomami dostępu:
- **ADMIN**:
   - Dostęp do pełnej listy wszystkich obywateli.
   - Możliwość wykonywania operacji **CRUD** (przeglądanie, modyfikacja, usuwanie danych dowolnego obywatela).
- **USER**:
-  - Może registerować się i logować.
   - Widzi wyłącznie swoje dane na stronie głównej.
   - Brak dostępu do przycisków dodawania, modyfikacji i usuwania.
   - Może przeglądać szczegóły swojego adresu.

### Integracja z Zewnętrznymi API
- **Pogoda**: Widżet na stronie głównej prezentujący aktualną pogodę dla lokalizacji obywatela (wykorzystanie API **OpenWeatherMap** lub podobnego).
- **Święta państwowe:** Na stronie głównej wyświetlany jest widżet z listą najbliższych świąt państwowych w Polsce.

## Wykorzystane Technologie

### Backend
- **Java 17**
- **Spring Boot 3**
- **Spring Security**
- **Spring Data JPA / Hibernate**
- **JWT (JSON Web Tokens)**
- **MySQL**
- **Maven**
- **Lombok**
- **Springdoc OpenAPI (Swagger)**

### Frontend
- **React**
- **TypeScript**
- **CSS**
- **Fetch API**

### Testowanie
- **JUnit 5**
- **Mockito**

### Konfiguracja Backendu
1.  Utwórz bazę danych MySQL o nazwie `citizen_db`.
2.  Uruchom skrypt `init.sql`, aby utworzyć tabele i dane początkowe (w tym użytkownika `admin`).
3.  Zaktualizuj dane do połączenia z bazą danych w pliku `src/main/resources/application.properties` (nazwa użytkownika, hasło).
4.  Uruchom aplikację za pomocą swojego IDE lub komendy w terminalu w głównym folderze projektu:
    ```bash
    mvn spring-boot:run
    ```
5.  Dokumentacja Swagger dostępne pod adresem pod `http://localhost:8080/swagger-ui.html`.

### Konfiguracja Frontendu
1.  Przejdź do folderu z frontendem (np. `/frontend`).
2.  Zainstaluj zależności:
    ```bash
    npm install
    ```
3.  Uruchom aplikację:
    ```bash
    npm start
    ```
4.  Interfejs użytkownika będzie dostępny pod adresem `http://localhost:3000`.