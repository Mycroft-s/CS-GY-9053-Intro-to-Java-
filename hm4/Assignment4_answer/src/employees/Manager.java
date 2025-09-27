package employees;

import java.util.Objects;

public class Manager extends Employee {
    private int teamSize;

    public Manager(String name, int teamSize) {
        super(name);
        this.teamSize = teamSize;
    }

    @Override
    public void work() {
        System.out.println("Managing team of " + teamSize + ".");
    }

    @Override
    public String toString() {
        return super.toString().replace("}", "") + ", teamSize=" + teamSize + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        Manager m = (Manager) o;
        return teamSize == m.teamSize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), teamSize);
    }
}
