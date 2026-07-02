package Project_part_3;

import javax.realtime.MonitorControl;
import javax.realtime.PriorityCeilingEmulation;
import javax.realtime.PriorityScheduler;

public class MotorController {

    // keeps track of which thread is currently using the motor
    private Thread currentThread = null;

    public MotorController() {

        // set the monitor ceiling to the highest possible priority
        int maxPriority = PriorityScheduler.instance().getMaxPriority();
        PriorityCeilingEmulation ceiling = PriorityCeilingEmulation.instance(maxPriority);

        // apply priority ceiling protocol to this object
        MonitorControl.setMonitorControl(this, ceiling);
    }

    public void executeAction(String threadName, String action, int delay, int priority) {

        Thread thread = Thread.currentThread();

        synchronized (this) {

            // if another thread is using the motor, wait
            while (currentThread != null) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    System.out.println(threadName + " got interrupted while waiting.");
                }
            }

            // current thread now takes control of the motor
            currentThread = thread;

            System.out.println("\n>>> " + threadName + " entered the motor section.");
            System.out.println("[" + System.currentTimeMillis() + "] "
                    + threadName + " locked the motor: " + action);

            try {
                // simulate work being done on the motor
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                System.out.println(threadName + " got interrupted while using the motor.");
            }

            System.out.println("[" + System.currentTimeMillis() + "] "
                    + threadName + " finished and released the motor.");

            // free the motor
            currentThread = null;

            System.out.println("<<< " + threadName + " left the motor section.\n");

            // wake up other waiting threads
            this.notifyAll();
        }
    }
}