import java.util.HashMap;
import java.util.Map;

public class Person {
    // Attributes
    private int name;
    private Map<String, Float> stats;

    // Constructor
    public Person(int name, float charisma, float intelligence, float aggressiveness, float happiness) {
        this.name = name;
        stats = new HashMap<>();
        stats.put("charisma", charisma);
        stats.put("intelligence", intelligence);
        stats.put("aggressiveness", aggressiveness);
        stats.put("happiness", happiness);
    }

    // Method to update a specific stat by a certain amount
    public void changeStat(String stat, float amount) {
        if (stats.containsKey(stat)) {
            float currentValue = stats.get(stat);
            stats.put(stat, currentValue * (1 + amount));
        } else {
            System.out.println("Invalid stat name.");
        }
    }

    // Getter for a specific stat
    public float getStat(String stat) {
        if (stats.containsKey(stat)) {
            return stats.get(stat);
        } else {
            System.out.println("Invalid stat name.");
            return 0.0f;
        }
    }

    // Getter for the name
    public int getName() {
        return name;
    }

    // Setter for the name
    public void setName(int name) {
        this.name = name;
    }
}
