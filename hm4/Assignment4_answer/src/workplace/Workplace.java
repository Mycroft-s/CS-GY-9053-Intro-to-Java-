package workplace;

import employees.Employee;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Workplace {
    private String name;
    private final List<Employee> employees = new ArrayList<>();

    public Workplace(String name) {
        this.name = name;
    }

    public void addEmployee(Employee e) {
        if (e != null) employees.add(e);
    }

    public void removeEmployee(Employee e) {
        employees.remove(e);
    }

    public int numEmployees() {
        return employees.size();
    }

    public void listEmployees() {
        System.out.println("All employees at " + name + ":");
        for (Employee e : employees) System.out.println(e);
    }

    public void processWorkday() {
        System.out.println("Processing workday at " + name + "...");
        for (Employee e : employees) e.work();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Employee> getEmployees() { return employees; }

    @Override
    public String toString() {
        return "Workplace{name='" + name + "', numEmployees=" + employees.size() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workplace that = (Workplace) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
