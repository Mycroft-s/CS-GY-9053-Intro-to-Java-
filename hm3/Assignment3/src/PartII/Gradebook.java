package PartII;

import java.util.concurrent.ThreadLocalRandom;

public class Gradebook {
    private final Student[] students;

    public Gradebook(int capacity) {
        this.students = new Student[capacity];
    }

    public void addStudent(Student s) {
        for (int i = 0; i < students.length; i++) {
            if (students[i] == null) {
                students[i] = s;
                return;
            }
        }
    }

    public Student findById(int id) {
        for (Student s : students) {
            if (s != null && s.getId() == id) return s;
        }
        return null;
    }

    public Student getTopStudent() {
        Student best = null;
        double bestAvg = -1.0;
        for (Student s : students) {
            if (s == null) continue;
            double avg = s.getAverage();
            if (avg > bestAvg) {
                bestAvg = avg;
                best = s;
            }
        }
        return best;
    }

    public void printAll() {
        for (Student s : students) {
            if (s != null) System.out.println(s);
        }
    }

    public static void main(String[] args) {
        Gradebook gb = new Gradebook(5);
        gb.addStudent(new Student("Alice"));
        gb.addStudent(new Student("Bob"));
        gb.addStudent(new Student("Carol"));
        gb.addStudent(new Student("David"));
        gb.addStudent(new Student("Eve"));

        for (Student s : gb.students) {
            if (s == null) continue;
            for (int i = 0; i < 5; i++) {
                int g = ThreadLocalRandom.current().nextInt(0, 101);
                s.setGrade(i, g);
            }
        }

        System.out.println("All students:");
        gb.printAll();

        Student top = gb.getTopStudent();
        System.out.println("\nTop student:");
        System.out.println(top == null ? "None" : top);
    }
}
