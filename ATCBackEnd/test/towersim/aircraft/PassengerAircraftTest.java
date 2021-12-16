package towersim.aircraft;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import towersim.tasks.Task;
import towersim.tasks.TaskList;
import towersim.tasks.TaskType;

import java.util.ArrayList;
import java.util.List;


public class PassengerAircraftTest {
    private PassengerAircraft passengerAircraft;
    private List<Task> tasks;
    private TaskList taskList;

    @Before
    public void setup() {
        this.tasks = new ArrayList<Task>();

        this.tasks.add(new Task(TaskType.LOAD, 65));
        this.tasks.add(new Task(TaskType.TAKEOFF));

        this.taskList = new TaskList(this.tasks);

        this.passengerAircraft = new PassengerAircraft(
                "ABC123", AircraftCharacteristics.AIRBUS_A320,
                this.taskList, 10000, 50);
    }

    @Test
    public void getTotalWeightTest() {
        double expected = 55100;
        assertEquals(expected, passengerAircraft.getTotalWeight(), 0.001);
    }

    @Test
    public void getLoadingTimeTest() {
        int expected = 2;
        assertEquals("Incorrect loading time", expected, passengerAircraft.getLoadingTime());
    }

    @Test
    public void calculateOccupancyLevel() {
        int expected = 33;
        assertEquals("Incorrect loading time", expected, passengerAircraft.calculateOccupancyLevel());
    }

    @Test
    public void toStringEmergencyTest() {
        String expected = "AIRPLANE ABC123 AIRBUS_A320 LOAD (EMERGENCY)";
        passengerAircraft.declareEmergency();
        assertEquals("Incorrect toString() method", expected, passengerAircraft.toString());
    }

    @Test
    public void NoEmergencyTest() {
        boolean expected = false;
        assertEquals("Incorrect emergency status", expected, passengerAircraft.hasEmergency());
    }

    @Test
    public void EmergencyTest() {
        boolean expected = true;
        passengerAircraft.declareEmergency();
        assertEquals("Incorrect emergency status", expected, passengerAircraft.hasEmergency());
    }

    @Test
    public void EmergencyTest2() {
        boolean expected = false;
        passengerAircraft.declareEmergency();
        passengerAircraft.clearEmergency();
        assertEquals("Incorrect emergency status", expected, passengerAircraft.hasEmergency());
    }

    @Test
    public void EmergencyTest3() {
        boolean expected = false;
        passengerAircraft.clearEmergency();
        assertEquals("Incorrect emergency status", expected, passengerAircraft.hasEmergency());
    }
}
