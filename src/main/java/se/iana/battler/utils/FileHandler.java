package se.iana.battler.utils;

import se.iana.battler.models.Attack;
import se.iana.battler.models.Pokemon;
import se.iana.battler.models.Type;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final Path PATH = Path.of("pokedex.csv");

    // Spara till fil
    public static void save(List<Pokemon> pokedex) {
        List<String> lines = new ArrayList<>();
        for (Pokemon p : pokedex) {
            StringBuilder sb = new StringBuilder();
            sb.append(p.getName()).append(",")
                    .append(p.getType().name()).append(",")
                    .append(p.getMaxHp()).append(",")
                    .append(p.getCurrentHp()).append(",");

            // Spara attackerna hopslagna med egna tecken
            List<String> attackStrings = new ArrayList<>();
            for (Attack a : p.getAttacks()) {
                attackStrings.add(a.getName() + "|" + a.getBaseDamage() + "|" + a.getAccuracy() + "|" + a.getType().name());
            }
            sb.append(String.join(";", attackStrings));
            lines.add(sb.toString());
        }

        try {
            Files.writeString(PATH, String.join(System.lineSeparator(), lines));
        } catch (IOException e) {
            System.out.println("Fel: Kunde inte spara data till filen (" + e.getMessage() + ")");
        }
    }

    // Ladda saknad eller korrupt datafil
    public static List<Pokemon> loadOrSeed() {
        List<Pokemon> pokedex = new ArrayList<>();

        // Om filen inte finns - seeda hårdkodad data
        if (!Files.exists(PATH)) {
            System.out.println("Ingen sparad fil hittades. Skapar initial data (Seeding)...");
            return generateSeedData();
        }

        try {
            List<String> lines = Files.readAllLines(PATH);
            for (String line : lines) {
                if (line.isBlank()) continue;

                // split(",", -1) behåller tomma fält i slutet
                String[] parts = line.split(",", -1);
                if (parts.length < 5)
                    continue; // Hoppa över korrupt rad

                Pokemon p = new Pokemon(parts[0], Type.valueOf(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));

                if (!parts[4].isBlank()) {
                    String[] attacks = parts[4].split(";");
                    for (String att : attacks) {
                        String[] aParts = att.split("\\|");
                        if (aParts.length == 4) {
                            p.addAttack(new Attack(aParts[0], Integer.parseInt(aParts[1]), Integer.parseInt(aParts[2]), Type.valueOf(aParts[3])));
                        }
                    }
                }
                pokedex.add(p);
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Varning: Kunde inte ladda filen korrekt. Startar med tillgänglig data.");
        }
        return pokedex;
    }

    // Seeding
    public static List<Pokemon> generateSeedData() {
        List<Pokemon> seed = new ArrayList<>();

        Pokemon p1 = new Pokemon("Pikachu", Type.ELECTRIC, 35, 35);
        p1.addAttack(new Attack("Thunderbolt", 40, 100, Type.ELECTRIC));
        seed.add(p1);

        Pokemon p2 = new Pokemon("Charmander", Type.FIRE, 39, 39);
        p2.addAttack(new Attack("Ember", 40, 100, Type.FIRE));
        p2.addAttack(new Attack("Scratch", 40, 100, Type.NORMAL));
        seed.add(p2);

        Pokemon p3 = new Pokemon("Squirtle", Type.WATER, 44, 44);
        p3.addAttack(new Attack("Water Gun", 40, 100, Type.WATER));
        seed.add(p3);

        Pokemon p4 = new Pokemon("Bulbasaur", Type.GRASS, 45, 45);
        p4.addAttack(new Attack("Vine Whip", 45, 100, Type.GRASS));
        seed.add(p4);

        Pokemon p5 = new Pokemon("Eevee", Type.NORMAL, 55, 55);
        p5.addAttack(new Attack("Quick Attack", 40, 100, Type.NORMAL));
        seed.add(p5);

        Pokemon p6 = new Pokemon("Snorlax", Type.NORMAL, 160, 160);
        p6.addAttack(new Attack("Body Slam", 85, 100, Type.NORMAL));
        seed.add(p6);

        return seed;
    }
}