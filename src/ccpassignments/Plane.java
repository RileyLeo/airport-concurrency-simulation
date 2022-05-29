package ccpassignments;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Plane extends Thread {

    public int index;
    private String planeName;
    public Boolean refueled = false;
    Date arrivalTime;
    ATC atc;
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_RESET = "\u001B[0m";

    public Plane(ATC atc) {
        this.atc = atc;
    }

    public String getPlaneName() {
        return planeName;
    }

    public void setPlaneName(String planeName) {
        this.planeName = planeName;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void run() {
        
        requestForLanding();
        acuireResourceForLanding();
        
        landingSequence();
        
        airportSequence();
        
        departSequence();
        
    }

    private void airportSequence() {
        Passenger passenger = new Passenger(this);
        Thread threadPassenger = new Thread(passenger);
        threadPassenger.start();
        RefuelTruck.add(this);
        clean();
        resupply();
        try {
            threadPassenger.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (refueled == false) {
        }
    }

    private void requestForLanding() {
        System.out.println(ANSI_CYAN_BACKGROUND + "Plane Thread: " + this.getPlaneName() + " request for landing." + ANSI_RESET);
        atc.addLanding(this);

    }

    private void acuireResourceForLanding() {
        System.out.println(ANSI_CYAN_BACKGROUND + "Plane Thread: " + this.getPlaneName() + " Attempt to accquire a gate and runway." + ANSI_RESET);
        atc.acquireGateAndRunway();
    }

    private void landingSequence() {
        System.out.println(ANSI_CYAN_BACKGROUND + "Plane Thread: " + this.getPlaneName() + " Landing" + ANSI_RESET);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(ANSI_CYAN_BACKGROUND + "Plane Thread: " + this.getPlaneName() + " Landed and Docked at Gate!" + ANSI_RESET);
        atc.runway.release();
    }

    private void departSequence() {
        atc.acquireRunway(this);
        System.out.println(ANSI_CYAN_BACKGROUND + "Plane Thread: " + this.getPlaneName() + " Departing" + ANSI_RESET);
        try {
            Thread.sleep(1000);
            System.out.println(ANSI_CYAN_BACKGROUND + "Plane Thread: " + this.getPlaneName() + " Successfully departed" + ANSI_RESET);
            atc.gate.release();
            atc.runway.release();
        } catch (InterruptedException ex) {
            Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, ex);
        }
        ATC.planeLeft++;
    }

    private void resupply() {
        System.out.println(ANSI_RED_BACKGROUND + "Plane Thread: " + this.planeName + " is resupplying." + ANSI_RESET);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(ANSI_RED_BACKGROUND + "Plane Thread: " + this.planeName + " finished resupplying!" + ANSI_RESET);
    }

    private void clean() {
        System.out.println(ANSI_RED_BACKGROUND + "Plane Thread: " + this.planeName + " is cleaning." + ANSI_RESET);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(ANSI_RED_BACKGROUND + "Plane Thread: " + this.planeName + " finished cleaning!" + ANSI_RESET);
    }

}
