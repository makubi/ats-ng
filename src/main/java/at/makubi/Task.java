package at.makubi;

public abstract class Task {

    private final String name;
    private Status status = Status.WAITING;

    public Task(String name) {
        this.name = name;
    }

    public enum Status {
        WAITING, RUNNING, FINISHED, ERROR
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        synchronized (status) {
            return status;
        }
    }

    public void setStatus(Status status) {
        synchronized (this.status) {
            this.status = status;
        }
    }

    public abstract void execute();
}
