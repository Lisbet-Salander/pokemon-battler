# Pokemon Battler

A console-based Java application where the user can manage a Pokedex and let one of their Pokemon battle against a randomly selected wild Pokemon. The project builds on basic Pokedex functionality and uses Maven and Java 21.

## Features

- View, add, edit, and delete Pokemon.
- Save and load Pokedex data in `pokedex.csv`.
- Reset the Pokedex to predefined starter data.
- Select a Pokemon and start a turn-based battle against a random wild Pokemon.
- Choose the player's attack from a menu; the computer randomly selects one of the opponent's attacks.
- Attack accuracy determines whether an attack hits.
- HP decreases when a Pokemon is hit, and the battle ends when one Pokemon is defeated.
- Display the battle log and final result.
- Save and display the total number of wins and losses using `stats.csv`.
- Basic validation of menu choices and numeric input.

## Requirements

- JDK 21
- Maven
- IntelliJ IDEA or another Java development environment

## How to Run the Program

### Using IntelliJ IDEA

1. Clone the repository and open the project folder as a Maven project.
2. Select JDK 21 as the Project SDK.
3. Open `src/main/java/se/iana/battler/Main.java`.
4. Run the `main` method in the `se.iana.battler.Main` class.

### Using Maven and the Terminal

Run the following commands from the project's root directory, where `pom.xml` is located:

```bash
mvn clean compile
java -cp target/classes se.iana.battler.Main
```

## Data Files

The program uses two CSV files in the program's working directory:

- `pokedex.csv` contains Pokemon data, HP, types, and attacks.
- `stats.csv` contains the total number of wins and losses.

If `pokedex.csv` is missing, predefined starter data is loaded. If `stats.csv` is missing, the statistics start at zero.

Pokedex data is saved when the user selects the manual save option or exits the program using menu option 10. Battle statistics are saved when the program exits using menu option 10.

## OOP Design and Structure

The classes are organized according to their responsibilities:

- `Pokemon` represents a Pokemon and its HP, type, and attacks.
- `Attack` represents an attack with a name, base damage, accuracy, and type.
- `BattleStats` keeps track of wins and losses.
- `Type` is an `enum` that restricts which types can be used.
- `PokedexManager` handles displaying and modifying the Pokemon collection.
- `BattleService` handles wild Pokemon selection, the battle loop, attacks, and damage calculation.
- `FileHandler` reads and saves CSV data.
- `InputHelper` contains reusable validation for user input.

`Pokemon` contains a list of `Attack` objects. This is an example of composition: a Pokemon has attacks. The model classes use private fields and methods to access or modify their data.

## Turn Order and Damage Calculation

The player always takes their turn first. If the opponent's Pokemon still has HP after the player's attack, the computer selects an attack and takes its turn.

First, the attack's accuracy is checked. A random number between 1 and 100 is generated. If the number is greater than the attack's accuracy, the attack misses.

If the attack hits, the damage is calculated using the following formula:

```text
randomFactor = 0.8 + (1.2 - 0.8) * randomValue
damage = max(1, integer part of (baseDamage * randomFactor))
```

The random value is between 0.0 and 1.0, giving a random factor from 0.8 up to, but not including, 1.2. The minimum damage for a successful hit is 1 HP.

Type advantages and disadvantages do not affect damage in the current version.
