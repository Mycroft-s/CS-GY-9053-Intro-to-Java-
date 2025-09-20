package PartII;

import java.util.Arrays;

public class Student {
    private static int NEXT_ID = 1;

    private final int id;
    private final String name;
    private final int[] grades;

    public Student(String name) {
        this.id = NEXT_ID++;
        this.name = name;
        this.grades = new int[5];
        Arrays.fill(this.grades, -1);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int[] getGrades() {
        return Arrays.copyOf(grades, grades.length);
    }

    public void setGrade(int assignmentIndex, int grade) {
        if (assignmentIndex < 0 || assignmentIndex >= grades.length) return;
        if (grade < 0 || grade > 100) return;
        grades[assignmentIndex] = grade;
    }

    public double getAverage() {
        int sum = 0, count = 0;
        for (int g : grades) {
            if (g >= 0) {
                sum += g;
                count++;
            }
        }
        return count == 0 ? 0.0 : (double) sum / count;
    }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + '\'' +
                ", grades=" + Arrays.toString(grades) +
                ", avg=" + String.format("%.2f", getAverage()) + "}";
    }
}
