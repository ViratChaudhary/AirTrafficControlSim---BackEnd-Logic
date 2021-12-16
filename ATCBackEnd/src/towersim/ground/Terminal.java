package towersim.ground;

import towersim.util.EmergencyState;
import towersim.util.NoSpaceException;
import towersim.util.NoSuitableGateException;
import towersim.util.OccupancyLevel;

import java.util.ArrayList;
import java.util.List;

/** Represents an airport terminal building, containing several aircraft gates. */
public abstract class Terminal implements EmergencyState, OccupancyLevel {

    /** maximum possible number of gates allowed at a single terminal */
    public static final int MAX_NUM_GATES = 6;

    /** The terminal's terminal number. */
    private int terminalNumber;

    /** The terminal's current state of emergency. */
    private boolean emergencyState;

    /** All gates that are present within this terminal. */
    private List<Gate> gates;

    /**
     * Creates a new Terminal with the given unique terminal number.
     *
     * @param terminalNumber identifying number of this terminal
     */
    protected Terminal(int terminalNumber) {
        this.terminalNumber = terminalNumber;
        this.emergencyState = false;
        this.gates = new ArrayList<Gate>();
    }

    /**
     * Returns this terminal's terminal number.
     *
     * @return terminal number
     */
    public int getTerminalNumber() {
        return terminalNumber;
    }

    /**
     * Adds a gate to the terminal.
     *
     * @param gate gate to add to terminal
     * @throws NoSpaceException if there is no space at the terminal for the new gate
     */
    public void addGate(Gate gate) throws NoSpaceException {
        if (gates.size() < MAX_NUM_GATES) {
            gates.add(gate);
        } else {
            throw new NoSpaceException();
        }
    }

    /**
     * Returns a list of all gates in the terminal.
     *
     * @return list of terminal's gates
     */
    public List<Gate> getGates() {
        return new ArrayList<>(gates);
    }

    /**
     * Finds and returns the first non-occupied gate in this terminal.
     *
     * @return first non-occupied gate in this terminal
     * @throws NoSuitableGateException if all gates in this terminal are occupied
     */
    public Gate findUnoccupiedGate() throws NoSuitableGateException {
        for (Gate gate : getGates()) {
            if (!gate.isOccupied()) {
                return gate;
            }
        }
        throw new NoSuitableGateException();
    }

    /**
     * Declares a state of emergency.
     */
    public void declareEmergency() {
        emergencyState = true;
    }

    /**
     * Clears any active state of emergency.
     */
    public void clearEmergency() {
        emergencyState = false;
    }

    /**
     * Returns whether or not a state of emergency is currently active.
     *
     * @return true if in emergency; false otherwise
     */
    public boolean hasEmergency() {
        return emergencyState;
    }

    /**
     * Returns the ratio of occupied gates to total gates as a percentage from 0 to 100.
     *
     * @return percentage of occupied gates in this terminal, 0 to 100
     */
    public int calculateOccupancyLevel() {
        if (getGates().size() == 0) {
            return 0;
        }

        // counts all occupied gates in the terminal
        int occupiedGates = 0;
        for (Gate gate : getGates()) {
            if (gate.isOccupied()) {
                occupiedGates++;
            }
        }
        return (int) Math.round(100 * (occupiedGates / (double) getGates().size()));
    }

    /**
     * Returns the human-readable string representation of this terminal.
     *
     * @return string representation of this terminal
     */
    @Override
    public String toString() {
        String terminalType = getClass().getSimpleName();
        String terminalNum = String.valueOf(getTerminalNumber());
        String numGates = String.valueOf(getGates().size());
        String emergency = "(EMERGENCY)";

        // if terminal has an emergency, then emergency status is added to the string
        if (hasEmergency()) {
            return String.format("%1$s %2$s, %3$s gates %4$s",
                    terminalType, terminalNum, numGates, emergency);
        } else {
            return String.format("%1$s %2$s, %3$s gates", terminalType, terminalNum, numGates);
        }
    }
}
