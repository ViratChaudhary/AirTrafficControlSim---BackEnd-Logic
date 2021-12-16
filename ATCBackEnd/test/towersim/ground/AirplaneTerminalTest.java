package towersim.ground;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import towersim.aircraft.AircraftCharacteristics;
import towersim.aircraft.FreightAircraft;
import towersim.aircraft.PassengerAircraft;
import towersim.tasks.Task;
import towersim.tasks.TaskList;
import towersim.tasks.TaskType;
import towersim.util.NoSpaceException;
import towersim.util.NoSuitableGateException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AirplaneTerminalTest {

    private AirplaneTerminal airplaneTerminal;
    private Gate gate1;
    private Gate gate2;
    private Gate gate3;
    private Gate gate4;
    private Gate gate5;
    private Gate gate6;
    private Gate gate7;

    private PassengerAircraft passengerAircraft;
    private FreightAircraft freightAircraft;
    private List<Task> tasks;
    private TaskList taskList;

    @Before
    public void setup() {
        this.airplaneTerminal = new AirplaneTerminal(5);
        this.gate1 = new Gate(1);
        this.gate2 = new Gate(2);
        this.gate3 = new Gate(3);
        this.gate4 = new Gate(4);
        this.gate5 = new Gate(5);
        this.gate6 = new Gate(6);
        this.gate7 = new Gate(7);

        this.tasks = new ArrayList<Task>();

        this.tasks.add(new Task(TaskType.AWAY));
        this.tasks.add(new Task(TaskType.LAND));
        this.tasks.add(new Task(TaskType.LOAD));
        this.tasks.add(new Task(TaskType.WAIT));
        this.tasks.add(new Task(TaskType.TAKEOFF));

        this.taskList = new TaskList(this.tasks);

        this.passengerAircraft = new PassengerAircraft(
                "ABC123", AircraftCharacteristics.AIRBUS_A320,
                this.taskList,10000, 50);

        this.freightAircraft = new FreightAircraft(
                "DEF456", AircraftCharacteristics.BOEING_747_8F,
                this.taskList,10000, 50);
    }

    @Test
    public void getTerminalNumberTest() {
        int expected = 5;
        assertEquals("Incorrect terminal number", airplaneTerminal.getTerminalNumber(), expected);
    }

    @Test
    public void addGateValidTest() {
        List<Gate> expected = new ArrayList<Gate>();
        expected.add(gate1);
        expected.add(gate2);

        try {
            airplaneTerminal.addGate(gate1);
            airplaneTerminal.addGate(gate2);
        } catch (NoSpaceException e) {}

        assertEquals("Incorrect gates added", airplaneTerminal.getGates(), expected);
    }

    @Test
    public void addGateMaxGatesTest() {
        List<Gate> expected = new ArrayList<Gate>();
        expected.add(gate1);
        expected.add(gate2);
        expected.add(gate3);
        expected.add(gate4);
        expected.add(gate5);
        expected.add(gate6);

        try {
            airplaneTerminal.addGate(gate1);
            airplaneTerminal.addGate(gate2);
            airplaneTerminal.addGate(gate3);
            airplaneTerminal.addGate(gate4);
            airplaneTerminal.addGate(gate5);
            airplaneTerminal.addGate(gate6);
        } catch (NoSpaceException e) {}

        assertEquals("Incorrect gates added", airplaneTerminal.getGates(), expected);
    }

    @Test
    public void addGateExceptionTest() {
        boolean expected = false;

        try {
            airplaneTerminal.addGate(gate1);
            airplaneTerminal.addGate(gate2);
            airplaneTerminal.addGate(gate3);
            airplaneTerminal.addGate(gate4);
            airplaneTerminal.addGate(gate5);
            airplaneTerminal.addGate(gate6);
            airplaneTerminal.addGate(gate7);
        } catch (NoSpaceException e) {
            expected = true;
        }

        assertTrue("Exception should be thrown as maximum gates added", expected);
    }

    @Test
    public void findUnoccupiedGateTest() {
        Gate expected = gate1;
        Gate gate = null;

        try {
            airplaneTerminal.addGate(gate1);
            airplaneTerminal.addGate(gate2);
        } catch (NoSpaceException e) {}

        try {
            gate = airplaneTerminal.findUnoccupiedGate();
        } catch (NoSuitableGateException e) {}

        assertEquals("Incorrect gate found", expected, gate);
    }

    @Test
    public void findUnoccupiedGateTest2() {
        Gate expected = gate2;
        Gate gate = null;

        try {
            airplaneTerminal.addGate(gate1);
            airplaneTerminal.addGate(gate2);
            airplaneTerminal.addGate(gate3);

            gate1.parkAircraft(passengerAircraft);
        } catch (NoSpaceException e) {}

        try {
            gate = airplaneTerminal.findUnoccupiedGate();
        } catch (NoSuitableGateException e) {}

        assertEquals("Incorrect gate found", expected, gate);
    }

    @Test
    public void findUnoccupiedGateExceptionTest3() {
        boolean expected = false;

        try {
            airplaneTerminal.addGate(gate1);
            gate1.parkAircraft(passengerAircraft);
        } catch (NoSpaceException e) {}

        try {
            airplaneTerminal.findUnoccupiedGate();
        } catch (NoSuitableGateException e) {
            expected = true;
        }

        assertTrue("Exception should be thrown", expected);
    }

    @Test
    public void hasEmergencyNoEmergency() {
        assertFalse(airplaneTerminal.hasEmergency());
    }

    @Test
    public void hasEmergencyTrueEmergencyTest() {
        airplaneTerminal.declareEmergency();
        assertTrue(airplaneTerminal.hasEmergency());
    }

    @Test
    public void hasEmergencyClearedEmergencyTest() {
        airplaneTerminal.declareEmergency();
        airplaneTerminal.clearEmergency();
        assertFalse(airplaneTerminal.hasEmergency());
    }

    @Test
    public void calculateOccupancyLevelNoElementsTest() {
        int expected = 0;
        assertEquals("Occupancy is 0%", airplaneTerminal.calculateOccupancyLevel(), expected);
    }

    @Test
    public void calculateOccupancyLevelTest1() {
        try {
            airplaneTerminal.addGate(gate1);
            airplaneTerminal.addGate(gate2);
            airplaneTerminal.addGate(gate3);
            gate1.parkAircraft(passengerAircraft);
            gate2.parkAircraft(freightAircraft);
        } catch (NoSpaceException e) {}

        int expected = 67;
        assertEquals("Incorrect occupancy level", airplaneTerminal.calculateOccupancyLevel(), expected);
    }

    @Test
    public void toStringEmergencyTest() {
        String expected = "AirplaneTerminal 5, 3 gates (EMERGENCY)";
        try {
            airplaneTerminal.addGate(gate1);
            airplaneTerminal.addGate(gate2);
            airplaneTerminal.addGate(gate3);
            airplaneTerminal.declareEmergency();
        } catch (NoSpaceException e) {}

        assertEquals("Incorrectly formatted string", airplaneTerminal.toString(), expected);
    }

    @Test
    public void toStringNoEmergencyTest() {
        String expected = "AirplaneTerminal 5, 3 gates";
        try {
            airplaneTerminal.addGate(gate1);
            airplaneTerminal.addGate(gate2);
            airplaneTerminal.addGate(gate3);
        } catch (NoSpaceException e) {}

        assertEquals("Incorrectly formatted string", airplaneTerminal.toString(), expected);
    }
}















