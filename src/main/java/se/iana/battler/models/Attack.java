package se.iana.battler.models;

public class Attack {
    private String name;
    private int baseDamage;
    private int accuracy;
    private Type type;

    public Attack(String name, int baseDamage, int accuracy, Type type) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Namnet får inte vara tomt.");
        }

        if (baseDamage <= 0) {
            throw new IllegalArgumentException("Basskadan måste vara större än 0.");
        }

        if (accuracy < 0 || accuracy > 100) {
            throw new IllegalArgumentException("Träffsäkerheten måste vara mellan 0 och 100.");
        }

        if (type == null) {
            throw new IllegalArgumentException("Typen får inte vara null.");
        }

        this.name = name;
        this.baseDamage = baseDamage;
        this.accuracy = accuracy;
        this.type = type;
    }

    public String getName() { return name; }
    public int getBaseDamage() { return baseDamage; }
    public int getAccuracy() { return accuracy; }
    public Type getType() { return type; }

    @Override
    public String toString() {
        return name + " (Damage: " + baseDamage + ", Accuracy: " + accuracy + "%, Type: " + type + ")";
    }
}