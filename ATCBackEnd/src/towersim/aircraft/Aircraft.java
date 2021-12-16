package towersim.aircraft;

import towersim.tasks.TaskList;
import towersim.tasks.TaskType;
import towersim.util.EmergencyState;
import towersim.util.OccupancyLevel;
import towersim.util.Tickable;

/** Represents an aircraft whose movement is managed by the system. */
public abstract class Aircraft implements EmergencyState, OccupancyLevel, Tickable {

    /** weight of a litre of aviation fuel, in kilograms */
    public static final double LITRE_OF_FUEL_WEIGHT = 0.8;

    /**  The aircraft's callsign. */
    private String callsign;

    /**  The aircraft's constant characteristics. */
    private AircraftCharacteristics characteristics;

    /**  The aircraft's task list. */
    private TaskList tasks;

    /** The aircraft's current amount of fuel onboard, in litres. */
    private double fuelAmount;

    /** The aircraft's current state of emergency. */
    private boolean emergencyState;

    /**
     * Creates a new aircraft with the given callsign, task list, characteristics and fuel amount.
     *
     * @param callsign        unique callsign
     * @param characteristics characteristics that describe this aircraft
     * @param tasks           task list to be used by aircraft
     * @param fuelAmount      current amount of fuel onboard, in litres
     * @throws IllegalArgumentException if fuelAmount < 0 or fuelAmount > fuel capacity
     */

    protected Aircraft(String callsign, AircraftCharacteristics characteristics,
                       TaskList tasks, double fuelAmount) {

        this.callsign = callsign;
        this.characteristics = characteristics;
        this.tasks = tasks;
        this.fuelAmount = fuelAmount;
        this.emergencyState = false;

        // ensures the fuelAmount is valid, as it cannot be below 0 or above the maximum capacity
        if (this.fuelAmount < 0 || this.fuelAmount > this.characteristics.fuelCapacity) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns the callsign of the aircraft.
     *
     * @return aircraft callsign
     */
    public String getCallsign() {
        return callsign;
    }

    /**
     * Returns the current amount of fuel onboard, in litres.
     *
     * @return current fuel amount
     */
    public double getFuelAmount() {
        return fuelAmount;
    }

    /**
     * Returns this aircraft's characteristics.
     *
     * @return aircraft characteristics
     */
    public AircraftCharacteristics getCharacteristics() {
        return characteristics;
    }

    /**
     * Returns the percentage of fuel remaining, rounded to the nearest whole percentage, 0 to 100.
     *
     * @return percentage of fuel remaining
     */
    public int getFuelPercentRemaining() {
        double fuelToCapacityRatio = getFuelAmount() / getCharacteristics().fuelCapacity;
        return (int) Math.round(100 * fuelToCapacityRatio);
    }

    /**
     * Returns the total weight of the aircraft in its current state.
     *
     * @return total weight of aircraft in kilograms
     */
    public double getTotalWeight() {
        // sums the empty weight and fuel weight of the aircraft
        return (getCharacteristics().emptyWeight + (LITRE_OF_FUEL_WEIGHT * getFuelAmount()));
    }

    /**99
     * Returns the task list of this aircraft.
     *
     * @return task list
     */
    public TaskList getTaskList() {
        return tasks;
    }

    /**
     * Returns the number of ticks required to load the aircraft at the gate.
     *
     * @return time to load aircraft, in ticks
     */
    public abstract int getLoadingTime();

    /**
     * Updates the aircraft's state on each tick of the simulation.
     */
    public void tick() {
        TaskType currentTask = getTaskList().getCurrentTask().getType();

        // decreases fuel of the aircraft by 10% per tick when aircraft is on the away task.
        if (currentTask == TaskType.AWAY) {
            double fuelDecreaseRate = 0.1 * getCharacteristics().fuelCapacity;
            fuelAmount = getFuelAmount() - fuelDecreaseRate;
            if (getFuelPercentRemaining() < 0) {
                // Sets fuel to 0 when a full tick decrement would fall below 0.
                fuelAmount = 0.0;
            }
        }

        // increases fuel of the aircraft by the (capacity / load time) per tick when on load task
        if (currentTask == TaskType.LOAD) {
            double fuelIncreaseRate = characteristics.fuelCapacity / getLoadingTime();
            if (getFuelAmount() + fuelIncreaseRate <= getCharacteristics().fuelCapacity) {
                fuelAmount += fuelIncreaseRate;
            } else {
                // Sets fuel to capacity when a full tick increment would exceed capacity
                fuelAmount = getCharacteristics().fuelCapacity;
            }
        }
    }

    /**
     * Returns the human-readable string representation of this aircraft.
     *
     * @return string representation of this aircraft
     */
    @Override
    public String toString() {
        String aircraftType = getCharacteristics().type.toString();
        String callsign = getCallsign();
        String model = getCharacteristics().toString();
        String currentTask = getTaskList().getCurrentTask().getType().toString();
        String emergency = "(EMERGENCY)";

        // if aircraft has an emergency, then emergency status is added to the string
        if (hasEmergency()) {
            return String.format("%1$s %2$s %3$s %4$s %5$s",
                    aircraftType, callsign, model, currentTask, emergency);
        } else {
            return String.format("%1$s %2$s %3$s %4$s", aircraftType, callsign, model, currentTask);
        }
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
}
