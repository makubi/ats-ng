package at.makubi;

import java.io.IOException;

public abstract class Task {

    private final String name;
    private String message = Status.WAITING.toString();

    public Task(String name) {
        this.name = name;
    }

    public enum Status {
        WAITING, RUNNING, FINISHED, ERROR
    }

    public String getName() {
        return name;
    }

    public void setStatus(Status status) {
        synchronized (this.message) {
            this.message = status.toString();
        }
    }

    public String getStatus() {
        synchronized (message) {
            return message;
        }
    }

    public void setStatus(String message) {
        synchronized (this.message) {
            this.message = message;
        }
    }

    public abstract void execute() throws Exception;
}
