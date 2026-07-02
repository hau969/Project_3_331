package Project_part_3;

public class MotionPlanner implements Runnable {

    private MotorController motor; // Added to store the reference

    // Updated constructor to accept the motor object
    public MotionPlanner(MotorController motor) {
        this.motor = motor;
    }

    @Override
    public void run() {
        System.out.println("[" + System.currentTimeMillis() + "] [MED] Motion Planner started.");
        System.out.println("[" + System.currentTimeMillis() + "] [MED] Motion Planner is running...");

        // simulate CPU-heavy work (instead of sleep)
        long startTime = System.currentTimeMillis();

        // busy loop to keep CPU busy for ~1 second
        while (System.currentTimeMillis() - startTime < 1000) {
            Math.sin(Math.random());
        }

        System.out.println("[" + System.currentTimeMillis() + "] [MED] Motion Planner finished.");
    }
}