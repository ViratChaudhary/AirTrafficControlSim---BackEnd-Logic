package towersim.aircraft;

import towersim.tasks.TaskList;
import towersim.tasks.TaskType;
import towersim.util.EmergencyState;
import towersim.util.OccupancyLevel;
import towersim.util.Tickable;

/** Represents an aircraft capable of carrying freight cargo. */
public class FreightAircraft extends Aircraft implements EmergencyState, OccupancyLevel, Tickable {

    /** The aircraft's current freight amount onboard, in kilograms. */
    private int freightAmount;

    /**
     * Creates a new freight aircraft with the given callsign, task list,
     * characteristics, amount of fuel and kilograms of freight.
     *
     * @param callsign        unique callsign
     * @param characteristics characteristics that describe this aircraft
     * @param tasks           task list to be used by aircraft
     * @param fuelAmount      current amount of fuel onboard, in litres
     * @param freightAmount   current amount of freight onboard, in kilograms
     * @throws IllegalArgumentException if freightAmount < 0 or freightAmount > freight capacity
     */
    public FreightAircraft(String callsign, AircraftCharacteristics characteristics, TaskList tasks,
                           double fuelAmount, int freightAmount) {

        super(callsign, characteristics, tasks, fuelAmount);
        this.freightAmount = freightAmount;

        // ensures the freight amount is valid, as it cannot be below 0 or above the capacity
        if (this.freightAmount < 0
                || this.freightAmount > this.getCharacteristics().freightCapacity) {
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
        // calculates total aircraft weight by summing empty weight with fuel and freight weight
        double emptyAircraftWeight = super.getTotalWeight();
        double freightWeight = freightAmount;

        return emptyAircraftWeight + freightWeight;
    }

    /**
     * Returns the total freight to be loaded based on the maximum freight capacity of the aircraft
     * and the load ratio specified in the aircraft's current task
     *
     *
     * @return total freight to be loaded
     */
    private int getTotalFreightToBeLoaded() {
        double loadPercentRatio = getTaskList().getCurrentTask().getLoadPercent() / 100.0;
        return (int) Math.round(getCharacteristics().freightCapacity * loadPercentRatio);
    }

    /**
     * Returns the number of ticks required to load the aircraft at the gate.
     *
     * @return loading time in ticks
     */
    public int getLoadingTime() {
        int freightToBeLoaded = getTotalFreightToBeLoaded();

        if (freightToBeLoaded < 1000) {
            return 1;
        } else if (freightToBeLoaded > 50000) {
            return 3;
        } else {
            return 2;
        }
    }

    /**
     * Returns the ratio of freight cargo onboard to maximum available freight capacity
     * as a percentage between 0 and 100.
     *
     * @return occupancy level as a percentage
     */
    public int calculateOccupancyLevel() {
        double freightToCapacityRatio = freightAmount
                / (double) getCharacteristics().freightCapacity;
        return (int) Math.round(100 * freightToCapacityRatio);
    }

    /**
     * Updates the aircraft's state on each tick of the simulation.
     */
    @Override
    public void tick() {
        super.tick();

        TaskType currentTask = getTaskList().getCurrentTask().getType();

        if (currentTask == TaskType.LOAD) {
            int totalFreightToBeLoaded = getTotalFreightToBeLoaded();
            int freightIncreasePerTick = (int) Math.round(totalFreightToBeLoaded
                    / (double) getLoadingTime());

            // increases freight amount by the per tick rate every time tick method is called
            if (freightAmount + freightIncreasePerTick
                    <= getCharacteristics().freightCapacity) {
                freightAmount += freightIncreasePerTick;
            } else {
                // sets freight amount to capacity when a full tick increment would exceed capacity
                freightAmount = getCharacteristics().freightCapacity;
            }
        }
    }
}






















