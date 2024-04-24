//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Person person = new Person(123456, 10.5f, 5.2f, 7.8f, 10.0f);
        System.out.println("Person's name: " + person.getName());
        person.changeStat("intelligence", 0.25f); // Increase intelligence by 2.5
        float intelligence = person.getStat("intelligence"); //
        System.out.println("Person's intelligence: " + intelligence);
    }
}