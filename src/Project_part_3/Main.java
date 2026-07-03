package Project_part_3;

import javax.realtime.PriorityParameters;
import javax.realtime.RealtimeThread;

public class Main {

    public static void main(String[] args) {

        try {

            // Run the Priority Inheritance test
            System.out.println("-------");
            System.out.println("--- STARTING RTSJ PRIORITY INHERITANCE TEST ---");
            System.out.println("--------");

            runSimulation("INHERITANCE");

            // Wait before starting the next test
            Thread.sleep(2000);

            // Run the Priority Ceiling test
            System.out.println("--------");
            System.out.println("--- STARTING RTSJ PRIORITY CEILING TEST ---");
            System.out.println("---------");

            runSimulation("CEILING");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Runs one simulation using either PIP or PCP
    private static void runSimulation(String protocolType) throws InterruptedException {

        // Create the shared motor controller
        MotorController motor = new MotorController(protocolType);

        // Set the priorities for each thread
        PriorityParameters lowPriority = new PriorityParameters(2);
        PriorityParameters mediumPriority = new PriorityParameters(5);
        PriorityParameters highPriority = new PriorityParameters(9);

        // Create the Logger thread
        RealtimeThread loggerThread = new RealtimeThread(
                lowPriority, null, null, null, null,
                new Logger(motor, 11));

        // Create the Motion Planner thread
        RealtimeThread motionThread = new RealtimeThread(
                mediumPriority, null, null, null, null,
                new MotionPlanner());

        // Create the Safety Monitor thread
        RealtimeThread safetyThread = new RealtimeThread(
                highPriority, null, null, null, null,
                new SafetyMonitor(motor, 20));

        // Give each thread a name
        loggerThread.setName("LoggerThread_" + protocolType);
        motionThread.setName("MotionThread_" + protocolType);
        safetyThread.setName("SafetyThread_" + protocolType);

        // Start the Logger first so it gets the lock
        loggerThread.start();
        Thread.sleep(50);

        // Start the Motion Planner
        motionThread.start();
        Thread.sleep(50);

        // Start the Safety Monitor
        safetyThread.start();

        // Wait for all threads to finish
        loggerThread.join();
        motionThread.join();
        safetyThread.join();
    }
}