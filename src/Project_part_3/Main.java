package Project_part_3;

import javax.realtime.PriorityParameters;
import javax.realtime.RealtimeThread;

public class Main {

    public static void main(String[] args) {

        // Shared motor used by the Logger and Safety Monitor
        MotorController motor = new MotorController();

        // Set the thread priorities
        PriorityParameters lowPriority = new PriorityParameters(11);
        PriorityParameters mediumPriority = new PriorityParameters(15);
        PriorityParameters highPriority = new PriorityParameters(20);

        // Create the real-time threads
        RealtimeThread loggerThread = new RealtimeThread(
                lowPriority, null, null, null, null,
                new Logger(motor, 11));

        RealtimeThread motionThread = new RealtimeThread(
                mediumPriority, null, null, null, null,
                new MotionPlanner());

        RealtimeThread safetyThread = new RealtimeThread(
                highPriority, null, null, null, null,
                new SafetyMonitor(motor, 20));

        // Give each thread a name
        loggerThread.setName("LoggerThread");
        motionThread.setName("MotionThread");
        safetyThread.setName("SafetyThread");

        System.out.println("--- STARTING RTSJ PRIORITY INHERITANCE DEMO ---");

        try {
            // 1. Logger starts first and enters the lock
            loggerThread.start();
            Thread.sleep(50); 
            
            // 2. Start the Medium Motion Planner NEXT!
            // It will instantly try to preempt the Logger.
            motionThread.start(); 
            Thread.sleep(50); // Give the medium thread a moment to run
            
            // 3. Start the High Safety Monitor LAST.
            safetyThread.start(); 
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}