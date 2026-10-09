package se.iana.battler.models;
import java.util.ArrayList;
import java.util.List;

public class Pokemon {
    private String name;
    private Type type;
    private int maxHp;
    private int currentHp;
    private List<Attack> attacks;

    public Pokemon(String name, Type type, int maxHp, int currentHp) {
        this.name = name;
        this.type = type;
        this.maxHp = maxHp;
        this.currentHp = currentHp;
        this.attacks = new ArrayList<>(); // Använder ArrayList för en dynamisk lista med attacker
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }

    public int getMaxHp() { return maxHp; }
    public void setMaxHp(int maxHp) { this.maxHp = maxHp; }

    public int getCurrentHp() { return currentHp; }

    // Skydd: Hp kan inte vara mindre än 0 eller större än maxgränsen
    public void setCurrentHp(int currentHp) {
        this.currentHp = Math.max(0, Math.min(currentHp, this.maxHp));
    }

    public List<Attack> getAttacks() { return attacks; }

    // Metod för att lägga till en attack (högst 4)
    public boolean addAttack(Attack attack) {
        if (this.attacks.size() < 4) {
            this.attacks.add(attack);
            return true;
        }
        return false;
    }

    public void clearAttacks() {
        this.attacks.clear();
    }

    @Override
    public String toString() {
        return String.format("%s [Typ: %s, HP: %d/%d, Attacker: %d/4]",
                name, type, currentHp, maxHp, attacks.size());
    }
}
