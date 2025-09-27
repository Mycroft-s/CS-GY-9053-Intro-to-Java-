package workplace;

import employees.Employee;
import employees.Manager;
import employees.Intern;

public class Main {
    public static void main(String[] args) {
        Employee e1 = new Employee("Alice");
        Employee e2 = new Employee("Bob");
        Manager  m1 = new Manager("Carol", 6);
        Intern   i1 = new Intern("Dave", "USC");
        Intern   i2 = new Intern("Eve",  "UCLA");

        Workplace wp = new Workplace("Tech Dept");
        wp.addEmployee(e1);
        wp.addEmployee(e2);
        wp.addEmployee(m1);
        wp.addEmployee(i1);
        wp.addEmployee(i2);

        wp.listEmployees();
        System.out.println();
        wp.processWorkday();
        System.out.println();
        System.out.println(wp);
    }
}
