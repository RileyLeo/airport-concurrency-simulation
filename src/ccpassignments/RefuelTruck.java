package ccpassignments;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RefuelTruck extends Thread {

    public static Queue<Plane> planesThatNeedsRefueling = new LinkedList<Plane>();
    Semaphore truck = new Semaphore(1);
    int fuelAmount = 2;
    int planeRefueled = 0;
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_RESET = "\u001B[0m";

    public void run() {
        Plane plane;
        while (true) {
            if (fuelAmount == 0) {
                try {
                    truck.acquire();
                    System.out.println(ANSI_PURPLE_BACKGROUND + "Refuel Truck: Out of Fuel, refueling time!" + ANSI_RESET);
                    Thread.sleep(5000);
                    System.out.println(ANSI_PURPLE_BACKGROUND + "Refuel Truck: Fuel's full, back to work!" + ANSI_RESET);
                    fuelAmount = fuelAmount + 2;
                    truck.release();
                } catch (InterruptedException ex) {
                    Logger.getLogger(RefuelTruck.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            synchronized (planesThatNeedsRefueling) {
                while (planesThatNeedsRefueling.isEmpty() && planeRefueled != ATC.maxPlane) {
                    System.out.println(ANSI_PURPLE_BACKGROUND + "Refuel truck is waiting for a plane" + ANSI_RESET);
                    try {
                        planesThatNeedsRefueling.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(RefuelTruck.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (planeRefueled == ATC.maxPlane) {
                    System.out.println(ANSI_PURPLE_BACKGROUND + "Refuel Truck: All planes have left! Work's done!" + ANSI_RESET);
                    break;
                }
                plane = (Plane) planesThatNeedsRefueling.poll();
                System.out.println(ANSI_PURPLE_BACKGROUND + "Refuel Truck: " + plane.getPlaneName() + " needs refueling" + ANSI_RESET);
                try {
                    truck.acquire();
                } catch (InterruptedException ex) {
                    Logger.getLogger(RefuelTruck.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println(ANSI_PURPLE_BACKGROUND + "Refuel Truck: Refueling for " + plane.getPlaneName());
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(RefuelTruck.class.getName()).log(Level.SEVERE, null, ex);
            }
            fuelAmount--;
            System.out.println(ANSI_PURPLE_BACKGROUND + "Refuel Truck: " + plane.getPlaneName() + " refueled!" + ANSI_RESET);
            truck.release();
            plane.refueled = true;
            planeRefueled++;
        }
    }

    public static void add(Plane plane) {
        System.out.println(ANSI_PURPLE_BACKGROUND + "Refuel Truck: " + plane.getPlaneName() + " added to the refueling queue" + ANSI_RESET);
        synchronized (planesThatNeedsRefueling) {
            ((Queue<Plane>) planesThatNeedsRefueling).add(plane);
            if (planesThatNeedsRefueling.size() == 1) {
                planesThatNeedsRefueling.notify();
            }
        }
    }
}
