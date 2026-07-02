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

        // try to use the motor resource for a short time
        motor.executeAction("Logger", "Logging system data", 400, priority);

        System.out.println("[" + System.currentTimeMillis() + "] [LOW] Logger finished.");
    }
}