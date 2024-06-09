import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
public class PersonGenerator {
    Random random = new Random();
    public int generatePopulus(int n, HashSet<Integer> populusIndex, ArrayList<Person> populus, int counter){
        populusIndex.clear();
        for (int i = 0; i < n; i ++){
            populus.add(generateRandomPerson());
            populusIndex.add(counter);
            counter ++;
        }
        return counter;
    }
    public Person generateRandomPerson() {
        float charisma = this.random.nextFloat();
        Person person = new Person(charisma);
        for(int i = 0; i < person.interests.length; i++) {
            person.interests[i] = random.nextFloat(0.1F, 1.0F);
        }
        return person;
    }
}