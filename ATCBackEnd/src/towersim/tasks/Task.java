package towersim.tasks;

/**
 * Represents a task currently assigned to an aircraft.
 * Tasks relate to an aircraft's movement and ground operations.
 */
public class Task {

    /** The Task's type. */
    private TaskType type;

    /** The Task's load percentage. */
    private int loadPercent;

    /**
     * Creates a new Task of the given task type.
     *
     * @param type type of task
     */
    public Task(TaskType type) {
        this.type = type;

        // sets load percent to 0 in the case LOAD type Task was created without load percentage
        this.loadPercent = 0;
    }

    /**
     * Creates a new Task of the given task type and stores the given load percentage in the task.
     *
     * @param type        type of task
     * @param loadPercent percentage of maximum capacity to load
     */
    public Task(TaskType type, int loadPercent) {
        this(type);
        this.loadPercent = loadPercent;
    }

    /**
     * Returns the type of this task.
     *
     * @return task type
     */
    public TaskType getType() {
        return type;
    }

    /**
     * Returns the load percentage specified when constructing the task, or 0 if none was specified.
     *
     * @return task load percentage
     */
    public int getLoadPercent() {
        return loadPercent;
    }

    /**
     * Returns the human-readable string representation of this task.
     *
     * @return string representation of this task
     */
    @Override
    public String toString() {
        String loadPercent = String.valueOf(getLoadPercent());
        String taskType = getType().toString();

        // if the task is LOAD, then the load percent is also added
        if (getType() == TaskType.LOAD) {
            return "LOAD at " + loadPercent + "%";
        } else {
            return String.format("%1$s", taskType);
        }
    }
}
