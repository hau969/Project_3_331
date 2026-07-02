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
        System.out.println("[" + System.currentTimeMillis() + "] [HIGH] trying to access motor...");

        long start = System.currentTimeMillis();

        // high priority thread trying to use motor resource
        motor.executeAction("SafetyMonitor", "Emergency stop check", 50, priority);

        long end = System.currentTimeMillis();

        // measure how long it had to wait
        long waitTime = end - start;

        System.out.println("Safety Monitor wait time: " + waitTime + " ms");
    }
}