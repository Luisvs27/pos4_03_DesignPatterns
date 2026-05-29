# Battle Arena API

## Überblick

In diesem Projekt wird eine einfache **Spring Boot REST API** erstellt.

Die API soll fiktive Characters verwalten können.  
Jeder Character besitzt einen bestimmten Angriffstyp und kann je nach Angriffstyp unterschiedlich angreifen.

Die Angriffslogik soll mit dem **Strategy Pattern** umgesetzt werden.

In diesem Beispiel soll eine eigene **Context-Klasse** verwendet werden.

---

## Lernziele

Nach dieser Übung solltest du:

- eine einfache Spring Boot REST API erstellen können
- eine Entity mit JPA verwenden können
- ein Repository mit `JpaRepository` erstellen können
- Geschäftslogik in einem Service kapseln können
- Controller und Service sauber trennen können
- JSON-Daten beim Start der Anwendung importieren können
- das Strategy Pattern in einer REST API anwenden können
- einfache Unit Tests für Service-Logik schreiben können

---

## Pattern-Fokus: Strategy Pattern

Beim **Strategy Pattern** wird ein Verhalten in eigene Klassen ausgelagert.

In diesem Beispiel ist das variable Verhalten der Angriff eines Characters.

Ein Character kann zum Beispiel mit folgenden Angriffstypen angreifen:

- `SWORD`
- `BOW`
- `SPECIAL`

Die konkrete Angriffslogik soll nicht direkt im Character oder im Controller stehen, sondern in eigenen Strategy-Klassen.

---

## Benötigte Klassen und Interfaces

Für die Umsetzung werden mindestens folgende Interfaces und Klassen benötigt:

- `IAttackStrategy`
    - `SwordAttackStrategy`
    - `BowAttackStrategy`
    - `SpecialAttackStrategy`
- `AttackContext`
- `GameCharacter`
- `GameCharacterRepository`
- `GameCharacterService`
- `GameCharacterController`
- `DbInitService`

---

## Mögliche Projektstruktur

Eine mögliche Package-Struktur:

```text
src/main/java/kif/pos4/designpatterns/e01_battle_arena
├── controller
│   └── GameCharacterController.java
├── dto
│   ├── AttackResultDto.java
│   └── AttackTypeDto.java
├── model
│   └── GameCharacter.java
├── repository
│   └── GameCharacterRepository.java
├── service
│   ├── DbInitService.java
│   └── GameCharacterService.java
└── strategy
    ├── IAttackStrategy.java
    ├── SwordAttackStrategy.java
    ├── BowAttackStrategy.java
    ├── SpecialAttackStrategy.java
    └── AttackContext.java
```

Die genaue Package-Struktur kann angepasst werden.  
Wichtig ist, dass Controller, Service, Repository, Model und Strategy-Klassen sinnvoll getrennt sind.

---

## Aufgaben

### 1. Entity `GameCharacter`

Implementiere die Klasse `GameCharacter` als JPA Entity.

Die Klasse soll mindestens folgende Eigenschaften besitzen:

- `id`
- `name`
- `team`
- `powerLevel`
- `attackType`

Hinweise:

- `id` soll automatisch von der Datenbank generiert werden.
- `attackType` wird später verwendet, um die passende Strategy auszuwählen.
- Beispiele für `attackType` sind:
    - `SWORD`
    - `BOW`
    - `SPECIAL`

---

### 2. Strategy Interface und konkrete Strategien

Implementiere das Interface `IAttackStrategy`.

Das Interface soll eine Methode besitzen:

```java
String attack(String characterName);
```

Implementiere danach folgende konkrete Strategien:

- `SwordAttackStrategy`
- `BowAttackStrategy`
- `SpecialAttackStrategy`

Jede Strategy soll einen anderen Angriffstext zurückgeben.

Beispiele:

```text
Roronoa Zoro attacks with a sword!
Usopp shoots an arrow!
Naruto Uzumaki uses a powerful special attack!
```

---

### 3. Context-Klasse `AttackContext`

Implementiere die Klasse `AttackContext`.

Die Klasse soll:

- eine Strategy speichern können
- eine Methode `setStrategy(...)` besitzen
- eine Methode `executeAttack(String characterName)` besitzen

Wenn keine Strategy gesetzt ist, soll eine sinnvolle Meldung zurückgegeben werden.

Beispiel:

```text
Naruto Uzumaki has no attack strategy!
```

---

### 4. Repository

Implementiere ein Repository für `GameCharacter`.

Das Repository soll von `JpaRepository` erben.

Zusätzlich soll eine Methode zum Filtern nach Team ergänzt werden.

Beispiel:

```java
List<GameCharacter> findByTeamIgnoreCase(String team);
```

---

### 5. Service

Implementiere ein Service für `GameCharacter`.

Der Service soll die Geschäftslogik enthalten.

Wichtig:

- Der Controller darf nicht direkt auf das Repository zugreifen.
- Der Service verwendet das Repository.
- Im Service soll je nach `attackType` die passende Strategy gesetzt werden.
- Der `AttackContext` soll verwendet werden, um den Angriff auszuführen.

---

### 6. REST Controller

Implementiere einen REST Controller mit den folgenden Endpoints.

---

## Endpoints

### `GET /characters`

Liefert alle Characters.

Beispiel-Request:

```http
GET /characters
```

Beispiel-Response:

```json
[
  {
    "id": 1,
    "name": "Naruto Uzumaki",
    "team": "Ninjas",
    "powerLevel": 9000,
    "attackType": "SPECIAL"
  },
  {
    "id": 2,
    "name": "Roronoa Zoro",
    "team": "Strawhats",
    "powerLevel": 8500,
    "attackType": "SWORD"
  }
]
```

Wenn keine Characters vorhanden sind, soll ein passender Statuscode zurückgegeben werden.

---

### `GET /characters?team=Strawhats`

Liefert alle Characters eines bestimmten Teams.

Beispiel-Request:

```http
GET /characters?team=Strawhats
```

Beispiel-Response:

```json
[
  {
    "id": 2,
    "name": "Roronoa Zoro",
    "team": "Strawhats",
    "powerLevel": 8500,
    "attackType": "SWORD"
  },
  {
    "id": 3,
    "name": "Usopp",
    "team": "Strawhats",
    "powerLevel": 4000,
    "attackType": "BOW"
  }
]
```

Wenn keine passenden Characters gefunden werden, soll ein passender Statuscode zurückgegeben werden.

---

### `POST /characters`

Erstellt einen neuen Character.

Beispiel-Request:

```http
POST /characters
```

Beispiel-Body:

```json
{
  "name": "Sanji",
  "team": "Strawhats",
  "powerLevel": 7800,
  "attackType": "SPECIAL"
}
```

Bei Erfolg soll der neu gespeicherte Character zurückgegeben werden.

---

### `PUT /characters/{id}/attack-type`

Ändert den Angriffstyp eines Characters.

Beispiel-Request:

```http
PUT /characters/2/attack-type
```

Beispiel-Body:

```json
{
  "attackType": "SPECIAL"
}
```

Bei Erfolg soll der aktualisierte Character zurückgegeben werden.

Wenn kein Character mit der angegebenen `id` existiert, soll ein passender Statuscode zurückgegeben werden.

---

### `GET /characters/{id}/attack`

Führt den Angriff des Characters aus.

Dabei soll die passende Strategy verwendet werden.

Beispiel-Request:

```http
GET /characters/2/attack
```

Beispiel-Response:

```json
{
  "characterName": "Roronoa Zoro",
  "attackType": "SWORD",
  "result": "Roronoa Zoro attacks with a sword!"
}
```

Wenn kein Character mit der angegebenen `id` existiert, soll ein passender Statuscode zurückgegeben werden.

---

## JSON-Beispieldaten

Beim Start der Anwendung sollen folgende Daten aus einer JSON-Datei importiert werden.

Lege dafür im Ordner `src/main/resources` eine Datei mit dem Namen `characters.json` an.

### `src/main/resources/characters.json`

```json
[
  {
    "name": "Naruto Uzumaki",
    "team": "Ninjas",
    "powerLevel": 9000,
    "attackType": "SPECIAL"
  },
  {
    "name": "Roronoa Zoro",
    "team": "Strawhats",
    "powerLevel": 8500,
    "attackType": "SWORD"
  },
  {
    "name": "Usopp",
    "team": "Strawhats",
    "powerLevel": 4000,
    "attackType": "BOW"
  }
]
```

Hinweis: Achte darauf, dass bei `"Strawhats"` keine zusätzlichen Leerzeichen im JSON stehen.

---

## Datenbank-Initialisierung

Erstelle eine Klasse `DbInitService`.

Diese Klasse soll beim Start der Anwendung die Datei `characters.json` einlesen und die Characters in der Datenbank speichern.

Verwende dafür `@PostConstruct`.

Beispielhafte Struktur:

```java
@PostConstruct
public void initDB() {
    // JSON-Datei einlesen
    // Java-Objekte erzeugen
    // Objekte mit dem Repository speichern
}
```

---

## Erwartetes Verhalten der Anwendung

Nach dem Start der Anwendung sollen die Characters aus `characters.json` automatisch in der Datenbank vorhanden sein.

Danach sollen die Endpoints getestet werden können, zum Beispiel mit:

- Swagger UI
- Postman
- IntelliJ HTTP Client
- Browser für einfache GET-Requests

---

## Zusatzaufgaben

### Zusatz 1: Weitere Strategy

Ergänze eine weitere Strategy.

Beispiel:

- `MagicAttackStrategy`

Dazu muss auch ein neuer `attackType` unterstützt werden.

Beispiel:

```text
MAGIC
```

---

### Zusatz 2: Validierung der Angriffstypen

Ergänze eine Validierung, sodass nur bekannte Angriffstypen erlaubt sind.

Erlaubte Werte könnten zum Beispiel sein:

- `SWORD`
- `BOW`
- `SPECIAL`
- `MAGIC`

Bei einem ungültigen Angriffstyp soll ein passender Fehler zurückgegeben werden.

---

### Zusatz 3: Swagger-Annotationen

Ergänze sinnvolle Swagger-Annotationen für die Endpoints.

Beispiele:

- `@Operation`
- `@ApiResponse`
- `@Parameter`

---

### Zusatz 4: Unit Test

Schreibe einen einfachen Unit Test für den `GameCharacterService`.

Der Test soll überprüfen, ob bei folgendem Character der richtige Angriffstext zurückgegeben wird:

```text
name: Roronoa Zoro
team: Strawhats
powerLevel: 8500
attackType: SWORD
```

Erwartetes Ergebnis:

```text
Roronoa Zoro attacks with a sword!
```

---

## Reflexionsfragen

### Frage 1

Welche Klassen gehören zum Strategy Pattern?

### Frage 2

Welche Klasse ist der Context?


### Frage 3

Welche Klasse entscheidet, welche Strategy verwendet wird?

### Frage 4

Welche Klasse führt die Strategy aus?

---

## Abgabe / Kontrolle

Am Ende sollen folgende Punkte funktionieren:

- Die Anwendung startet ohne Fehler.
- Die JSON-Daten werden beim Start importiert.
- `GET /characters` liefert alle Characters.
- `GET /characters?team=Strawhats` filtert nach Team.
- `POST /characters` erstellt einen neuen Character.
- `PUT /characters/{id}/attack-type` ändert den Angriffstyp.
- `GET /characters/{id}/attack` führt den Angriff mit Strategy Pattern aus.
- Die Geschäftslogik befindet sich im Service.
- Der Controller greift nicht direkt auf das Repository zu.
- Das Strategy Pattern ist im Code erkennbar.
