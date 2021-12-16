package towersim.control;

import towersim.aircraft.Aircraft;
import towersim.aircraft.AircraftType;
import towersim.ground.AirplaneTerminal;
import towersim.ground.Gate;
import towersim.ground.HelicopterTerminal;
import towersim.ground.Terminal;
import towersim.tasks.TaskType;
import towersim.util.NoSpaceException;
import towersim.util.NoSuitableGateException;
import towersim.util.Tickable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the control tower of an airport. The control tower is responsible for managing
 * the operations of the airport, including arrivals and departures in/out of the airport,
 * as well as aircraft that need to be loaded with cargo at gates in terminals.
 */
public class ControlTower implements Tickable {

    /** All aircraft that are part of the jurisdiction of this control tower. */
    private List<Aircraft> aircrafts;

    /** All terminals that are part of the jurisdiction of this control tower. */
    private List<Terminal> terminals;

    /**
     * Creates a new ControlTower.
     */
    public ControlTower() {
        this.aircrafts = new ArrayList<Aircraft>();
        this.terminals = new ArrayList<Terminal>();
    }

    /**
     * Adds the given terminal to the jurisdiction of this control tower.
     *
     * @param terminal terminal to add
     */
    public void addTerminal(Terminal terminal) {
        terminals.add(terminal);
    }

    /**
     * Returns a list of all terminals currently managed by this control tower.
     *
     * @return all terminals
     */
    public List<Terminal> getTerminals() {
        // return new array so that adding and/or removing terminals does not effect original list
        return new ArrayList<>(terminals);
    }

    /**
     * Adds the given aircraft to the jurisdiction of this control tower.
     *
     * @param aircraft aircraft to add
     * @throws NoSuitableGateException if there is no suitable gate for an aircraft
     * with a current task type of WAIT or LOAD
     */
    public void addAircraft(Aircraft aircraft) throws NoSuitableGateException {

        TaskType currentTask = aircraft.getTaskList().getCurrentTask().getType();
        // if aircraft is in load or wait task, then it finds and occupies a gate
        // before being added to the jurisdiction
        if (currentTask == TaskType.LOAD || currentTask == TaskType.WAIT) {
            try {
                Gate aircraftGate = findUnoccupiedGate(aircraft);
                aircraftGate.parkAircraft(aircraft);
                aircrafts.add(aircraft);
            } catch (NoSuitableGateException e) {
                throw new NoSuitableGateException();
            } catch (NoSpaceException e) {
                // this exception should not occur as aircraft is being parked to an unoccupied gate
                assert false;
            }
        } else {
            aircrafts.add(aircraft);
        }
    }

    /**
     * Returns a list of all aircraft currently managed by this control tower.
     *
     * @return all aircraft
     */
    public List<Aircraft> getAircraft() {
        // return new array so that adding and/or removing aircrafts does not effect original list
        return new ArrayList<Aircraft>(aircrafts);
    }

    /**
     * Attempts to find an unoccupied gate in a compatible terminal for the given aircraft.
     *
     * @param aircraft aircraft for which to find gate
     * @return gate for given aircraft if one exists
     * @throws NoSuitableGateException  if no suitable gate could be found
     */
    public Gate findUnoccupiedGate(Aircraft aircraft) throws NoSuitableGateException {
        AircraftType aircraftType = aircraft.getCharacteristics().type;
        Gate unoccupiedGate;

        // every terminal is checked for a suitable gate for the aircraft to be parked at
        for (Terminal terminal : getTerminals()) {
            // checks for airplane suitable gates
            if (terminal instanceof AirplaneTerminal && aircraftType == AircraftType.AIRPLANE) {
                try {
                    unoccupiedGate = terminal.findUnoccupiedGate();
                    return unoccupiedGate;
                } catch (NoSuitableGateException e) {
                    // if exception occurs, then it will move to next terminal in getTerminals()
                }
                // checks for helicopter suitable gates
            } else if (terminal instanceof HelicopterTerminal && aircraftType
                    == AircraftType.HELICOPTER) {
                try {
                    unoccupiedGate = terminal.findUnoccupiedGate();
                    return unoccupiedGate;
                } catch (NoSuitableGateException e) {
                    // if exception occurs, then it will move to next terminal in getTerminals()
                }
            }
        }
        throw new NoSuitableGateException();
    }

    /**
     * Finds the gate where the given aircraft is parked,
     * and returns null if the aircraft is not parked at any gate in any terminal.
     *
     * @param aircraft aircraft whose gate to find
     * @return gate occupied by the given aircraft; or null if none exists
     */
    public Gate findGateOfAircraft(Aircraft aircraft) {
        // every gate in every terminal is checked to identify the gate of aircraft
        for (Terminal terminal : getTerminals()) {
            for (Gate gate : terminal.getGates()) {
                if (gate.getAircraftAtGate() == aircraft) {
                    return gate;
                }
            }
        }
        return null;
    }

    /**
     * Advances the simulation by one tick.
     */
    public void tick() {
        for (Aircraft aircraft : getAircraft()) {
            aircraft.tick();
        }
    }
}
