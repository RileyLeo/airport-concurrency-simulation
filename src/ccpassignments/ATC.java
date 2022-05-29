package ccpassignments;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ATC extends Thread {

    Queue<Plane> planesInAir;
    Semaphore runway = new Semaphore(1);
    Semaphore gate = new Semaphore(2);
    public static int planeLeft = 0;
    public static int maxPlane = 6;
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_RESET = "\u001B[0m";

    public ATC() {
        planesInAir = new LinkedList<Plane>();
    }

    public void addLanding(Plane plane) {
        synchronized (planesInAir) {
            ((Queue<Plane>) planesInAir).add(plane);
            System.out.println(ANSI_YELLOW_BACKGROUND + "ATC: " + plane.getPlaneName() + " queueing up at " + plane.getArrivalTime() + ANSI_RESET);
        }
    }

    public void acquireGateAndRunway() {
        try {
            gate.acquire();
            Plane plane = (Plane) planesInAir.poll();
            System.out.println(ANSI_YELLOW_BACKGROUND + "ATC: " + plane.getPlaneName() + " has acquire a gate." + ANSI_RESET);
            System.out.println(ANSI_YELLOW_BACKGROUND + "ATC: " + plane.getPlaneName() + " Attempting to acquire the runway for landing." + ANSI_RESET);
            runway.acquire();
            System.out.println(ANSI_YELLOW_BACKGROUND + "ATC: " + plane.getPlaneName() + " has acquire a runway." + ANSI_RESET);
        } catch (InterruptedException ex) {
            Logger.getLogger(ATC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void acquireRunway(Plane plane) {
        try {
            System.out.println(ANSI_YELLOW_BACKGROUND + "ATC: " + plane.getPlaneName() + " Attempting to acquire the runway for departure.");
            runway.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(ATC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
