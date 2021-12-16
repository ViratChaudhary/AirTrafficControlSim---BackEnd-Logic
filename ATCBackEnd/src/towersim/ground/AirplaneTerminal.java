package towersim.ground;

import towersim.util.EmergencyState;
import towersim.util.OccupancyLevel;

/** Represents an airport terminal that is designed to accommodate airplanes. */
public class AirplaneTerminal extends Terminal implements EmergencyState, OccupancyLevel {

    /**
     * Creates a new AirplaneTerminal with the given unique terminal number.
     *
     * @param terminalNumber identifying number of this airplane terminal
     */
    public AirplaneTerminal(int terminalNumber) {
        super(terminalNumber);
    }
}
