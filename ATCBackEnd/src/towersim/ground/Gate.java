package towersim.ground;

import towersim.aircraft.Aircraft;
import towersim.util.NoSpaceException;

import java.util.ArrayList;
import java.util.List;

/** Represents an aircraft gate with facilities for a single aircraft to be parked. */
public class Gate {

    /** The Gate's gate number. */
    private int gateNumber;

    /** The Gate's occupant. */
    private List<Aircraft> gate;

    /**
     * Creates a new Gate with the given unique gate number.
     *
     * @param gateNumber identifying number of this gate
     */
    public Gate(int gateNumber) {
        this.gateNumber = gateNumber;
        this.gate = new ArrayList<Aircraft>();
    }

    /**
     * Returns this gate's gate number.
     *
     * @return gate number
     */
    public int getGateNumber() {
        return gateNumber;
    }

    /**
     * Parks the given aircraft at this gate, so that the gate becomes occupied.
     *
     * @param aircraft aircraft to park at gate
     * @throws NoSpaceException if the gate is already occupied by an aircraft
     */
    public void parkAircraft(Aircraft aircraft) throws NoSpaceException {
        if (isOccupied()) {
            throw new NoSpaceException();
        } else {
            gate.add(aircraft);
        }
    }

    /**
     * Removes the currently parked aircraft from the gate.
     */
    public void aircraftLeaves() {
        gate.clear();
    }

    /**
     * Returns whether an aircraft is currently parked or not
     *
     * @return true if an aircraft parked; false otherwise
     */
    public boolean isOccupied() {
        // since each gate can only park one aircraft at a time
        // the gate list can only hold upto 1 element
        return gate.size() >= 1;
    }

    /**
     * Returns the aircraft currently parked at the gate, or null if there is no aircraft parked.
     *
     * @return currently parked aircraft
     */
    public Aircraft getAircraftAtGate() {
        if (isOccupied()) {
            // gets the 0th index or first element since gate list can only hold one aircraft
            return gate.get(0);
        }
        return null;
    }

    /**
     * Returns the human-readable string representation of this gate.
     *
     * @return currently parked aircraft
     */
    @Override
    public String toString() {
        String gateNumber = String.valueOf(getGateNumber());

        // if there is an aircraft parked at this gate, then its callsign is added
        // otherwise left empty
        if (getAircraftAtGate() == null) {
            return String.format("Gate %1$s [empty]", gateNumber);
        } else {
            return String.format("Gate %1$s [%2$s]",
                    gateNumber, getAircraftAtGate().getCallsign());
        }
    }
}
