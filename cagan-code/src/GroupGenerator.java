import java.util.Random;

public class GroupGenerator {
    Random random = new Random();
    public Group generateRandomGroup() {
        return new Group(0.2F);
    }
}
