package se.iana.battler.services;

import se.iana.battler.models.Attack;
import se.iana.battler.models.Pokemon;
import se.iana.battler.models.Type;

import se.iana.battler.models.BattleStats;
import se.iana.battler.utils.InputHelper;
import java.util.List;
import java.util.Scanner;

import java.util.Random;

// Serviceklass som hanterar logik relaterad till strider
public class BattleService {
    private static final Random random = new Random();

    // Datorn väljer en motståndare slumpvis från en pool av "vilda" Pokemons
    public Pokemon generateWildPokemon() {
        String[] names = {"Zubat", "Pidgey", "Rattata", "Magikarp", "Geodude"};
        Type[] types = {Type.NORMAL, Type.NORMAL, Type.NORMAL, Type.WATER, Type.NORMAL};
        int[] hps = {40, 45, 35, 20, 50};

        int index = random.nextInt(names.length);
        Pokemon wild = new Pokemon(names[index], types[index], hps[index], hps[index]);

        // Ger datorns Pokemon en slumpmässig attack
        wild.addAttack(new Attack("Tackle", 20, 100, Type.NORMAL));

        return wild;
    }

    // Startar striden och hanterar Kraschtest-skydd
    public void startBattle(List<Pokemon> pokedex, BattleStats stats, Scanner scanner) {
        // Kraschtest: Starta strid utan att äga någon Pokemon
        if (pokedex.isEmpty()) {
            System.out.println("Fel: Du har inga Pokemon i din Pokedex! Lägg till en först.");
            return;
        }

        System.out.println("\n=== Välj din Pokemon för striden ===");
        for (int i = 0; i < pokedex.size(); i++) {
            System.out.println((i + 1) + ". " + pokedex.get(i).toString());
        }

        int choice = InputHelper.readInt(scanner, "Välj en Pokemon (1-" + pokedex.size() + "): ", 1, pokedex.size());
        Pokemon playerPokemon = pokedex.get(choice - 1);

        // Kraschtest: Starta strid med en Pokemon som har 0 hp
        if (playerPokemon.isFainted()) {
            System.out.println("Fel: Denna Pokemon har 0 HP och kan inte slåss!");
            return;
        }

        // Kraschtest: Starta strid med en Pokemon som har 0 attacker
        if (!playerPokemon.hasAttacks()) {
            System.out.println("Fel: Denna Pokémon har inga attacker och kan inte slåss!");
            return;
        }

        Pokemon wildPokemon = generateWildPokemon();
        System.out.println("\nEn vild " + wildPokemon.getName() + " dök upp!");
        System.out.println("Du valde " + playerPokemon.getName() + "!");

        // Här anropar vi stridsloopen
        battleLoop(playerPokemon, wildPokemon, stats, scanner);
    }
}