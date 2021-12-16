package towersim.tasks;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

public class TaskTest {

    private Task t1;
    private Task t2;

    @Before
    public void setup() {
        this.t1 = new Task(TaskType.LOAD, 50);
        this.t2 = new Task(TaskType.AWAY);
    }

    @Test
    public void getTypeTest1() {
        TaskType expected = TaskType.LOAD;
        assertEquals("Incorrect task type", t1.getType(), expected);
    }

    @Test
    public void getTypeTest2() {
        TaskType expected = TaskType.AWAY;
        assertEquals("Incorrect task type", t2.getType(), expected);
    }

    @Test
    public void getLoadPercentTest1() {
        int expected = 50;
        assertEquals("Incorrect Load Percentage", t1.getLoadPercent(), expected);
    }

    @Test
    public void getLoadPercentTest2() {
        int expected = 0;
        assertEquals("Incorrect Load Percentage", t2.getLoadPercent(), expected);
    }

    @Test
    public void toStringTest1() {
        String expected = "LOAD at 50%";
        assertEquals("Incorrect toString() output", t1.toString(), expected);
    }

    @Test
    public void toStringTest2() {
        String expected = "AWAY";
        assertEquals("Incorrect toString() output", t2.toString(), expected);
    }
}
