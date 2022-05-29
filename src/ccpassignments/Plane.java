package ccpassignments;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Plane extends Thread {

    public int index;
    private String planeName;
    public Boolean refueled = false;
    Date arrivalTime;
    Date departureTime;
    ATC atc;
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_RESET = "\u001B[0m";
    long landingStartTime;
    long landingEndTime;
    long landingExecutionTime;
    long airportStartTime;
    long airportEndTime;
    long airportExecutionTime;
    long departureStartTime;
    long departureEndTime;
    long departureExecutionTime;

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

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    @Override
    public void run() {

        requestForLanding();

        landingSequence();

        airportSequence();

        requestForDeparture();

        departSequence();

    }

    private void requestForLanding() {
//        System.out.println(this.planeName + ": Landing timer start");
        this.landingStartTime = new Date().getTime();
        System.out.println(ANSI_CYAN_BACKGROUND + "Plane Thread: " + this.getPlaneName() + " request for landing." + ANSI_RESET);
        atc.acquireGateAndRunway(this);
        this.landingEndTime = new Date().getTime();
        this.landingExecutionTime = this.landingEndTime - this.landingStartTime;
        Report.landingWaitTime.set(this.index, this.landingExecutionTime);
//        System.out.println(this.planeName + " landing wait time is " + this.landingExecutionTime);
    }

    private void requestForDeparture() {
//        System.out.println(this.planeName + ": Departure timer start");
        this.departureStartTime = new Date().getTime();
        System.out.println(ANSI_CYAN_BACKGROUND + "Plane Thread: " + this.getPlaneName() + " request for departure." + ANSI_RESET);
        atc.acquireRunway(this);
        this.departureEndTime = new Date().getTime();
        this.departureExecutionTime = this.departureEndTime - this.departureStartTime;
        Report.departureWaitTime.set(index, departureExecutionTime);
//        System.out.println(this.planeName + " departure wait time is " + this.departureExecutionTime);
    }

    private void landingSequence() {
        try {
            System.out.println(ANSI_CYAN_BACKGROUND + "Plane Thread: " + this.getPlaneName() + " landing" + ANSI_RESET);
            Thread.sleep(1000);
            System.out.println(ANSI_CYAN_BACKGROUND + "Plane Thread: " + this.getPlaneName() + " landed on the runway!" + ANSI_RESET);
            System.out.println(ANSI_CYAN_BACKGROUND + "Plane Thread: " + this.getPlaneName() + " is coasting to the gate now!" + ANSI_RESET);
            Thread.sleep(1000);
            System.out.println(ANSI_CYAN_BACKGROUND + "Plane Thread: " + this.getPlaneName() + " docked at Gate!" + ANSI_RESET);
            atc.runway.release();
        } catch (InterruptedException ex) {
            Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void departSequence() {
        try {
            System.out.println(ANSI_CYAN_BACKGROUND + "Plane Thread: " + this.getPlaneName() + " coasting to runway." + ANSI_RESET);
            Thread.sleep(1000);
            System.out.println(ANSI_CYAN_BACKGROUND + "Plane Thread: " + this.getPlaneName() + " runway reached!" + ANSI_RESET);
            System.out.println(ANSI_CYAN_BACKGROUND + "Plane Thread: " + this.getPlaneName() + " departing" + ANSI_RESET);
            Thread.sleep(1000);
            System.out.println(ANSI_CYAN_BACKGROUND + "Plane Thread: " + this.getPlaneName() + " successfully departed!" + ANSI_RESET);
            atc.gate.release();
            atc.runway.release();
        } catch (InterruptedException ex) {
            Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setDepartureTime(new Date());
        atc.departureConfirmation(this);
    }

    private void airportSequence() {
//        System.out.println(this.planeName + ": Airport timer start");
        this.airportStartTime = new Date().getTime();
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
        this.airportEndTime = new Date().getTime();
        this.airportExecutionTime = this.airportEndTime - this.airportStartTime;
        Report.airportOperationsTime.set(this.index, this.airportExecutionTime);
//        System.out.println(this.planeName + " airport operation time is " + this.airportExecutionTime);
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
