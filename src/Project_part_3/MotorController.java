package Project_part_3;

import javax.realtime.MonitorControl;
import javax.realtime.PriorityInheritance;

public class MotorController {

    private Thread currentThread = null;

    public MotorController() {
        // Set the monitor control policy to Priority Inheritance
        PriorityInheritance inheritance = PriorityInheritance.instance();
        MonitorControl.setMonitorControl(this, inheritance);
    }

    public void executeAction(String threadName, String action, int delay, int priority) {
        Thread thread = Thread.currentThread();

        synchronized (this) {
            // Wait until the motor is free
            while (currentThread != null) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    System.out.println(threadName + " was interrupted.");
                }
            }

            currentThread = thread;

            System.out.println("\n>>> " + threadName + " entered the monitor.");
            System.out.println("[" + System.currentTimeMillis() + "] " 
                    + threadName + " acquired the lock: " + action);

            try {
                // Simulate holding the resource
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                System.out.println(threadName + " was interrupted while using the motor.");
            }

            System.out.println("[" + System.currentTimeMillis() + "] " 
                    + threadName + " released the lock.");

            currentThread = null;
            System.out.println("<<< " + threadName + " left the monitor.\n");

            this.notifyAll();
        }
    }
}