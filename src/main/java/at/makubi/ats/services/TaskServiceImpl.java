package at.makubi.ats.services;

import at.makubi.ats.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.*;

@Service
public class TaskServiceImpl implements TaskService {

    private final static Logger LOG = LoggerFactory.getLogger(TaskService.class);

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
                            LOG.error("Unable to execute task", e);
                            task.setStatus(Task.Status.ERROR + ": " + e.getMessage());
                        }

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
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
