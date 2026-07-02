package Project_part_3;

// Import the official real-time monitor management APIs from JamaicaVM
import javax.realtime.MonitorControl;
import javax.realtime.PriorityInheritance;

public class MotorController {

    // Keeps track of which thread is currently using the motor
    private Thread currentThread = null;

    public MotorController() {
        // Fetch the native RTSJ Priority Inheritance policy instance
        PriorityInheritance pi = PriorityInheritance.instance();

        // Apply it directly to this monitor object
        MonitorControl.setMonitorControl(this, pi);
    }

    public void executeAction(String threadName, String action, int delay, int priority) {
        Thread thread = Thread.currentThread();

        // We wrap the ENTIRE entry, execution, and exit in ONE synchronized block
        synchronized (this) {

            // Wait if another thread is using the motor
            while (currentThread != null) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    System.out.println(threadName + " was interrupted.");
                }
            }

            currentThread = thread;

            // Thread enters critical section
            System.out.println("\n>>> [PIP LOCK ACTIVE] " + threadName + " entered monitor at priority: " + thread.getPriority());
            System.out.println("[" + System.currentTimeMillis() + "] " + threadName + " acquired lock: " + action);

            try {
                // By sleeping INSIDE the synchronized block, Logger physically keeps the lock.
                // When SafetyMonitor tries to enter this synchronized block, JamaicaVM detects
                // the collision and instantly boosts Logger's priority to 10!
                Thread.sleep(delay);

            } catch (InterruptedException e) {
                System.out.println(threadName + " was interrupted inside critical section.");
            }

            System.out.println("[" + System.currentTimeMillis() + "] " + threadName + " released lock.");
            
            currentThread = null;

            // Exit monitor
            System.out.println("<<< [PIP DEACTIVATED] " + threadName + " exited monitor. <<<\n");

            this.notifyAll();
        } // <--- Monitor lock is safely released here, dropping any inherited priority automatically
    }
}