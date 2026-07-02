package Project_part_3;

import javax.realtime.PriorityParameters;
import javax.realtime.RealtimeThread;

public class Main {

    public static void main(String[] args) {

        // shared motor object used by all threads
        MotorController motor = new MotorController();

        // setting priorities for each thread
        PriorityParameters lowPriority = new PriorityParameters(11);
        PriorityParameters mediumPriority = new PriorityParameters(15);
        PriorityParameters highPriority = new PriorityParameters(20);

        // creating the real-time threads

        // logger runs first (low priority)
        RealtimeThread loggerThread = new RealtimeThread(
                lowPriority, null, null, null, null,
                new Logger(motor, 11));

        // motion planner runs second (medium priority)
        RealtimeThread motionThread = new RealtimeThread(
                mediumPriority, null, null, null, null,
                new MotionPlanner(motor));

        // safety monitor runs last (high priority)
        RealtimeThread safetyThread = new RealtimeThread(
                highPriority, null, null, null, null,
                new SafetyMonitor(motor, 20));

        // naming threads just for easier debugging
        loggerThread.setName("LoggerThread");
        motionThread.setName("MotionThread");
        safetyThread.setName("SafetyThread");

        System.out.println("--- STARTING RTSJ PRIORITY CEILING TEST ---");

        try {
            // start logger first
            loggerThread.start();
            Thread.sleep(50);

            // start motion planner after a short delay
            motionThread.start();
            Thread.sleep(50);

            // start safety monitor last
            safetyThread.start();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}