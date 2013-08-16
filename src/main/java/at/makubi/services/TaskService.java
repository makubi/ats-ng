package at.makubi.services;

import at.makubi.Task;

import java.util.Collection;

public interface TaskService {

    void addTask(Task task);
    Collection<Task> getTasks();
}
