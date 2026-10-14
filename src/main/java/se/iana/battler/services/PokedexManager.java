package se.iana.battler.services;

import se.iana.battler.models.Attack;
import se.iana.battler.models.Pokemon;
import se.iana.battler.models.Type;
import se.iana.battler.utils.InputHelper;

import java.util.List;
import java.util.Scanner;

public class PokedexManager {
    private List<Pokemon> pokedex;

    public PokedexManager(List<Pokemon> initialData) {
        this.pokedex = initialData;
    }

    public List<Pokemon> getPokedex() {
        return pokedex;
    }

    // Visa alla
    public void viewAll() {
        if (pokedex.isEmpty()) {
            System.out.println("Pokedex är tom.");
            return;
        }
        for (int i = 0; i < pokedex.size(); i++) {
            System.out.println((i + 1) + ". " + pokedex.get(i).toString());
        }
    }

    // Lägg till
    public void addPokemon(Scanner scanner) {
        String name = InputHelper.readString(scanner, "Pokemon namn: ", 50);
        Type type = selectType(scanner);

        // Skydd mot 0 och negativa tal finns inbyggt i readInt
        int maxHp = InputHelper.readInt(scanner, "Max HP (1-999): ", 1, 999);

        Pokemon newPokemon = new Pokemon(name, type, maxHp, maxHp);
        addAttacksLoop(scanner, newPokemon);

        pokedex.add(newPokemon);
        System.out.println("Pokemon tillagd!");
    }

    // Uppdatera
    public void editPokemon(Scanner scanner) {
        viewAll();
        if (pokedex.isEmpty())
            return;

        // Använder pokedex.size() som maxgräns för att undvika IndexOutOfBoundsException
        int index = InputHelper.readInt(scanner, "Ange numret på den Pokemon du vill redigera: ", 1, pokedex.size()) - 1;
        Pokemon p = pokedex.get(index);

        p.setName(InputHelper.readString(scanner, "Nytt namn (" + p.getName() + "): ", 50));
        p.setType(selectType(scanner));
        p.setMaxHp(InputHelper.readInt(scanner, "Nytt Max HP (1-999): ", 1, 999));
        p.setCurrentHp(InputHelper.readInt(scanner, "Nuvarande HP: ", 0, p.getMaxHp()));

        int changeAttacks = InputHelper.readInt(scanner, "Vill du skriva över attackerna? (1 - Ja, 2 - Nej): ", 1, 2);
        if (changeAttacks == 1) {
            p.clearAttacks();
            addAttacksLoop(scanner, p);
        }
        System.out.println("Pokemon uppdaterad!");
    }

    // Ta bort
    public void deletePokemon(Scanner scanner) {
        viewAll();
        if (pokedex.isEmpty())
            return;

        int index = InputHelper.readInt(scanner, "Ange numret på den Pokemon du vill ta bort: ", 1, pokedex.size()) - 1;
        Pokemon removed = pokedex.remove(index);
        System.out.println(removed.getName() + " har tagits bort från Pokedex.");
    }

    // Hjälpmetod för att välja typ
    private Type selectType(Scanner scanner) {
        System.out.println("Välj typ:");
        Type[] types = Type.values();
        for (int i = 0; i < types.length; i++) {
            System.out.println((i + 1) + ". " + types[i]);
        }
        int choice = InputHelper.readInt(scanner, "Ditt val: ", 1, types.length);
        return types[choice - 1];
    }

    // Hjälpmetod för att lägga till attacker
    private void addAttacksLoop(Scanner scanner, Pokemon p) {
        while (p.getAttacks().size() < 4) {
            int add = InputHelper.readInt(scanner, "Lägg till attack? (1 - Ja, 2 - Nej): ", 1, 2);

            if (add == 1) {
                String aName = InputHelper.readString(scanner, "Attackens namn: ", 30);
                int dmg = InputHelper.readInt(scanner, "Skada (1-200): ", 1, 200);
                int acc = InputHelper.readInt(scanner, "Träffsäkerhet (1-100): ", 1, 100);
                Type aType = selectType(scanner);

                p.addAttack(new Attack(aName, dmg, acc, aType));
                System.out.println("Attack tillagd! (Totalt: " + p.getAttacks().size() + "/4)");
            } else {
                break;
            }
        }
    }
}
