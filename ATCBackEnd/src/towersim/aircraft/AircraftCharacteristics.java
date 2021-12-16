package towersim.aircraft;

import static towersim.aircraft.AircraftType.AIRPLANE;
import static towersim.aircraft.AircraftType.HELICOPTER;

/**
 * Stores information about particular models of aircraft.Characteristics of an individual
 * aircraft include the type of aircraft, its empty weight, fuel capacity, passenger capacity
 * and freight capacity
 */
public enum AircraftCharacteristics {

    /** Narrow-body twin-jet airliner. */
    AIRBUS_A320(AIRPLANE, 42600, 27200, 150, 0),

    /** Wide-body quad-jet freighter. */
    BOEING_747_8F(AIRPLANE, 197131, 226117, 0, 137756),

    /** Four-seater light helicopter. */
    ROBINSON_R44(HELICOPTER, 658, 190, 4, 0),

    /** Long range, wide-body twin-jet airliner. */
    BOEING_787(AIRPLANE, 119950, 126206, 242, 0),

    /** Twin-jet regional airliner. */
    FOKKER_100(AIRPLANE, 24375, 13365, 97, 0),

    /** Twin-engine heavy-lift helicopter. */
    SIKORSKY_SKYCRANE(HELICOPTER, 8724, 3328, 0, 9100);

    /** The aircraft's type */
    public final AircraftType type;

    /** The aircraft's empty weight in kilograms. */
    public final int emptyWeight;

    /** The aircraft's fuel capacity in litres. */
    public final double fuelCapacity;

    /** The aircraft's maximum capacity of passengers. */
    public final int passengerCapacity;

    /** The aircraft's maximum capacity of freight. */
    public final int freightCapacity;

    /**
     * Creates a TaskType with the given description
     *
     * @param type              type of aircraft
     * @param emptyWeight       weight of aircraft with no load or fuel, in kilograms
     * @param fuelCapacity      maximum amount of fuel able to be carried, in litres
     * @param passengerCapacity maximum number of passengers able to be carried
     * @param freightCapacity   maximum amount of freight able to be carried, in kilograms
     */
    AircraftCharacteristics(AircraftType type, int emptyWeight, double fuelCapacity,
                            int passengerCapacity, int freightCapacity) {
        this.type = type;
        this.emptyWeight = emptyWeight;
        this.fuelCapacity = fuelCapacity;
        this.passengerCapacity = passengerCapacity;
        this.freightCapacity = freightCapacity;
    }
}
