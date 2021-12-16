package towersim.aircraft;

import towersim.tasks.TaskList;
import towersim.tasks.TaskType;
import towersim.util.EmergencyState;
import towersim.util.OccupancyLevel;
import towersim.util.Tickable;

/** Represents an aircraft capable of carrying passenger cargo. */
public class PassengerAircraft extends Aircraft implements
        EmergencyState, OccupancyLevel, Tickable {

    /** average weight of a single passenger including their baggage, in kilograms */
    public static final double AVG_PASSENGER_WEIGHT = 90.0;

    /** The aircraft's current number of passengers onboard. */
    private int numPassengers;

    /**
     * Creates a new passenger aircraft with the given callsign, task list,
     * characteristics, amount of fuel and number of passengers.
     *
     * @param callsign        unique callsign
     * @param characteristics characteristics that describe this aircraft
     * @param tasks           task list to be used by aircraft
     * @param fuelAmount      current amount of fuel onboard, in litres
     * @param numPassengers   current number of passengers onboard
     * @throws IllegalArgumentException if numPassengers < 0 or numPassengers > passenger capacity
     */
    public PassengerAircraft(String callsign, AircraftCharacteristics characteristics,
                             TaskList tasks, double fuelAmount, int numPassengers) {

        super(callsign, characteristics, tasks, fuelAmount);
        this.numPassengers = numPassengers;

        // ensures the passenger number is valid, as it cannot be below 0 or above the capacity
        if (this.numPassengers < 0
                || this.numPassengers > this.getCharacteristics().passengerCapacity) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns the total weight of the aircraft in its current state.
     *
     * @return total weight of aircraft in kilograms
     */
    @Override
    public double getTotalWeight() {
        // calculates total aircraft weight by summing empty weight with fuel and passenger weight
        double emptyAircraftWeight = super.getTotalWeight();
        double passengerWeight = AVG_PASSENGER_WEIGHT * numPassengers;

        return emptyAircraftWeight + passengerWeight;
    }

    /**
     * Returns the total number of passenger to be boarded based on the maximum passenger capacity
     * and the load ratio specified in the aircraft's current task
     *
     *
     * @return total passengers to be boarded
     */
    private int getTotalPassengersToBeLoaded() {
        double loadPercentRatio = getTaskList().getCurrentTask().getLoadPercent() / 100.0;
        return (int) Math.round(getCharacteristics().passengerCapacity * loadPercentRatio);
    }

    /**
     * Returns the number of ticks required to load the aircraft at the gate.
     *
     * @return loading time in ticks
     */
    public int getLoadingTime() {
        int passengersToBeLoaded = getTotalPassengersToBeLoaded();
        int passengerLoadingTime = (int) Math.round(Math.log10(passengersToBeLoaded));

        // tick time bound by 1, it must take at least 1 tick
        if (passengerLoadingTime < 1) {
            return 1;
        } else {
            return passengerLoadingTime;
        }
    }

    /**
     * Returns the ratio of passengers onboard to maximum passenger capacity
     * as a percentage between 0 and 100.
     *
     * @return occupancy level as a percentage
     */
    public int calculateOccupancyLevel() {
        double passengerToCapacityRatio = numPassengers
                / (double) getCharacteristics().passengerCapacity;

        return (int) Math.round(100 * passengerToCapacityRatio);
    }

    /**
     * Updates the aircraft's state on each tick of the simulation.
     */
    @Override
    public void tick() {
        super.tick();

        TaskType currentTask = getTaskList().getCurrentTask().getType();

        if (currentTask == TaskType.LOAD) {
            int totalPassengersToBeLoaded = getTotalPassengersToBeLoaded();
            int passengerIncreasePerTick = (int) Math.round(totalPassengersToBeLoaded
                    / (double) getLoadingTime());

            // increases passengers by the per tick rate every time tick method is called
            if (numPassengers + passengerIncreasePerTick
                    <= getCharacteristics().passengerCapacity) {
                numPassengers += passengerIncreasePerTick;
            } else {
                // sets passengers to capacity when a full tick increment would exceed capacity
                numPassengers = getCharacteristics().passengerCapacity;
            }
        }
    }
}
