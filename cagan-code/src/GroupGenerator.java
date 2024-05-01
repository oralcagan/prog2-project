import java.util.Random;

public class GroupGenerator {
    Random random = new Random();
    public Group generateRandomGroup() {
        return new Group(random.nextFloat(0.1F,0.2F));
    }
}
