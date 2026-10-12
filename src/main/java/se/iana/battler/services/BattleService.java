package se.iana.battler.services;

import se.iana.battler.models.Attack;
import se.iana.battler.models.Pokemon;
import se.iana.battler.models.Type;

import java.util.Random;

public class BattleService {
    private static final Random random = new Random();

    // Datorn väljer en motståndare slumpvis från en pool av "vilda" Pokemons
    public Pokemon generateWildPokemon() {
        String[] names = {"Zubat", "Pidgey", "Rattata", "Magikarp", "Geodude"};
        Type[] types = {Type.NORMAL, Type.NORMAL, Type.NORMAL, Type.WATER, Type.NORMAL};
        int[] hps = {40, 45, 35, 20, 50};

        int index = random.nextInt(names.length);
        Pokemon wild = new Pokemon(names[index], types[index], hps[index], hps[index]);

        // Ger datorns Pokémon en slumpmässig attack
        wild.addAttack(new Attack("Tackle", 20, 100, Type.NORMAL));

        return wild;
    }
}