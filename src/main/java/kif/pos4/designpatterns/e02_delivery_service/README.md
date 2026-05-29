# Lieferdienst API

## Überblick

In diesem Projekt wird eine einfache **Spring Boot REST API** erstellt.

Die API soll Bestellungen eines Lieferdienstes verwalten können.  
Jede Bestellung besitzt eine bestimmte Zustellart. Je nach Zustellart soll eine andere Lieferinformation erzeugt werden.

Die Lieferlogik soll mit dem **Strategy Pattern** umgesetzt werden.

In diesem Beispiel kann entweder eine eigene **Context-Klasse** verwendet werden oder der Service übernimmt die Rolle des Context.

---

## Lernziele

Nach dieser Übung solltest du:

- eine einfache Spring Boot REST API erstellen können
- eine Entity mit JPA verwenden können
- ein Repository mit `JpaRepository` erstellen können
- Geschäftslogik in einem Service kapseln können
- Controller und Service sauber trennen können
- JSON-Daten beim Start der Anwendung importieren können
- das Strategy Pattern in einem alltäglichen Szenario anwenden können
- den Unterschied zwischen eigener Context-Klasse und Service als Context erklären können
- einfache Unit Tests für Service-Logik schreiben können

---

## Pattern-Fokus: Strategy Pattern

Beim **Strategy Pattern** wird ein Verhalten in eigene Klassen ausgelagert.

In diesem Beispiel ist das variable Verhalten die Lieferinformation einer Bestellung.

Eine Bestellung kann zum Beispiel mit folgenden Zustellarten geliefert werden:

- `BIKE`
- `CAR`
- `PICKUP`

Die konkrete Lieferlogik soll nicht direkt in der Entity oder im Controller stehen, sondern in eigenen Strategy-Klassen.

---

## Benötigte Klassen und Interfaces

Für die Umsetzung werden mindestens folgende Interfaces und Klassen benötigt:

- `IDeliveryStrategy`
    - `BikeDeliveryStrategy`
    - `CarDeliveryStrategy`
    - `PickupDeliveryStrategy`
- optional: `DeliveryContext`
- `DeliveryOrder`
- `DeliveryOrderRepository`
- `DeliveryOrderService`
- `DeliveryOrderController`
- `DbInitService`

---

## Projektstruktur

Überlege dir eine Projektstruktur, die anhand der Aufgabenstellung passend sein könnte.

Wichtig ist, dass Controller, Service, Repository, Model und Strategy-Klassen sinnvoll getrennt sind.

Wenn der Service die Rolle des Context übernimmt, ist die Klasse `DeliveryContext` optional.

---

## Aufgaben

### 1. Entity `DeliveryOrder`

Implementiere die Klasse `DeliveryOrder` als JPA Entity.

Die Klasse soll mindestens folgende Eigenschaften besitzen:

- `id`
- `customerName`
- `address`
- `orderValue`
- `deliveryType`

Hinweise:

- `id` soll automatisch von der Datenbank generiert werden.
- `deliveryType` wird später verwendet, um die passende Strategy auszuwählen.
- Beispiele für `deliveryType` sind:
    - `BIKE`
    - `CAR`
    - `PICKUP`

---

### 2. Strategy Interface und konkrete Strategien

Implementiere das Interface `IDeliveryStrategy`.

Das Interface soll eine Methode besitzen, zum Beispiel:

```java
String getDeliveryInfo(DeliveryOrder order);
```

Alternativ kann die Methode auch einfacher gehalten werden:

```java
String getDeliveryInfo(String customerName);
```

Implementiere danach folgende konkrete Strategien:

- `BikeDeliveryStrategy`
- `CarDeliveryStrategy`
- `PickupDeliveryStrategy`

Jede Strategy soll eine andere Lieferinformation zurückgeben.

Beispiele:

```text
Delivery by bike: fast for short distances.
Delivery by car: suitable for larger orders.
Pickup: customer collects the order at the pickup station.
```

---

### 3. Context-Entscheidung

Entscheide, wo der Context liegen soll.

Es gibt zwei mögliche Varianten.

---

#### Variante A: Eigene Context-Klasse

Implementiere eine Klasse `DeliveryContext`.

Die Klasse soll:

- eine Strategy speichern können
- eine Methode `setStrategy(...)` besitzen
- eine Methode zum Ausführen der Strategy besitzen

Beispielhafte Idee:

```java
public class DeliveryContext {

    private IDeliveryStrategy strategy;

    public void setStrategy(IDeliveryStrategy strategy) {
        this.strategy = strategy;
    }

    public String executeDeliveryInfo(DeliveryOrder order) {
        if (strategy == null) {
            return "No delivery strategy selected.";
        }

        return strategy.getDeliveryInfo(order);
    }
}
```

---

#### Variante B: Service als Context

Alternativ kann der `DeliveryOrderService` die Rolle des Context übernehmen.

Dann wird keine eigene `DeliveryContext`-Klasse benötigt.

Der Service entscheidet dann:

- Welche Strategy wird verwendet?
- Welche Methode der Strategy wird ausgeführt?
- Welche Antwort wird zurückgegeben?

Diese Variante ist bei kleineren Spring Boot APIs oft ausreichend.

---

### 4. Repository

Implementiere ein Repository für `DeliveryOrder`.

Das Repository soll von `JpaRepository` erben.

Zusätzlich soll eine Methode zum Filtern nach Zustellart ergänzt werden.

Beispiel:

```java
List<DeliveryOrder> findByDeliveryTypeIgnoreCase(String deliveryType);
```

---

### 5. Service

Implementiere ein Service für `DeliveryOrder`.

Der Service soll die Geschäftslogik enthalten.

Wichtig:

- Der Controller darf nicht direkt auf das Repository zugreifen.
- Der Service verwendet das Repository.
- Im Service soll je nach `deliveryType` die passende Strategy verwendet werden.
- Wenn du Variante A wählst, soll der Service den `DeliveryContext` verwenden.
- Wenn du Variante B wählst, übernimmt der Service selbst die Rolle des Context.

---

### 6. REST Controller

Implementiere einen REST Controller mit den folgenden Endpoints.

---

## Endpoints

### `GET /orders`

Liefert alle Bestellungen.

Beispiel-Request:

```http
GET /orders
```

Beispiel-Response:

```json
[
  {
    "id": 1,
    "customerName": "Alex Kim",
    "address": "Main Street 12",
    "orderValue": 24.90,
    "deliveryType": "BIKE"
  },
  {
    "id": 2,
    "customerName": "Sam Taylor",
    "address": "Park Avenue 8",
    "orderValue": 59.90,
    "deliveryType": "CAR"
  }
]
```

Wenn keine Bestellungen vorhanden sind, soll ein passender Statuscode zurückgegeben werden.

---

### `GET /orders?deliveryType=BIKE`

Liefert alle Bestellungen mit einer bestimmten Zustellart.

Beispiel-Request:

```http
GET /orders?deliveryType=BIKE
```

Beispiel-Response:

```json
[
  {
    "id": 1,
    "customerName": "Alex Kim",
    "address": "Main Street 12",
    "orderValue": 24.90,
    "deliveryType": "BIKE"
  }
]
```

Wenn keine passenden Bestellungen gefunden werden, soll ein passender Statuscode zurückgegeben werden.

---

### `POST /orders`

Erstellt eine neue Bestellung.

Beispiel-Request:

```http
POST /orders
```

Beispiel-Body:

```json
{
  "customerName": "Jamie Morgan",
  "address": "Lake Road 5",
  "orderValue": 32.50,
  "deliveryType": "CAR"
}
```

Bei Erfolg soll die neu gespeicherte Bestellung zurückgegeben werden.

---

### `PUT /orders/{id}/delivery-type`

Ändert die Zustellart einer Bestellung.

Beispiel-Request:

```http
PUT /orders/1/delivery-type
```

Beispiel-Body:

```json
{
  "deliveryType": "PICKUP"
}
```

Bei Erfolg soll die aktualisierte Bestellung zurückgegeben werden.

Wenn keine Bestellung mit der angegebenen `id` existiert, soll ein passender Statuscode zurückgegeben werden.

---

### `GET /orders/{id}/delivery-info`

Gibt die Lieferinformation für eine Bestellung zurück.

Dabei soll die passende Strategy verwendet werden.

Beispiel-Request:

```http
GET /orders/1/delivery-info
```

Beispiel-Response:

```json
{
  "customerName": "Alex Kim",
  "deliveryType": "BIKE",
  "result": "Delivery by bike: fast for short distances."
}
```

Wenn keine Bestellung mit der angegebenen `id` existiert, soll ein passender Statuscode zurückgegeben werden.

---

## JSON-Beispieldaten

Beim Start der Anwendung sollen folgende Daten aus einer JSON-Datei importiert werden.

Lege dafür im Ordner `src/main/resources` eine Datei mit dem Namen `orders.json` an.

### `src/main/resources/orders.json`

```json
[
  {
    "customerName": "Alex Kim",
    "address": "Main Street 12",
    "orderValue": 24.90,
    "deliveryType": "BIKE"
  },
  {
    "customerName": "Sam Taylor",
    "address": "Park Avenue 8",
    "orderValue": 59.90,
    "deliveryType": "CAR"
  },
  {
    "customerName": "Robin Lee",
    "address": "Pickup Station 3",
    "orderValue": 14.50,
    "deliveryType": "PICKUP"
  }
]
```

---

## Datenbank-Initialisierung

Erstelle eine Klasse `DbInitService`.

Diese Klasse soll beim Start der Anwendung die Datei `orders.json` einlesen und die Bestellungen in der Datenbank speichern.

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

Nach dem Start der Anwendung sollen die Bestellungen aus `orders.json` automatisch in der Datenbank vorhanden sein.

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

- `ExpressDeliveryStrategy`

Dazu muss auch ein neuer `deliveryType` unterstützt werden.

Beispiel:

```text
EXPRESS
```

---

### Zusatz 2: Validierung der Zustellarten

Ergänze eine Validierung, sodass nur bekannte Zustellarten erlaubt sind.

Erlaubte Werte könnten zum Beispiel sein:

- `BIKE`
- `CAR`
- `PICKUP`
- `EXPRESS`

Bei einer ungültigen Zustellart soll ein passender Fehler zurückgegeben werden.

---

### Zusatz 3: Unit Test

Schreibe einen einfachen Unit Test für den `DeliveryOrderService`.

Der Test soll überprüfen, ob bei folgender Bestellung die richtige Lieferinformation zurückgegeben wird:

```text
customerName: Alex Kim
address: Main Street 12
orderValue: 24.90
deliveryType: BIKE
```

Erwartetes Ergebnis:

```text
Delivery by bike: fast for short distances.
```

---

## Reflexionsfragen

### Frage 1

Welche Klassen gehören zum Strategy Pattern?

### Frage 2

Wo befindet sich der Context in deiner Lösung?

### Frage 3

Welche Klasse entscheidet, welche Strategy verwendet wird?

### Frage 4

Welche Klasse führt die Strategy bei dir aus?

---

## Abgabe / Kontrolle

Am Ende sollen folgende Punkte funktionieren:

- Die Anwendung startet ohne Fehler.
- Die JSON-Daten werden beim Start importiert.
- `GET /orders` liefert alle Bestellungen.
- `GET /orders?deliveryType=BIKE` filtert nach Zustellart.
- `POST /orders` erstellt eine neue Bestellung.
- `PUT /orders/{id}/delivery-type` ändert die Zustellart.
- `GET /orders/{id}/delivery-info` gibt die Lieferinformation mit Strategy Pattern zurück.
- Die Geschäftslogik befindet sich im Service.
- Der Controller greift nicht direkt auf das Repository zu.
- Das Strategy Pattern ist im Code erkennbar.
- Es ist klar erklärbar, wo der Context liegt.
