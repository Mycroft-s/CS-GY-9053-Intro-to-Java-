package employees;

import java.util.Objects;

public class Intern extends Employee {
    private String university;

    public Intern(String name, String university) {
        super(name);
        this.university = university;
    }

    @Override
    public void work() {
        System.out.println("Learning and assisting as an intern from " + university + ".");
    }

    @Override
    public String toString() {
        return super.toString().replace("}", "") + ", university='" + university + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        Intern i = (Intern) o;
        return Objects.equals(university, i.university);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), university);
    }
}
