package orgchart;

public class IndividualContributor extends Employee {
    public IndividualContributor(String name, String title, int baseSalary) {
        super(name, title, baseSalary);
    }
    @Override public int getHeadCount() { return 1; }
    @Override public int getTotalComp() { return baseSalary; }
}
