package Project_part_3;

public class MotionPlanner implements Runnable {

    @Override
    public void run() {

        System.out.println("[" + System.currentTimeMillis() + "] [MED] Motion Planner started.");
        System.out.println("[" + System.currentTimeMillis() + "] [MED] Motion Planner is running...");

        // simulate CPU-heavy work (instead of sleep)
        long startTime = System.currentTimeMillis();

        // busy loop to keep CPU busy for ~1 second
        while (System.currentTimeMillis() - startTime < 1000) {
            // just wasting CPU cycles to simulate calculations
            Math.sin(Math.random());
        }

        System.out.println("[" + System.currentTimeMillis() + "] [MED] Motion Planner finished.");
    }
}