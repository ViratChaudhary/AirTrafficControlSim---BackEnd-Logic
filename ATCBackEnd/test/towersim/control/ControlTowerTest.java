package towersim.control;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import towersim.aircraft.Aircraft;
import towersim.aircraft.AircraftCharacteristics;
import towersim.aircraft.FreightAircraft;
import towersim.aircraft.PassengerAircraft;
import towersim.ground.AirplaneTerminal;
import towersim.ground.Gate;
import towersim.ground.HelicopterTerminal;
import towersim.ground.Terminal;
import towersim.tasks.Task;
import towersim.tasks.TaskList;
import towersim.tasks.TaskType;
import towersim.util.NoSpaceException;
import towersim.util.NoSuitableGateException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ControlTowerTest {
    private ControlTower controlTower;

    private FreightAircraft freightAircraft;
    private PassengerAircraft passengerAircraft;
    private PassengerAircraft helicopter1;
    private FreightAircraft helicopter2;
    private List<Task> tasks;
    private TaskList taskList;

    private Terminal airplaneTerminal;
    private Terminal airplaneTerminal2;
    private Terminal helicopterTerminal;

    private Gate gate1;
    private Gate gate2;
    private Gate gate3;
    private Gate gate4;
    private Gate gate5;
    private Gate gate6;
    private Gate gate7;

    @Before
    public void setup() {
        this.controlTower = new ControlTower();

        this.airplaneTerminal = new AirplaneTerminal(1);
        this.airplaneTerminal2 = new AirplaneTerminal(3);
        this.helicopterTerminal = new HelicopterTerminal(2);

        this.gate1 = new Gate(1);
        this.gate2 = new Gate(2);
        this.gate3 = new Gate(3);
        this.gate4 = new Gate(4);
        this.gate5 = new Gate(5);
        this.gate6 = new Gate(6);
        this.gate7 = new Gate(7);

        this.tasks = new ArrayList<Task>();

        this.tasks.add(new Task(TaskType.WAIT));
        this.tasks.add(new Task(TaskType.LOAD));
        this.tasks.add(new Task(TaskType.TAKEOFF));

        this.taskList = new TaskList(this.tasks);

        this.passengerAircraft = new PassengerAircraft(
                "ABC123", AircraftCharacteristics.AIRBUS_A320,
                this.taskList,10000, 50);

        this.freightAircraft = new FreightAircraft(
                "DEF456", AircraftCharacteristics.BOEING_747_8F,
                this.taskList,11000, 50);

        this.helicopter1 = new PassengerAircraft(
                "GHI123", AircraftCharacteristics.ROBINSON_R44,
                this.taskList, 30, 3);

        this.helicopter2 = new FreightAircraft(
                "JKL456", AircraftCharacteristics.SIKORSKY_SKYCRANE,
                this.taskList, 1500, 5000);
    }

    @Test
    public void getTerminalTest() {
        ArrayList<Terminal> expected = new ArrayList<Terminal>();
        expected.add(airplaneTerminal);
        expected.add(helicopterTerminal);

        this.controlTower.addTerminal(airplaneTerminal);
        this.controlTower.addTerminal(helicopterTerminal);

        assertEquals("Incorrect getTerminalMethod()", controlTower.getTerminals(), expected);
    }

    @Test
    public void findUnoccupiedGateAirplaneTerminal() {
        Gate expected = gate1;
        Gate gate = null;

        try {
            airplaneTerminal.addGate(gate1);
            controlTower.addTerminal(airplaneTerminal);
        } catch (NoSpaceException e) {}

        try {
            gate = controlTower.findUnoccupiedGate(passengerAircraft);
        } catch (NoSuitableGateException e) {}

        assertEquals("Incorrect Gate found", expected, gate);
    }

    @Test
    public void findUnoccupiedGateAirplaneTerminal2() {
        Gate expected = gate2;
        Gate gate = null;

        try {
            airplaneTerminal.addGate(gate1);
            airplaneTerminal.addGate(gate2);
            gate1.parkAircraft(passengerAircraft);
            controlTower.addTerminal(airplaneTerminal);
        } catch (NoSpaceException e) {}

        try {
            gate = controlTower.findUnoccupiedGate(freightAircraft);
        } catch (NoSuitableGateException e) {}

        assertEquals("Incorrect Gate found", expected, gate);
    }

    @Test
    public void findUnoccupiedGateAirplaneTerminalException() {
        boolean expected = false;

        try {
            airplaneTerminal.addGate(gate1);
            gate1.parkAircraft(passengerAircraft);
            controlTower.addTerminal(airplaneTerminal);
        } catch (NoSpaceException e) {}

        try {
            controlTower.findUnoccupiedGate(freightAircraft);
        } catch (NoSuitableGateException e) {
            expected = true;
        }

        assertTrue(expected);
    }

    @Test
    public void findUnoccupiedGateHelicopterTerminal() {
        Gate expected = gate1;
        Gate gate = null;

        try {
            helicopterTerminal.addGate(gate1);
            controlTower.addTerminal(helicopterTerminal);
        } catch (NoSpaceException e) {}

        try {
            gate = controlTower.findUnoccupiedGate(helicopter1);
        } catch (NoSuitableGateException e) {}

        assertEquals("Incorrect Gate found", expected, gate);
    }

    @Test
    public void findUnoccupiedGateHelicopterTerminal2() {
        Gate expected = gate2;
        Gate gate = null;

        try {
            helicopterTerminal.addGate(gate1);
            helicopterTerminal.addGate(gate2);
            gate1.parkAircraft(helicopter1);
            controlTower.addTerminal(helicopterTerminal);
        } catch (NoSpaceException e) {}

        try {
            gate = controlTower.findUnoccupiedGate(helicopter2);
        } catch (NoSuitableGateException e) {}

        assertEquals("Incorrect Gate found", expected, gate);
    }

    @Test
    public void findUnoccupiedGateHelicopterTerminalException() {
        boolean expected = false;

        try {
            helicopterTerminal.addGate(gate1);
            gate1.parkAircraft(helicopter1);
            controlTower.addTerminal(helicopterTerminal);
        } catch (NoSpaceException e) {}

        try {
            controlTower.findUnoccupiedGate(helicopter2);
        } catch (NoSuitableGateException e) {
            expected = true;
        }

        assertTrue(expected);
    }

    @Test
    public void findUnoccupiedGateHelicopterTerminalTest4() {
        Gate expected = gate3;
        Gate gate = null;

        try {
            airplaneTerminal.addGate(gate1);
            helicopterTerminal.addGate(gate2);
            helicopterTerminal.addGate(gate3);
            gate2.parkAircraft(helicopter1);
            controlTower.addTerminal(airplaneTerminal);
            controlTower.addTerminal(helicopterTerminal);
        } catch (NoSpaceException e) {}

        try {
            gate = controlTower.findUnoccupiedGate(helicopter2);
        } catch (NoSuitableGateException e) {}

        assertEquals("Incorrect Gate found", expected, gate);
    }

    @Test
    public void findUnoccupiedGateMultipleTerminalsTest() {
        Gate expected = gate2;
        Gate gate = null;

        try {
            airplaneTerminal.addGate(gate1);
            airplaneTerminal2.addGate(gate2);
            gate1.parkAircraft(passengerAircraft);
            controlTower.addTerminal(airplaneTerminal);
            controlTower.addTerminal(airplaneTerminal2);
        } catch (NoSpaceException e) {}

        try {
            gate = controlTower.findUnoccupiedGate(freightAircraft);
        } catch (NoSuitableGateException e) {}

        assertEquals("Incorrect Gate found", expected, gate);
    }

    @Test
    public void findUnoccupiedGateMultipleTerminalExceptionTest() {
        boolean expected = false;

        controlTower.addTerminal(airplaneTerminal);
        controlTower.addTerminal(airplaneTerminal2);

        try {
            controlTower.findUnoccupiedGate(freightAircraft);
        } catch (NoSuitableGateException e) {
            expected = true;
        }

        assertTrue(expected);
    }

    @Test
    public void addAircraftTest() {
        ArrayList<Aircraft> expected = new ArrayList<Aircraft>();
        expected.add(passengerAircraft);

        try {
            airplaneTerminal.addGate(gate1);
            controlTower.addTerminal(airplaneTerminal);
        } catch (NoSpaceException e) {}

        try {
            controlTower.addAircraft(passengerAircraft);
        } catch (NoSuitableGateException e) {}

        assertEquals("Incorrect addAircraftMethod", expected, controlTower.getAircraft());
    }

    @Test
    public void findGateOfAircraftTest() {
        try {
            gate1.parkAircraft(passengerAircraft);
            airplaneTerminal.addGate(gate1);
            airplaneTerminal.addGate(gate2);
            controlTower.addTerminal(airplaneTerminal);
        } catch (NoSpaceException e) {}

        assertEquals("Incorrect gate of aircraft", gate1, controlTower.findGateOfAircraft(passengerAircraft));
    }
}
