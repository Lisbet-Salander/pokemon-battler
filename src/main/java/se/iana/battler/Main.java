package se.iana.battler;

import se.iana.battler.models.BattleStats;
import se.iana.battler.services.BattleService;
import se.iana.battler.services.PokedexManager;
import se.iana.battler.utils.FileHandler;
import se.iana.battler.utils.InputHelper;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ladda filen eller generera initial data (Seeding) vid start
        PokedexManager manager = new PokedexManager(FileHandler.loadOrSeed());

        // Laddar stridsstatistik vid start
        BattleStats stats = FileHandler.loadStats();

        // Skapa BattleService
        BattleService battleService = new BattleService();

        boolean running = true;
        System.out.println("Välkommen till Pokedex!");

        while (running) {
            printMenu();

            // Skydd mot inmatning av ogiltiga menyval
            int choice = InputHelper.readInt(scanner, "Välj (1-10): ", 1, 10);

            switch (choice) {
                case 1 -> manager.viewAll();
                case 2 -> manager.addPokemon(scanner);
                case 3 -> manager.editPokemon(scanner);
                case 4 -> manager.deletePokemon(scanner);
                case 5 -> {
                    FileHandler.save(manager.getPokedex());
                    System.out.println("Pokedex har sparats till fil!");
                }
                case 6 -> {
                    manager = new PokedexManager(FileHandler.loadOrSeed());
                    System.out.println("Data har laddats från fil!");
                }
                case 7 -> {
                    // Ersätt nuvarande samling med genererad startdata
                    manager = new PokedexManager(FileHandler.generateSeedData());
                    System.out.println("Pokedex har återställts till startdata!");
                }
                case 8 -> battleService.startBattle(manager.getPokedex(), stats, scanner);                case 9 -> System.out.println("Statistik: " + stats.toString()); // Nytt menyval
                case 10 -> {
                    FileHandler.save(manager.getPokedex());
                    FileHandler.saveStats(stats); // Sparar statistiken när vi avslutar
                    System.out.println("Data sparad. Hej då!");
                    running = false;
                }
            }
        }
        scanner.close();
    }
    private static void printMenu() {
        System.out.println("\n=== Huvudmeny ===");
        System.out.println("1. Visa alla Pokemon");
        System.out.println("2. Lägg till Pokemon");
        System.out.println("3. Redigera Pokemon");
        System.out.println("4. Ta bort Pokemon");
        System.out.println("5. Spara till fil (manuellt)");
        System.out.println("6. Ladda från fil (manuellt)");
        System.out.println("7. Återställ till seedad data");
        System.out.println("8. Starta strid"); // Nytt menyval
        System.out.println("9. Stridsresultat"); // Nytt menyval
        System.out.println("10. Avsluta och spara");
    }
}