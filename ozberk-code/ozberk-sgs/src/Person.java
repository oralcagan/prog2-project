import java.util.HashSet;

public class Person {
    float charisma;
    float[] interests = new float[5];
    float health = 1;

    Person(float charisma) {
        this.charisma = charisma;
    }

    public static void makePeopleInteract(Person personA,Person personB,Group group) {
        float groupModifier = 0.2F;
        if(group != null) {
            groupModifier = group.influence;
        }
        Person.changePerson(personA,personB,groupModifier);
        Person.changePerson(personB,personA,groupModifier);
    }

    public static void changePerson(Person changer, Person changed,float rateOfChange) {
        for(int i = 0; i < changer.interests.length; i++) {
            changed.interests[i] += (changer.interests[i] - changed.interests[i])
                    * changer.charisma
                    * rateOfChange;
            changed.interests[i] = Math.clamp(changed.interests[i],0.F,1.F);
        }
    }
}