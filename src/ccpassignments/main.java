
package ccpassignments;


public class main {

   
    public static void main(String[] args) {
        
        ATC atc = new ATC();
        PlaneGenerator planeGen = new PlaneGenerator(atc);
        RefuelTruck refuelTruck = new RefuelTruck();
        
        Thread threadPlaneGen = new Thread(planeGen);
        Thread threadRefuelTruck = new Thread(refuelTruck);
        
        threadPlaneGen.start();
        threadRefuelTruck.start();
    }
    
}
