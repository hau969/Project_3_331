package Project_part_3;

import javax.realtime.PriorityParameters;
import javax.realtime.RealtimeThread;

public class Main {

    public static void main(String[] args) {
        // Shared motor resource
        MotorController motor = new MotorController();

        // Assign thread priorities
        PriorityParameters lowPriority = new PriorityParameters(11);
        PriorityParameters mediumPriority = new PriorityParameters(15);
        PriorityParameters highPriority = new PriorityParameters(20);

        // Instantiate Real-time threads
        RealtimeThread loggerThread = new RealtimeThread(
                lowPriority, null, null, null, null,
                new Logger(motor, 11));

        RealtimeThread motionThread = new RealtimeThread(
                mediumPriority, null, null, null, null,
                new MotionPlanner());

        RealtimeThread safetyThread = new RealtimeThread(
                highPriority, null, null, null, null,
                new SafetyMonitor(motor, 20));

        loggerThread.setName("LoggerThread");
        motionThread.setName("MotionThread");
        safetyThread.setName("SafetyThread");

        System.out.println("--- STARTING RTSJ PRIORITY INHERITANCE TEST ---");

        try {
            // 1. Logger starts at 0ms and immediately gets the lock
            loggerThread.start();
            Thread.sleep(50); 
            
            // 2. Motion Planner starts at 50ms and preempts Logger because it's higher priority
            motionThread.start(); 
            Thread.sleep(50); 
            
            // 3. Safety Monitor starts at 100ms, preempts Motion Planner, and blocks on Logger.
            // This triggers Priority Inheritance.
            safetyThread.start(); 
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}