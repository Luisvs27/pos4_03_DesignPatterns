# 1 Coding-Conventions

The following coding conventions are to be followed when programming:

- Naming conventions
  - Rules for identifier syntax: allowed letters, numbers, and special characters.  
    No use of umlauts.
  - Meaningful names in English are to be used for names.  
    Exception: single letters may be used for loop variables (e.g. int i).
  - CamelCase: uppercase letters within a word
  - Uppercase for class names
  - Lowercase for all other identifiers (variables, methods, etc.)
  - Naming conventions for getter and setter methods: getXxx(), setXxx(), isXxx()
- Indentation: Ctrl-Alt-L → IntelliJ Auto-Formatter
  - Indentations (using spaces)
  - Line breaks
  - Observe line length (gray line in IntelliJ)
- Comments:
  - JavaDoc comments for all self-implemented methods
  - Explanatory implementation comments
- Miscellaneous
  - Only one instruction per line
  - Only one declaration per line

---

# 2 Design Patterns Kategorien – Wiederholung

- Creational – Wie werden Objekte erstellt?
  - Beispiele: Singleton, Factory Method.
- Structural – Wie werden Objekte zusammengesetzt/umhüllt?
  - Beispiel: Decorator.
- Behavioral – Wie kooperieren Objekte?
  - Beispiele: Strategy, Observer.

---

# 3 Strategy Pattern (Behavioral) – Wiederholung

- Idee
  - Verhalten auslagern
  - Statt viele if/else-Abfragen Klassen für verschiedene Varianten erstellen
- Bausteine
  - Strategy Interface: definiert das gemeinsame Verhalten.
  - Konkrete Strategien: verschiedene Implementierungen des Verhaltens.
  - Context: Nutzt eine Strategie und delegiert die Ausführung, kennt aber keine Details.
- Video
  - Christopher Okhravi – Strategy Pattern

## 3.1 Der Nutzen von Context in SpringBoot

- Context – Allgemein
  - Klasse, die mit einer Strategy arbeitet
  - Anstatt:

```java
if (type.equals("SWORD")) {
   ...
}
```

  - … verwenden wir:

```java
strategy.attack();
```

  - Der Context muss nicht wissen, wie der Angriff funktioniert.  
    Er weiß nur: „Ich habe eine Strategy und führe sie aus.“

- Konsolenanwendung vs Spring Boot
  - Konsole
    - Oft eigene Klassen wie ReportContext, AttackContext, ExportContext
  - Spring Boot
    - Service-Klassen beinhalten oft schon Business Logic:  
      CharacterService, BattleService, RentalService
    - Service könnte auch Rolle des Context übernehmen

- Zwei Varianten bei Spring Boot
  - Variante A: Eigene Context-Klasse
    - Controller  
      → Service  
      → AttackContext  
      → AttackStrategy
    - Pattern ist klar ersichtlich
  - Variante B: Service als Context
    - Controller  
      → CharacterService  
      → AttackStrategy
    - Bei kleineren Projekten auch ausreichend!

- Empfehlungen für unsere Übungen
  - Zuerst bewusst eigene Context-Klasse verwenden
  - Danach vergleichen:
    - Wo ist der Context?
      - eigene Klasse AttackContext
      - CharacterService übernimmt diese Rolle
  - Hoffentlich erkenntlich, dass:
    - DesignPatterns keine starren Regeln sind und
    - Helfen, den Code sinnvoll zu strukturieren

## 3.2 Simples Code-Tutorial

### IAttackStrategy.java

```java
public interface IAttackStrategy {
    String attack(String characterName);
}
```

### SwordAttackStrategy.java

```java
public class SwordAttackStrategy implements IAttackStrategy {
    @Override
    public String attack(String characterName) {
        return characterName + " attacks with a sword!";
    }
}
```

### BowAttackStrategy.java

```java
public class BowAttackStrategy implements IAttackStrategy {
    @Override
    public String attack(String characterName) {
        return characterName + " shoots an arrow!";
    }
}
```

### SpecialAttackStrategy.java

```java
public class SpecialAttackStrategy implements IAttackStrategy {
    @Override
    public String attack(String characterName) {
        return characterName + " uses a powerful special attack!";
    }
}
```

### AttackContext.java

```java
public class AttackContext {
    private IAttackStrategy strategy;

    public void setStrategy(IAttackStrategy strategy) { 
        this.strategy = strategy; 
    }

    public String executeAttack(String characterName) {
        if (strategy == null)
            return characterName + " has no attack strategy!");
        else
            return strategy.attack(characterName);
    }
}
```

### Verwendung z.B. im Service:

```java
@Service
public class CharacterService {

    public String attack(AnimeCharacter character) {
        AttackContext context = new AttackContext();

        if ("SWORD".equalsIgnoreCase(character.getAttackType())) {
            context.setStrategy(new SwordAttackStrategy());
        } else if ("BOW".equalsIgnoreCase(character.getAttackType())) {
            context.setStrategy(new BowAttackStrategy());
        } else if ("SPECIAL".equalsIgnoreCase(character.getAttackType())) {
            context.setStrategy(new SpecialAttackStrategy());
        }

        return context.executeAttack(character.getName());
    }
}
```

---

# 4 Singleton Pattern (Creational)

- Idee
  - Es gibt genau eine Instanz der Klasse.
  - Diese Instanz ist global zugänglich – wie ein zentraler Manager.
- Bausteine
  - Privater Konstruktor: Verhindert, dass andere Klassen new verwenden.
  - getInstance(): Liefert die einzige Instanz zurück.
- Nutzen
  - Ideal für gemeinsame Ressourcen wie:
    - Logger
    - Konfigurationseinstellungen
    - Datenbankverbindungen
- In Spring Boot ist wichtig:
  - Viele Klassen wie @Service sind bereits ähnlich wie Singleton, weil Spring standardmäßig nur eine Instanz davon verwaltet.
- Video-Erklärung
  - Christopher Okhravi – Singleton Pattern

## 4.1 Code-Tutorial: GameSettings

### GameSettings.java

```java
public class GameSettings {
    private static final GameSettings INSTANCE = new GameSettings();

    private String difficulty = "NORMAL";

    private GameSettings() {
    }

    public static GameSettings getInstance() {
        return INSTANCE;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
```
