package Project_part_3;

import javax.realtime.MonitorControl;
import javax.realtime.PriorityCeilingEmulation;
import javax.realtime.PriorityInheritance;

public class MotorController {

    private Thread currentThread = null;
    private String protocolName;

    public MotorController(String protocolType) {

        if (protocolType.equalsIgnoreCase("CEILING")) {

            protocolName = "Priority Ceiling";

            int maxPriority = 10;
            PriorityCeilingEmulation ceiling = PriorityCeilingEmulation.instance(maxPriority);
            MonitorControl.setMonitorControl(this, ceiling);

        } else {

            protocolName = "Priority Inheritance";

            PriorityInheritance inheritance = PriorityInheritance.instance();
            MonitorControl.setMonitorControl(this, inheritance);
        }
    }

    public void executeAction(String threadName, String action, int delay, int priority) {

        Thread thread = Thread.currentThread();
        int originalPriority = thread.getPriority();

        // If using PCP, boost Logger when it enters the monitor
        if (protocolName.equals("Priority Ceiling") && threadName.equalsIgnoreCase("Logger")) {

            System.out.println("\n>>> [PCP] " + threadName + " entering monitor, priority boosted from " + originalPriority + " to 9 <<<\n");

            thread.setPriority(9);
        }

        synchronized (this) {

            // If using PIP, check if SafetyMonitor is blocked and adjust priority
            if (protocolName.equals("Priority Inheritance")&& threadName.equalsIgnoreCase("SafetyMonitor")) {

                if (currentThread != null && currentThread.getPriority() < priority) {

                    System.out.println("\n>>> [PIP] " + threadName + " blocked by " + currentThread.getName()  + ", increasing its priority to " + priority + " <<<\n");

                    currentThread.setPriority(priority);
                }
            }

            // Wait until motor is free
            while (currentThread != null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println(threadName + " interrupted while waiting");
                }
            }

            currentThread = thread;

            System.out.println("\n>>> [" + protocolName + "] " + threadName + " entered monitor");
            System.out.println("[" + System.currentTimeMillis() + "] " + threadName + " acquired lock: " + action);

            try {

                int timeElapsed = 0;

                while (timeElapsed < delay) {

                    Thread.sleep(50);
                    timeElapsed += 50;

                    // Only log details for Logger thread
                    if (threadName.equalsIgnoreCase("Logger")) {
                        System.out.println("    -> Logger running... priority: "
                                + thread.getPriority());
                    }
                }

            } catch (InterruptedException e) {
                System.out.println(threadName + " interrupted while using motor");
            }

            System.out.println("[" + System.currentTimeMillis() + "] "
                    + threadName + " released lock");

            currentThread = null;

            System.out.println("<<< [" + protocolName + "] " + threadName + " left monitor\n");

            // reset priority
            thread.setPriority(originalPriority);

            notifyAll();
        }
    }
}