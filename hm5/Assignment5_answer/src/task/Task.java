package task;

public abstract class Task implements Comparable<Task> {
    protected String module;
    protected int time;

    public Task(String module, int time) {
        this.module = module;
        this.time = time;
    }

    public String getModule() { return module; }
    public int getTime() { return time; }

    public abstract void performTask();

    @Override
    public int compareTo(Task other) {
        return Integer.compare(this.time, other.time);
    }

    @Override
    public String toString() {
        return String.format("%s{module='%s', time=%d}",
                getClass().getSimpleName(), module, time);
    }
}
