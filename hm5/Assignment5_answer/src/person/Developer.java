package person;

import java.util.ArrayList;
import task.DevelopModule;
import task.Task;

public class Developer extends Person {
    public Developer(ArrayList<Task> jobQueue) { super(jobQueue); }

    @Override
    public void startTask(String moduleName, int time) {
        jobQueue.add(new DevelopModule(moduleName, time));
    }
}
