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
        System.out.println("[" + System.currentTimeMillis() + "] [HIGH] Safety Monitor started.");
        System.out.println("[" + System.currentTimeMillis() + "] [HIGH] Trying to access motor...");

        long start = System.currentTimeMillis();

        // High priority request to use the motor
        motor.executeAction("SafetyMonitor", "Emergency stop check", 50, priority);

        long end = System.currentTimeMillis();
        long waitTime = end - start;

        System.out.println("Safety Monitor wait time: " + waitTime + " ms");
    }
}