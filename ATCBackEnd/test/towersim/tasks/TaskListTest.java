package towersim.tasks;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TaskListTest {

    private List<Task> tasks;
    private List<Task> tasks2;
    private TaskList taskList1;
    private TaskList taskList2;
    private Task t1;
    private Task t2;
    private Task t3;
    private Task t4;
    private Task t5;
    private Task t6;
    private Task t7;

    @Before
    public void setup() {

        this.t1 = new Task(TaskType.AWAY);
        this.t2 = new Task(TaskType.LAND);
        this.t3 = new Task(TaskType.LOAD);
        this.t4 = new Task(TaskType.WAIT);
        this.t5 = new Task(TaskType.TAKEOFF);

        this.t6 = new Task(TaskType.LOAD);
        this.t7 = new Task(TaskType.LOAD, 35);

        this.tasks = new ArrayList<Task>();
        this.tasks2 = new ArrayList<Task>();

        this.tasks.add(this.t1);
        this.tasks.add(this.t2);
        this.tasks.add(this.t3);
        this.tasks.add(this.t4);
        this.tasks.add(this.t5);

        this.tasks2.add(this.t6);
        this.tasks2.add(this.t7);

        this.taskList1 = new TaskList(this.tasks);
        this.taskList2 = new TaskList(this.tasks2);
    }

    @Test
    public void getNextTaskOnFirstTaskTest() {
        Task expected = this.t2;
        assertEquals("The next task is incorrect", expected, taskList1.getNextTask());
    }

    @Test
    public void getNextTaskOnThirdTaskTest() {
        Task expected = this.t4;

        this.taskList1.moveToNextTask();
        this.taskList1.moveToNextTask();

        assertEquals("The next task is incorrect", expected, taskList1.getNextTask());
    }

    @Test
    public void getNextTaskOnFinalTaskTest() {
        Task expected = this.t1;

        this.taskList1.moveToNextTask();
        this.taskList1.moveToNextTask();
        this.taskList1.moveToNextTask();
        this.taskList1.moveToNextTask();

        assertEquals("The next task is incorrect", expected, taskList1.getNextTask());
    }

    @Test
    public void moveToNextTaskFromFirstTaskTest() {
        Task expected = this.t2;
        taskList1.moveToNextTask();

        assertEquals("The next task is incorrect", expected, taskList1.getCurrentTask());
    }

    @Test
    public void moveToNextTaskFromThirdTaskTest() {
        Task expected = this.t4;

        this.taskList1.moveToNextTask();
        this.taskList1.moveToNextTask();
        this.taskList1.moveToNextTask();

        assertEquals("The current task is incorrect", expected, taskList1.getCurrentTask());
    }

    @Test
    public void moveToNextTaskFromFinalTaskTest() {
        Task expected = this.t1;

        this.taskList1.moveToNextTask();
        this.taskList1.moveToNextTask();
        this.taskList1.moveToNextTask();
        this.taskList1.moveToNextTask();
        this.taskList1.moveToNextTask();

        assertEquals("The current task is incorrect", expected, taskList1.getCurrentTask());
    }

    @Test
    public void toStringLoadTaskNoPercentageTest() {
        String expected = "TaskList currently on LOAD at 0% [1/2]";
        assertEquals("The toString is incorrect", expected, taskList2.toString());
    }

    @Test
    public void toStringLoadTaskPercentageTest() {
        String expected = "TaskList currently on LOAD at 35% [2/2]";

        taskList2.moveToNextTask();
        assertEquals("The toString is incorrect", expected, taskList2.toString());
    }

    @Test
    public void toStringAwayTaskTest() {
        String expected = "TaskList currently on AWAY [1/5]";
        assertEquals("The toString is incorrect", expected, taskList1.toString());

    }

    @Test
    public void toStringLandTaskTest() {
        String expected = "TaskList currently on LAND [2/5]";

        taskList1.moveToNextTask();
        assertEquals("The toString is incorrect", expected, taskList1.toString());

    }

    @Test
    public void toStringWaitTaskTest() {
        String expected = "TaskList currently on WAIT [4/5]";

        taskList1.moveToNextTask();
        taskList1.moveToNextTask();
        taskList1.moveToNextTask();

        assertEquals("The toString is incorrect", expected, taskList1.toString());

    }

    @Test
    public void toStringTakeoffTaskTest() {
        String expected = "TaskList currently on TAKEOFF [5/5]";

        taskList1.moveToNextTask();
        taskList1.moveToNextTask();
        taskList1.moveToNextTask();
        taskList1.moveToNextTask();

        assertEquals("The toString is incorrect", expected, taskList1.toString());

    }
}
