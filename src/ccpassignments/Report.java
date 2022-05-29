package ccpassignments;

import java.util.ArrayList;
import java.util.Collections;

public class Report {

    public static ArrayList<Long> landingWaitTime = new ArrayList<Long>();
    public static ArrayList<Long> airportOperationsTime = new ArrayList<Long>();
    public static ArrayList<Long> departureWaitTime = new ArrayList<Long>();

    public static ArrayList<Integer> disembarkPassengerCount = new ArrayList<Integer>();
    public static ArrayList<Integer> embarkPassengerCount = new ArrayList<Integer>();

    public static void printReport() {
        float sum = 0;
        float average;
        System.out.println("=====================================================================================================================================");
        System.out.println("Sanity Check");
        System.out.println("=====================================================================================================================================");
        System.out.println("Landing Wait Time Statistics");
        System.out.println("Max Landing Wait Time:" + Collections.max(landingWaitTime));
        System.out.println("Min Landing Wait Time:" + Collections.min(landingWaitTime));
        for (int i = 0; i < landingWaitTime.size(); i++) {
            sum += landingWaitTime.get(i);
        }
        average = sum / landingWaitTime.size();
        System.out.println("Average Landing Wait Time:" + average);

        System.out.println("=====================================================================================================================================");
        sum = 0;
        System.out.println("Airport Operations Time Statistics");
        System.out.println("Max Airport Operations Wait Time:" + Collections.max(airportOperationsTime));
        System.out.println("Min Airport Operations Wait Time:" + Collections.min(airportOperationsTime));
        for (int i = 0; i < airportOperationsTime.size(); i++) {
            sum += airportOperationsTime.get(i);
        }
        average = sum / airportOperationsTime.size();
        System.out.println("Airport Operations Wait Time:" + average);

        System.out.println("=====================================================================================================================================");
        sum = 0;
        System.out.println("Departure Wait Time Statistics");
        System.out.println("Max Departure Wait Time:" + Collections.max(departureWaitTime));
        System.out.println("Min Departure Wait Time:" + Collections.min(departureWaitTime));
        for (int i = 0; i < departureWaitTime.size(); i++) {
            sum += departureWaitTime.get(i);
        }
        average = sum / departureWaitTime.size();
        System.out.println("Average Departure Wait Time:" + average);
        System.out.println("=====================================================================================================================================");
        System.out.println("Stat Checker");
        for (int i = 0; i < ATC.maxPlane; i++) {
            System.out.println("=====================================================================================================================================");
            int number = i + 1;
            System.out.println("Plane " + number + " All Stats");
            System.out.println("Plane " + number + ": disembarked customer count is " + disembarkPassengerCount.get(i));
            System.out.println("Plane " + number + ": embarked customer count is " + embarkPassengerCount.get(i));
            System.out.println("Plane " + number + ": Landing wait time is " + landingWaitTime.get(i));
            System.out.println("Plane " + number + ": Airport operation time is " + airportOperationsTime.get(i));
            System.out.println("Plane " + number + ": Departure wait time is " + departureWaitTime.get(i));
        }
        System.out.println("=====================================================================================================================================");
    }

    public static void sizeDefination() {
        for (int i = 0; i < ATC.maxPlane; i++) {
            landingWaitTime.add(i, (long) (0));
        }
        for (int i = 0; i < ATC.maxPlane; i++) {
            airportOperationsTime.add(i, (long) (0));
        }
        for (int i = 0; i < ATC.maxPlane; i++) {
            departureWaitTime.add(i, (long) (0));
        }
        for (int i = 0; i < ATC.maxPlane; i++) {
            disembarkPassengerCount.add(i, (int) (0));
        }
        for (int i = 0; i < ATC.maxPlane; i++) {
            embarkPassengerCount.add(i, (int) (0));
        }
    }
}
