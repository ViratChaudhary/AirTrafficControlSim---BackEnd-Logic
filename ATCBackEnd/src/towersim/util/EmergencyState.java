package towersim.util;

/** Denotes an entity whose behaviour and operations can be affected by emergencies. */
public interface EmergencyState {

    /** Declares a state of emergency. */
    void declareEmergency();

    /** Clears any active state of emergency. */
    void clearEmergency();

    /**
     * Returns whether or not a state of emergency is currently active.
     *
     * @return true if in emergency; false otherwise
     */
    boolean hasEmergency();
}
