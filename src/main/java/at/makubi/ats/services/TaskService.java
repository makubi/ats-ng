package at.makubi.ats.services;

import at.makubi.ats.Task;

import java.util.Collection;


public interface TaskService {

    void addTask(Task task);
    Collection<Task> getTasks();
}
