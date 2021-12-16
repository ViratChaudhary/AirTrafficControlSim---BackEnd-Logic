package towersim.ground;

import towersim.util.EmergencyState;
import towersim.util.OccupancyLevel;

/** Represents an airport terminal that is designed to accommodate helicopters. */
public class HelicopterTerminal extends Terminal implements EmergencyState, OccupancyLevel {

    /**
     * Creates a new HelicopterTerminal with the given unique terminal number.
     *
     * @param terminalNumber identifying number of this helicopter terminal
     */
    public HelicopterTerminal(int terminalNumber) {
        super(terminalNumber);
    }
}
