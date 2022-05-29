
package ccpassignments;


public class main {

   
    public static void main(String[] args) {
        
        Report.sizeDefination();
        ATC atc = new ATC();
        PlaneGenerator planeGen = new PlaneGenerator(atc);
        RefuelTruck refuelTruck = new RefuelTruck();
        
        Thread threadPlaneGen = new Thread(planeGen);
        Thread threadRefuelTruck = new Thread(refuelTruck);
        
        threadPlaneGen.start();
        threadRefuelTruck.start();
        
        while (ATC.planeLeft != ATC.maxPlane){          
        }
        System.out.println("All planes have departed!");
        Report.printReport();
    }
    
}
