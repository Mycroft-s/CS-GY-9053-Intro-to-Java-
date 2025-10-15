package orgchart;

import java.util.ArrayList;

public class OrgTest {
    public static void main(String[] args) {
        IndividualContributor terrance = new IndividualContributor("Terrance Ng", "Senior Engineer", 225000);
        IndividualContributor sarah    = new IndividualContributor("Sarah Blinken", "Engineer", 230000);
        IndividualContributor raymond  = new IndividualContributor("Raymond Zhang", "Director of Sales", 265000);

        ArrayList<Employee> johnReports = new ArrayList<>();
        johnReports.add(terrance);
        johnReports.add(sarah);
        Manager john = new Manager("John Vice", "VP", 250000, johnReports);

        ArrayList<Employee> bobReports = new ArrayList<>();
        bobReports.add(john);
        bobReports.add(raymond);
        Manager bob = new Manager("Bob Ceo", "CEO", 350000, bobReports);

        System.out.println("Headcount of John Vice: " + john.getHeadCount());      // 3
        System.out.println("Headcount of Bob Ceo: " + bob.getHeadCount());         // 5
        System.out.println("Total compensation of John Vice: $" + john.getTotalComp()); // 705000
        System.out.println("Total compensation of Bob Ceo: $" + bob.getTotalComp());   // 1320000
        System.out.println("Path from Sarah Blinken to CEO: " + sarah.pathToCeo());    // name + id
        System.out.println("Organization structure valid? " + sarah.validate());       // true
    }
}
