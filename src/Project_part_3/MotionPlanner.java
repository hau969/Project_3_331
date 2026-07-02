package Project_part_3;

public class MotionPlanner implements Runnable {

    @Override
    public void run() {

        // Motion planner starts running
        System.out.println("[" + System.currentTimeMillis() + "] [MED] Motion Planner started.");

        // Simulate the thread doing a lot of processing
        System.out.println("[" + System.currentTimeMillis() + "] [MED] Motion Planner is using the CPU...");

        try {
            // Delay to represent a long calculation
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            System.out.println("Motion Planner was interrupted.");
        }

        // Finished processing
        System.out.println("[" + System.currentTimeMillis() + "] [MED] Motion Planner finished.");
    }
}