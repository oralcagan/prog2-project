import java.util.List;

public class PhysicalGroup extends Group{
    float prosperity;

    PhysicalGroup(float prosperity,float influence, List<Person> members) {
        super(influence, members);
    }
}
