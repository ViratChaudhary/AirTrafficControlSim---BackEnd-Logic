package towersim.ground;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import towersim.aircraft.Aircraft;
import towersim.aircraft.AircraftCharacteristics;
import towersim.aircraft.FreightAircraft;
import towersim.aircraft.PassengerAircraft;
import towersim.tasks.Task;
import towersim.tasks.TaskList;
import towersim.tasks.TaskType;
import towersim.util.NoSpaceException;

import java.util.ArrayList;
import java.util.List;

public class GateTest {

    private FreightAircraft freightAircraft;
    private PassengerAircraft passengerAircraft;
    private List<Task> tasks;
    private TaskList taskList;
    private Gate gate1;
    private Gate gate2;

    @Before
    public void setup() {

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
                "DEF456", AircraftCharacteristics.SIKORSKY_SKYCRANE,
                this.taskList, 1500, 5000);

        this.gate1 = new Gate(5);
        this.gate2 = new Gate(7);
    }

    @Test
    public void getGateNumberOfGateOneTest() {
        int expected = 5;
        assertEquals("the gate number is incorrect", expected, gate1.getGateNumber());
    }

    @Test
    public void getGateNumberOfGateTwoTest() {
        int expected = 7;
        assertEquals("the gate number is incorrect", expected, gate2.getGateNumber());
    }

    @Test
    public void parkAircraftUnoccupiedGateOneTest() {
        try {
            gate1.parkAircraft(passengerAircraft);
        } catch (NoSpaceException e) {}

        assertTrue("An Aircraft should be parked when the gate is unoccupied", gate1.isOccupied());
    }

    @Test
    public void parkAircraftUnoccupiedGateTwoTest() {
        try {
            gate2.parkAircraft(freightAircraft);
        } catch (NoSpaceException e) {}

        assertTrue("An Aircraft should be parked when the gate is unoccupied", gate2.isOccupied());
    }

    @Test
    public void parkAircraftOccupiedGateOneExceptionTest() {
        try {
            gate1.parkAircraft(passengerAircraft);
        } catch (NoSpaceException e) {}

        boolean exceptionThrown = false;

        try {
            gate1.parkAircraft(freightAircraft);
        } catch (NoSpaceException e) {
            exceptionThrown = true;
        }
        assertTrue("An Exception should be thrown when aircraft already parked", exceptionThrown);
    }

    @Test
    public void parkAircraftOccupiedGateTwoExceptionTest() {
        try {
            gate2.parkAircraft(freightAircraft);
        } catch (NoSpaceException e) {}

        boolean exceptionThrown = false;

        try {
            gate2.parkAircraft(passengerAircraft);
        } catch (NoSpaceException e) {
            exceptionThrown = true;
        }
        assertTrue("An Exception should be thrown when aircraft already parked", exceptionThrown);
    }

    @Test
    public void aircraftLeavesNoAircraftParkedTest() {
        gate1.aircraftLeaves();
        assertFalse("no aircraft parked in the gate after it leaves", gate1.isOccupied());
    }

    @Test
    public void aircraftLeavesAircraftParkedInGateOneTest() {
        try {
            gate1.parkAircraft(passengerAircraft);
        } catch (NoSpaceException e) {}

        gate1.aircraftLeaves();
        assertFalse("no aircraft parked in the gate after it leaves", gate1.isOccupied());
    }

    @Test
    public void aircraftLeavesAircraftParkedInGateTwoTest() {
        try {
            gate2.parkAircraft(freightAircraft);
        } catch (NoSpaceException e) {}

        gate2.aircraftLeaves();
        assertFalse("no aircraft parked in the gate after it leaves", gate2.isOccupied());
    }

    @Test
    public void isOccupiedNoAircraftOccupiedInGateOneTest() {
        assertFalse("the gate is not occupied and should return false", gate1.isOccupied());
    }

    @Test
    public void isOccupiedNoAircraftOccupiedInGateTwoTest() {
        assertFalse("the gate is not occupied and should return false", gate2.isOccupied());
    }

    @Test
    public void isOccupiedAircraftOccupiedInGateOneTest() {
        try {
            gate1.parkAircraft(passengerAircraft);
        } catch (NoSpaceException e) {}

        assertTrue("the gate is occupied and should return true", gate1.isOccupied());
    }

    @Test
    public void isOccupiedAircraftOccupiedInGateTwoTest() {
        try {
            gate2.parkAircraft(freightAircraft);
        } catch (NoSpaceException e) {}

        assertTrue("the gate is occupied and should return true", gate2.isOccupied());
    }

    @Test
    public void getAircraftAtGateNoAircraftParkedInGateOneTest() {
        Aircraft expected = null;
        assertEquals("getAircraftAtGate() should return null", expected, gate1.getAircraftAtGate());
    }

    @Test
    public void getAircraftAtGateNoAircraftParkedInGateTwoTest() {
        Aircraft expected = null;
        assertEquals("getAircraftAtGate() should return null", expected, gate2.getAircraftAtGate());
    }

    @Test
    public void getAircraftAtGateAircraftParkedInGateOneTest() {
        try {
            gate1.parkAircraft(passengerAircraft);
        } catch (NoSpaceException e) {}

        Aircraft expected = passengerAircraft;
        assertEquals("getAircraftAtGate() should return an aircraft", expected, gate1.getAircraftAtGate());
    }

    @Test
    public void getAircraftAtGateAircraftParkedInGateTwoTest() {
        try {
            gate2.parkAircraft(freightAircraft);
        } catch (NoSpaceException e) {}

        Aircraft expected = freightAircraft;
        assertEquals("getAircraftAtGate() should return an aircraft", expected, gate2.getAircraftAtGate());
    }

    @Test
    public void toStringNoAircraftParkedTest() {
        String expected = "Gate 5 [empty]";
        assertEquals("The toString() method is incorrect", expected, gate1.toString());
    }

    @Test
    public void toStringPassengerAircraftParkedInGateOneTest() {
        try {
            gate1.parkAircraft(passengerAircraft);
        } catch (NoSpaceException e) {}

        String expected = "Gate 5 [ABC123]";
        assertEquals("The toString() method is incorrect", expected, gate1.toString());
    }

    @Test
    public void toStringFreightAircraftParkedInGateTwoTest() {
        try {
            gate2.parkAircraft(freightAircraft);
        } catch (NoSpaceException e) {}

        String expected = "Gate 7 [DEF456]";
        assertEquals("The toString() method is incorrect", expected, gate2.toString());
    }
}
