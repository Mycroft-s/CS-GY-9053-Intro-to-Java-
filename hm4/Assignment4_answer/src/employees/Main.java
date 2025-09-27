package employees;

public class Main {
    public static void main(String[] args) {
        Employee e1 = new Employee("Alice");
        Employee e2 = new Employee("Bob");
        Manager m1 = new Manager("Carol", 6);
        Intern i1 = new Intern("Dave", "USC");
        Intern i2 = new Intern("Eve", "UCLA");

        e1.displayInfo(); e1.work();
        e2.displayInfo(); e2.work();
        m1.displayInfo(); m1.work();
        i1.displayInfo(); i1.work();
        i2.displayInfo(); i2.work();

        System.out.println("Total employees: " + Employee.getNumberOfEmployees());
    }
}
