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

        // Ger den vilda Pokemon två attacker
        wild.addAttack(new Attack("Tackle", 20, 100, Type.NORMAL));
        wild.addAttack(new Attack("Quick Attack", 15, 100, Type.NORMAL));

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

        // Starta strid med en Pokemon som har 0 hp
        if (playerPokemon.isFainted()) {
            System.out.println("Fel: Denna Pokemon har 0 HP och kan inte slåss!");
            return;
        }

        // Starta strid med en Pokemon som har 0 attacker
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

    // Huvudloopen för striden
    private void battleLoop(Pokemon player, Pokemon cpu, BattleStats stats, Scanner scanner) {
        System.out.println("\n--- Striden börjar! ---");

        // Striden slutar när ena sidans Pokemon är besegrad
        while (!player.isFainted() && !cpu.isFainted()) {

            // 1. Spelarens tur
            System.out.println("\n>>> " + player.getName() + "s tur! (HP: " + player.getCurrentHp() + "/" + player.getMaxHp() + ")");
            List<Attack> playerAttacks = player.getAttacks();
            for (int i = 0; i < playerAttacks.size(); i++) {
                System.out.println((i + 1) + ". " + playerAttacks.get(i).getName() + " (Skada: " + playerAttacks.get(i).getBaseDamage() + ", Träff: " + playerAttacks.get(i).getAccuracy() + "%)");
            }

            // Välj attack
            int attackChoice = InputHelper.readInt(scanner, "Välj attack (1-" + playerAttacks.size() + "): ", 1, playerAttacks.size());
            Attack playerAttack = playerAttacks.get(attackChoice - 1);

            executeAttack(player, cpu, playerAttack);

            if (cpu.isFainted()) {
                System.out.println("\nDen vilda " + cpu.getName() + " svimmade! Du vann!");
                stats.addWin();
                return;
            }

            // 2. Datorns tur (slumpvis attack)
            System.out.println("\n>>> " + cpu.getName() + "s tur! (HP: " + cpu.getCurrentHp() + "/" + cpu.getMaxHp() + ")");
            List<Attack> cpuAttacks = cpu.getAttacks();
            Attack cpuAttack = cpuAttacks.get(random.nextInt(cpuAttacks.size()));

            executeAttack(cpu, player, cpuAttack);

            if (player.isFainted()) {
                System.out.println("\n" + player.getName() + " svimmade! Du förlorade...");
                stats.addLoss();
                return;
            }
        }
    }

    // Utför attacken med träffsäkerhet och skadeberäkning
    private void executeAttack(Pokemon attacker, Pokemon defender, Attack attack) {
        System.out.println(attacker.getName() + " använder " + attack.getName() + "!");

        // Träffsäkerhet avgör om attacken träffar
        int hitChance = random.nextInt(100) + 1;
        if (hitChance > attack.getAccuracy()) {
            System.out.println("Attacken missade!");
            return;
        }

        // Skadan beräknas: basskada * slumpfaktor (0.8 till 1.2)
        double randomFactor = 0.8 + (1.2 - 0.8) * random.nextDouble();
        int damage = (int) (attack.getBaseDamage() * randomFactor);
        damage = Math.max(1, damage); // Minst 1 i skada vid träff

        System.out.println("Det var en träff! " + defender.getName() + " tog " + damage + " i skada.");
        defender.takeDamage(damage); // Metoden drar av HP säkert (aldrig under 0)
    }
}