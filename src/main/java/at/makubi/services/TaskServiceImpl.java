package at.makubi.services;

import at.makubi.Task;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.*;

@Service
public class TaskServiceImpl implements TaskService {

    private final Collection<Task> tasks = new ArrayList<Task>();

    private final BlockingQueue<Task> queue = new LinkedBlockingQueue<Task>();

    @PostConstruct
    public void postConstruct() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        final Task task = queue.take();

                        task.setStatus(Task.Status.RUNNING);
                        try {
                            task.execute();
                            task.setStatus(Task.Status.FINISHED);
                        }
                        catch (Exception e) {
                            task.setStatus(Task.Status.ERROR);
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }

                }
            }
        });

        thread.start();
    }

    @Override
    public void addTask(Task task) {
        synchronized (task) {
            tasks.add(task);
        }
        queue.add(task);
    }

    @Override
    public Collection<Task> getTasks() {
        synchronized (tasks) {
            return tasks;
        }
    }
}
