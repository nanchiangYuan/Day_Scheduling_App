/*
 * Object for a single task, contains the name of the task, estimated time to finish, and priority
 */
public class Task {

    private String name;
    private Time estTime;
    private int priority;

    // Constructor
    public Task(String name, Time estTime, int priority) {
        this.name = name;
        this.estTime = estTime;
        this.priority = priority;
    }

    public String getName() {
        return this.name;
    }

    public Time getEstTime() {
        return this.estTime;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setEstTime(Time estTime) {
        this.estTime = estTime;
    }

    public String toString() {
        return "Task: " + String.format("%-20s", name) + "Est time: " + String.format("%-8s", estTime) +
                "Priority: " + String.format("%-2s", priority);
    }
}
