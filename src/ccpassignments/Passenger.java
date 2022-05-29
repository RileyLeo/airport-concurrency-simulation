package ccpassignments;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Passenger extends Thread {

    Plane plane;
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_RESET = "\u001B[0m";

    public Passenger(Plane plane) {
        this.plane = plane;
    }

    public void run() {
        disembarkPassengers();
        embarkPassengers();
    }

    private void disembarkPassengers() {
        int rand = (int) ((Math.random() * (11) + 40));
        Report.disembarkPassengerCount.set(plane.index, rand);
        System.out.println(ANSI_GREEN_BACKGROUND + "Passenger Thread: There are " + rand + " passengers disembarking " + plane.getPlaneName() + "." + ANSI_RESET);
        for (int i = 1; i <= rand; i++) {
            try {
//                System.out.println("Thread Passenger: Passenger " + i + " is disembarking" + plane.getPlaneName() + "!");
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println(ANSI_GREEN_BACKGROUND + "Passenger Thread:" + plane.getPlaneName() + " fully disembarked " + rand + " Passengers!" + ANSI_RESET);
    }

    private void embarkPassengers() {
        int rand = (int) ((Math.random() * (11) + 40));
        Report.embarkPassengerCount.set(plane.index, rand);
        System.out.println(ANSI_GREEN_BACKGROUND + "Passenger Thread: There are " + rand + " passengers boarding " + plane.getPlaneName() + "." + ANSI_RESET);
        for (int i = 1; i <= rand; i++) {
            try {
//                System.out.println("Thread Passenger: Passenger " + i + " is boarding" + plane.getPlaneName() + "!");
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println(ANSI_GREEN_BACKGROUND + "Passenger Thread:" + plane.getPlaneName() + " fully boarded " + rand + " Passengers!" + ANSI_RESET);
    }
}
