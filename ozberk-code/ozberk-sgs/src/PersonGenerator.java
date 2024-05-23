import java.util.Random;

public class PersonGenerator {
    Random random = new Random();

    public Person generateRandomPerson() {
        float charisma = this.random.nextFloat();
        Person person = new Person(charisma);
        for(int i = 0; i < person.interests.length; i++) {
            person.interests[i] = random.nextFloat();
        }
        return person;
    }
}
