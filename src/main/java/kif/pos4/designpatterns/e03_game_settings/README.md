# Game Settings API

## Überblick

In diesem Projekt wird eine kleine **Spring Boot REST API** erstellt.

Die API soll globale Einstellungen für ein Spiel verwalten können.  
Diese Einstellungen gelten für die gesamte Anwendung.

Die Einstellungen sollen mit dem **Singleton Pattern** umgesetzt werden.

Für diese Aufgabe wird **keine Datenbank** benötigt.  
Es gibt daher:

- keine Entity
- kein Repository
- keinen JSON-Import

Die Werte werden direkt im Singleton gespeichert.

---

## Lernziele

Nach dieser Übung solltest du:

- das Singleton Pattern erklären können
- eine Singleton-Klasse in Java erstellen können
- globale Einstellungen zentral verwalten können
- eine einfache REST API für diese Einstellungen erstellen können
- Controller und Service sauber trennen können
- einfache Validierungen im Service oder im Singleton umsetzen können
- einen einfachen Unit Test für Service-Logik schreiben können

---

## Pattern-Fokus: Singleton Pattern

Beim **Singleton Pattern** gibt es genau eine Instanz einer Klasse.

Diese eine Instanz ist global zugänglich.

In diesem Beispiel verwenden wir das Singleton Pattern für globale Spiel-Einstellungen.

Beispiele für globale Einstellungen:

- Lautstärke
- Sprache
- Schwierigkeit

Es soll nicht mehrere unterschiedliche `GameSettings`-Objekte geben.  
Alle Teile der Anwendung sollen dieselben Einstellungen verwenden.

---

## Benötigte Klassen

Für die Umsetzung werden mindestens folgende Klassen benötigt:

- `GameSettings`
- `GameSettingsService`
- `GameSettingsController`

Optional:

- `SettingsDto`

---

## Empfohlene Projektstruktur

Eine mögliche Package-Struktur:

```text
src/main/java/kif/pos4/designpatterns/e03_game_settings
├── controller
│   └── GameSettingsController.java
├── dto
│   └── SettingsDto.java
├── model
│   └── GameSettings.java
└── service
    └── GameSettingsService.java
```

Die genaue Package-Struktur kann angepasst werden.  
Wichtig ist, dass Controller, Service und Model sinnvoll getrennt sind.

---

## Aufgaben

### 1. Singleton-Klasse `GameSettings`

Implementiere die Klasse `GameSettings` als Singleton.

Die Klasse soll:

- genau eine Instanz besitzen
- einen privaten Konstruktor besitzen
- eine öffentliche Methode `getInstance()` besitzen
- globale Einstellungen speichern

Die Klasse soll mindestens folgende Eigenschaften besitzen:

- `volume`
- `language`
- `difficulty`

Beispielwerte:

```text
volume: 50
language: EN
difficulty: NORMAL
```

---

### 2. Getter und Setter

Implementiere passende Getter und Setter.

Dabei sollen einfache Validierungen eingebaut werden.

#### `volume`

`volume` soll nur Werte von `0` bis `100` erlauben.

Beispiele:

```text
80 bleibt 80
-10 wird zu 0
150 wird zu 100
```

#### `language`

`language` soll nicht leer sein.

Beispiele:

```text
DE ist gültig
EN ist gültig
"" soll ignoriert werden
```

#### `difficulty`

`difficulty` soll nicht leer sein.

Beispiele:

```text
EASY ist gültig
NORMAL ist gültig
HARD ist gültig
"" soll ignoriert werden
```

---

### 3. Service

Implementiere ein Service für `GameSettings`.

Der Service soll die Geschäftslogik enthalten.

Wichtig:

- Der Controller soll nicht direkt mit `GameSettings.getInstance()` arbeiten.
- Der Controller verwendet den Service.
- Der Service verwendet das Singleton.

Der Service soll Methoden anbieten, um:

- die aktuellen Settings auszulesen
- die Settings zu ändern
- optional die Settings zurückzusetzen

---

### 4. REST Controller

Implementiere einen REST Controller mit den folgenden Endpoints.

---

## Endpoints

### `GET /settings`

Liefert die aktuellen globalen Einstellungen.

Beispiel-Request:

```http
GET /settings
```

Beispiel-Response:

```json
{
  "volume": 50,
  "language": "EN",
  "difficulty": "NORMAL"
}
```

---

### `PUT /settings`

Ändert die globalen Einstellungen.

Beispiel-Request:

```http
PUT /settings
```

Beispiel-Body:

```json
{
  "volume": 80,
  "language": "DE",
  "difficulty": "HARD"
}
```

Beispiel-Response:

```json
{
  "volume": 80,
  "language": "DE",
  "difficulty": "HARD"
}
```

---

## Optionaler Endpoint

### `PUT /settings/reset`

Setzt die globalen Einstellungen auf die Standardwerte zurück.

Beispiel-Request:

```http
PUT /settings/reset
```

Beispiel-Response:

```json
{
  "volume": 50,
  "language": "EN",
  "difficulty": "NORMAL"
}
```

---

## Erwartetes Verhalten der Anwendung

Nach dem Start der Anwendung sollen die Standardwerte im Singleton vorhanden sein.

Beispiel:

```json
{
  "volume": 50,
  "language": "EN",
  "difficulty": "NORMAL"
}
```

Wenn über `PUT /settings` neue Werte gesetzt werden, sollen diese Werte danach bei `GET /settings` sichtbar sein.

Da es sich um ein Singleton handelt, gibt es nur eine gemeinsame Instanz der Settings.

---

## Beispielablauf

### 1. Settings abfragen

```http
GET /settings
```

Response:

```json
{
  "volume": 50,
  "language": "EN",
  "difficulty": "NORMAL"
}
```

---

### 2. Settings ändern

```http
PUT /settings
```

Body:

```json
{
  "volume": 90,
  "language": "DE",
  "difficulty": "HARD"
}
```

Response:

```json
{
  "volume": 90,
  "language": "DE",
  "difficulty": "HARD"
}
```

---

### 3. Settings erneut abfragen

```http
GET /settings
```

Response:

```json
{
  "volume": 90,
  "language": "DE",
  "difficulty": "HARD"
}
```

---

## Unit Test

Schreibe einen einfachen Unit Test für den `GameSettingsService`.

Der Test soll überprüfen:

- ob die Standardwerte korrekt sind
- ob `volume` geändert werden kann
- ob `volume` bei ungültigen Werten korrekt begrenzt wird

Beispiele:

```text
-10 wird zu 0
150 wird zu 100
```

Optional kann auch getestet werden:

- ob `language` geändert werden kann
- ob `difficulty` geändert werden kann
- ob `resetSettings()` wieder die Standardwerte setzt

---

## Zusatzaufgaben

### Zusatz 1: Reset-Methode

Ergänze eine Methode `resetSettings()`.

Danach sollen wieder die Standardwerte gesetzt sein:

```text
volume: 50
language: EN
difficulty: NORMAL
```

---

### Zusatz 2: Erlaubte Schwierigkeitsgrade

Ergänze eine Validierung für erlaubte Schwierigkeitsgrade.

Erlaubte Werte:

- `EASY`
- `NORMAL`
- `HARD`

Ungültige Werte sollen ignoriert werden oder einen passenden Fehler erzeugen.

---

## Reflexionsfragen

### Frage 1

Warum ist `GameSettings` ein sinnvolles Beispiel für ein Singleton?

### Frage 2

Warum ist der Konstruktor bei einem Singleton privat?

### Frage 3

Wozu dient `getInstance()`?

---

### Frage 4

Warum soll der Controller nicht direkt `GameSettings.getInstance()` verwenden?

Mögliche Antwort:

```text
Weil die Geschäftslogik im Service liegen soll.
Der Controller soll nur HTTP-Anfragen entgegennehmen und Antworten zurückgeben.
```

---

## Abgabe / Kontrolle

Am Ende sollen folgende Punkte funktionieren:

- Die Anwendung startet ohne Fehler.
- `GET /settings` liefert die aktuellen Settings.
- `PUT /settings` ändert die Settings.
- Die Settings werden im Singleton gespeichert.
- Es gibt keine Datenbank für diese Aufgabe.
- Es gibt kein Repository für diese Aufgabe.
- Der Controller greift nicht direkt auf das Singleton zu.
- Die Geschäftslogik befindet sich im Service.
- Das Singleton Pattern ist im Code erkennbar.
- Ein einfacher Unit Test für den Service ist vorhanden.
