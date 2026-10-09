package se.iana.battler.models;

public class BattleStats {
    private int wins;
    private int losses;

    public BattleStats() {
        this.wins = 0;
        this.losses = 0;
    }

    public BattleStats(int wins, int losses) {
        this.wins = Math.max(0, wins);
        this.losses = Math.max(0, losses);
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public void addWin() {
        this.wins++;
    }

    public void addLoss() {
        this.losses++;
    }

    @Override
    public String toString() {
        return String.format("Vinster: %d | Förluster: %d", wins, losses);
    }
}