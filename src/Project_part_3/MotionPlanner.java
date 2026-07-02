package Project_part_3;

public class MotionPlanner implements Runnable {

    @Override
    public void run() {
        System.out.println("[" + System.currentTimeMillis() + "] [MED] Motion Planner started.");
        System.out.println("[" + System.currentTimeMillis() + "] [MED] Motion Planner is actively burning CPU cycles...");

        // Actively hog the CPU for 1000ms instead of sleeping
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < 1000) {
            // Busy-wait loop simulating intensive calculations
            Math.sin(Math.random()); 
        }

        System.out.println("[" + System.currentTimeMillis() + "] [MED] Motion Planner finished.");
    }
}