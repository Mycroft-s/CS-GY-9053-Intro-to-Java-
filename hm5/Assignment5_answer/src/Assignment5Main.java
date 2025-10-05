import java.util.ArrayList;
import java.util.Random;

import person.Developer;
import person.Person;
import person.TechWriter;
import person.Tester;

import task.Task;

import triplet.Triplet;

public class Assignment5Main {
    public static void main(String[] args) {
        // ---------- Part I:----------
        ArrayList<Task> jobQueue = new ArrayList<>();
        Person dev = new Developer(jobQueue);
        Person writer = new TechWriter(jobQueue);
        Person tester = new Tester(jobQueue);

        Random rand = new Random();
        int tasksPerPerson = 5;

        for (int i = 0; i < tasksPerPerson; i++) {
            dev.startTask(randomModule(rand), randomTime(rand));
            writer.startTask(randomModule(rand), randomTime(rand));
            tester.startTask(randomModule(rand), randomTime(rand));
        }

        int total = 0;
        System.out.println("=== Performing tasks (Part I) ===");
        for (Task t : jobQueue) {
            t.performTask();
            total += t.getTime();
        }
        System.out.println("Total time = " + total);

        // ---------- Part II: ----------
        jobQueue.sort(null); // time 
        System.out.println("\n=== Sorted by time (Part II) ===");
        for (Task t : jobQueue) {
            System.out.println(t);
        }

        // ---------- Part III: Triplet + Comparable ----------
        ArrayList<Triplet<Double, Double, Double>> vecs = new ArrayList<>();
        vecs.add(new Triplet<>(3.0, 4.0, 0.0));   // mag=5
        vecs.add(new Triplet<>(1.0, 1.0, 1.0));   // mag=sqrt(3)
        vecs.add(new Triplet<>(0.0, 0.0, 10.0));  // mag=10
        vecs.add(new Triplet<>(2.0, 2.0, 2.0));   // mag=sqrt(12)

        System.out.println("\n=== Triplets before sort (Part III) ===");
        for (Triplet<Double, Double, Double> v : vecs) {
            System.out.println(v);
        }

        vecs.sort(null); // 
        System.out.println("\n=== Triplets after sort (Part III) ===");
        for (Triplet<Double, Double, Double> v : vecs) {
            System.out.println(v);
        }
    }

    private static String randomModule(Random r) {
        return String.format("%04d", r.nextInt(10000)); // 0000~9999
    }

    private static int randomTime(Random r) {
        return r.nextInt(10) + 1; // 1~10
    }
}
