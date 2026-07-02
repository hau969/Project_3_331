package Project_part_3;

public class Logger implements Runnable {

    private MotorController motor;
    private int priority;

    public Logger(MotorController motor, int priority) {
        this.motor = motor;
        this.priority = priority;
    }

    @Override
    public void run() {

        // Logger thread starts
        System.out.println("[" + System.currentTimeMillis() + "] [LOW] Logger started.");

        // Simulate logging activity using the shared motor
        motor.executeAction("Logger", "Logging system data", 400, priority);

        // Logger finishes
        System.out.println("[" + System.currentTimeMillis() + "] [LOW] Logger finished.");
    }
}