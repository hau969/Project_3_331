package Project_part_3;

public class MotionPlanner implements Runnable {

    @Override
    public void run() {

        // Show that the Motion Planner has started
        System.out.println("[" + System.currentTimeMillis() + "] [MED] Motion Planner started.");
        System.out.println("[" + System.currentTimeMillis() + "] [MED] Motion Planner is actively burning CPU cycles...");

        // Keep the CPU busy for about 1 second
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < 1000) {

            // Simulate some processing work
            Math.sin(Math.random());
        }

        // Show that the Motion Planner has finished
        System.out.println("[" + System.currentTimeMillis() + "] [MED] Motion Planner finished.");
    }
}