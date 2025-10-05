package person;

import java.util.ArrayList;
import task.Task;
import task.TestModule;

public class Tester extends Person {
    public Tester(ArrayList<Task> jobQueue) { super(jobQueue); }

    @Override
    public void startTask(String moduleName, int time) {
        jobQueue.add(new TestModule(moduleName, time));
    }
}
