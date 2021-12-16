package towersim.tasks;

import java.util.List;

/** Represents a circular list of tasks for an aircraft to cycle through. */
public class TaskList {

    /** All tasks that the aircraft performs. */
    private List<Task> tasks;

    /** The current task the aircraft is performing */
    private int currentTaskPosition;

    /**
     * Creates a new TaskList with the given list of tasks.
     *
     * @param tasks list of tasks
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;

        // creates a index based counter to identify the current task for the aircraft
        this.currentTaskPosition = 0;
    }

    /**
     * Returns the current task in the list.
     *
     * @return current task
     */
    public Task getCurrentTask() {
        return tasks.get(currentTaskPosition);
    }

    /**
     * Returns the task in the list that comes after the current task.
     *
     * @return next task
     */
    public Task getNextTask() {
        // checks next position, if it exists then position is moved up
        if ((currentTaskPosition + 1) < tasks.size()) {
            return tasks.get(currentTaskPosition + 1);
        } else {
            // if the next position does not exist, the cycle is restarted from the first task
            // hence index 0 or first element
            return tasks.get(0);
        }
    }

    /**
     * Moves the reference to the current task forward by one in the circular task list.
     */
    public void moveToNextTask() {
        // if next position exists, then position is moved up otherwise reverted back to the
        // start to recycle the tasks
        if (currentTaskPosition + 1 < tasks.size()) {
            currentTaskPosition++;
        } else {
            currentTaskPosition = 0;
        }
    }

    /**
     * Returns the human-readable string representation of this task list.
     *
     * @return string representation of this task list
     */
    @Override
    public String toString() {
        String currentTask = getCurrentTask().toString();
        String currentPosition = String.valueOf(currentTaskPosition + 1);
        String finalTaskPosition = String.valueOf(tasks.size());

        // if current task is LOAD, the string will differ and will be LOAD at X% instead of
        // simply just the task name ie. AWAY or TAKEOFF
        return String.format("TaskList currently on %1$s [%2$s/%3$s]",
                currentTask, currentPosition, finalTaskPosition);
    }
}
