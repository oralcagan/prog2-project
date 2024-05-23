import java.util.*;

public class Main {
    public static void main(String[] args) {
        int numPeople = 1000;
        float minGroupAffiliation = 0.6F;
        int numInterests = 5;
        int numTurns = 300;
        HashMap<Integer,Person> allPeople = new HashMap<Integer, Person>();
        Group[] groups = new Group[numInterests];
        PersonGenerator personGenerator = new PersonGenerator();
        GroupGenerator groupGenerator = new GroupGenerator();
        for(int i = 0; i < numInterests; i++) {
            groups[i] = groupGenerator.generateRandomGroup();
        }
        for(int i = 0; i < numPeople; i++) {
            allPeople.put(i,personGenerator.generateRandomPerson());
        }
        for(int key : allPeople.keySet()) {
            for(int i = 0; i < numInterests; i++) {
                groups[i].updateGroupStatus(allPeople,key,i,minGroupAffiliation);
            }
        }
        InteractionSet interactionSet = new InteractionSet();
        for(int turnNumber = 0; turnNumber < numTurns; turnNumber++) {
            if(turnNumber % 20 == 0) {
                for(Group group : groups) {
                    System.out.println(group.members);
                }
                System.out.println("------------------");
            }
            for(int groupIndex = 0; groupIndex < numInterests; groupIndex++) {
                Group group = groups[groupIndex];
                List<Integer> memberList = group.members.stream().toList();
                for(int i = 0; i < memberList.size(); i++) {
                    for(int j = 1; j < memberList.size(); j++) {
                        Integer idPersonA = memberList.get(i);
                        Integer idPersonB = memberList.get(j);
                        if(!interactionSet.has(idPersonA,idPersonB)) {
                            interactionSet.add(idPersonA,idPersonB);
                            Person personA = allPeople.get(idPersonA);
                            Person personB = allPeople.get(idPersonB);
                            Person.makePeopleInteract(personA,personB,group);
                            for(int k = 0; k < numInterests; k++) {
                                Group groupK = groups[k];
                                groupK.updateGroupStatus(allPeople,idPersonA,k,minGroupAffiliation);
                                groupK.updateGroupStatus(allPeople,idPersonB,k,minGroupAffiliation);
                            }
                        }
                    }
                }
            }
            interactionSet.clear();
        }
    }
    public static List<Integer> createRandomGroup(HashMap<Integer, Person> people, int groupLength) {
        Random random = new Random();
        HashSet<Integer> randomPeople = new HashSet<Integer>();
        for(int i = 0; i < groupLength; i++) {
            randomPeople.add(random.nextInt(0,people.size()));
        }
        return randomPeople.stream().toList();
    }
}