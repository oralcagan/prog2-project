import java.util.Random;

public class PersonGenerator {
    Random random = new Random();

    public Person generateRandomPerson(int numInterests) {
        float charisma = this.random.nextFloat();
        Person person = new Person(charisma,numInterests);
        for(int i = 0; i < person.interests.length; i++) {
            person.interests[i] = random.nextFloat();
        }
        return person;
    }
}
