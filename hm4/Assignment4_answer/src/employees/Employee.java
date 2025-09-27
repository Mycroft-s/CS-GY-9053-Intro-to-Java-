package employees;

import java.util.Objects;

public class Employee {
    private static int numberOfEmployees = 0;
    private final int id;
    private String name;

    public Employee(String name) {
        this.id = ++numberOfEmployees;
        this.name = name;
    }

    public void work() {
        System.out.println("Doing work.");
    }

    public void displayInfo() {
        System.out.println(this.toString());
    }

    public static int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{id=" + id + ", name='" + name + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id && Objects.equals(name, employee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
