package Project_part_3;

public class SafetyMonitor implements Runnable {

    private MotorController motor;
    private int priority;

    public SafetyMonitor(MotorController motor, int priority) {
        this.motor = motor;
        this.priority = priority;
    }

    @Override
    public void run() {

        // Safety monitor starts
        System.out.println("[" + System.currentTimeMillis() + "] [HIGH] Safety Monitor started.");

        System.out.println("[" + System.currentTimeMillis() + "] [HIGH] Trying to access motor...");

        long start = System.currentTimeMillis();

        // Critical safety check using the shared motor
        motor.executeAction("SafetyMonitor", "Emergency stop check", 50, priority);

        long end = System.currentTimeMillis();
        long waitTime = end - start;

        // Print how long it had to wait
        System.out.println("Safety Monitor wait time: " + waitTime + " ms");
    }
}