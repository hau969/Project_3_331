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
        System.out.println("[" + System.currentTimeMillis() + "] [LOW] Logger started.");

        // Request the motor control lock for 400ms
        motor.executeAction("Logger", "Logging system data", 400, priority);

        System.out.println("[" + System.currentTimeMillis() + "] [LOW] Logger finished.");
    }
}