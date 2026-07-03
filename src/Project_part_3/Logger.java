package Project_part_3;

public class Logger implements Runnable {

    // Reference to the shared motor controller
    private MotorController motor;

    // Stores the thread's priority level
    private int priority;

    // Constructor to initialize the shared resource and priority
    public Logger(MotorController motor, int priority) {
        this.motor = motor;
        this.priority = priority;
    }

    @Override
    public void run() {

        // Display a message when the Logger thread starts
        System.out.println("[" + System.currentTimeMillis() + "] [LOW] Logger started.");

        // The Logger requests access to the shared motor controller.
        // It keeps the resource for 400 ms to simulate a long logging task.
        motor.executeAction("Logger", "Logging system data", 400, priority);

        // Display a message after the Logger has finished its work
        System.out.println("[" + System.currentTimeMillis() + "] [LOW] Logger finished.");
    }
}