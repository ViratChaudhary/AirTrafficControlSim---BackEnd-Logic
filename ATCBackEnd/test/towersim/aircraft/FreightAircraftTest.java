package towersim.aircraft;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import towersim.tasks.Task;
import towersim.tasks.TaskList;
import towersim.tasks.TaskType;

import java.util.ArrayList;
import java.util.List;

public class FreightAircraftTest {
    private FreightAircraft freightAircraft;
    private List<Task> tasks;
    private TaskList taskList;

    @Before
    public void setup() {
        this.tasks = new ArrayList<Task>();

        this.tasks.add(new Task(TaskType.LOAD, 65));
        this.tasks.add(new Task(TaskType.TAKEOFF));

        this.taskList = new TaskList(this.tasks);

        this.freightAircraft = new FreightAircraft(
                "ABC123", AircraftCharacteristics.BOEING_747_8F,
                this.taskList,11000, 50);
    }

    @Test
    public void getFuelPercentRemainingTest() {
        int expected = 5;
        assertEquals("Incorrect fuel % remaining", expected, freightAircraft.getFuelPercentRemaining());
    }

    @Test
    public void getTotalWeightTest() {
        double expected = 205981;
        assertEquals(expected, freightAircraft.getTotalWeight(), 0.001);
    }

    @Test
    public void getLoadingTimeTest() {
        int expected = 3;
        assertEquals("Incorrect Loading Time", expected, freightAircraft.getLoadingTime());
    }

    @Test
    public void calculateOccupancyLevel() {
        int expected = 0;
        assertEquals("Incorrect occupancy percentage", expected, freightAircraft.calculateOccupancyLevel());
    }

    @Test
    public void toStringNoEmergencyTest() {
        String expected = "AIRPLANE ABC123 BOEING_747_8F LOAD";
        assertEquals("Incorrect toString() method", expected, freightAircraft.toString());
    }

    @Test
    public void NoEmergencyTest() {
        boolean expected = false;
        assertEquals("Incorrect emergency status", expected, freightAircraft.hasEmergency());
    }

    @Test
    public void EmergencyTest() {
        boolean expected = true;
        freightAircraft.declareEmergency();
        assertEquals("Incorrect emergency status", expected, freightAircraft.hasEmergency());
    }

    @Test
    public void EmergencyTest2() {
        boolean expected = false;
        freightAircraft.declareEmergency();
        freightAircraft.clearEmergency();
        assertEquals("Incorrect emergency status", expected, freightAircraft.hasEmergency());
    }

    @Test
    public void EmergencyTest3() {
        boolean expected = false;
        freightAircraft.clearEmergency();
        assertEquals("Incorrect emergency status", expected, freightAircraft.hasEmergency());
    }



}