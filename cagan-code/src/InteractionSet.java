import java.util.HashSet;

/**
 To check if they interacted before, two numbers are combined and put into a set.
 When combining the 2 numbers, the bigger number is shifted 16 bits to the left,
 then the smaller number is added to the smaller bits.
 This creates a new number which is unique for each interaction.*/
public class InteractionSet {
    HashSet<Integer> set = new HashSet<Integer>();

    public void clear() {
        this.set.clear();
    }

    private int combineNumbers(Integer numA, Integer numB) {
        int smallerNumber = Math.min(numA,numB);
        int biggerNumber = Math.max(numA,numB);
        biggerNumber = biggerNumber << 16;
        biggerNumber += smallerNumber;
        return biggerNumber;
    }

    public void add(Integer numA, Integer numB) {
        int combinedNumber = this.combineNumbers(numA,numB);
        this.set.add(combinedNumber);
    }

    public boolean has(Integer numA, Integer numB) {
        return this.set.contains(this.combineNumbers(numA,numB));
    }
}
