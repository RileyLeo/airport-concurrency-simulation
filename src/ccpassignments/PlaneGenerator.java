package ccpassignments;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class PlaneGenerator extends Thread {

    ATC atc;

    public PlaneGenerator(ATC atc) {
        this.atc = atc;
    }

    public void run() {
        for (int i = 1; i <= ATC.maxPlane; i++) {
            Plane plane = new Plane(atc);
            plane.setArrivalTime(new Date());
            Thread threadPlane = new Thread(plane);
            plane.index = i - 1;
            plane.setPlaneName("Plane " + i);
            threadPlane.start();
            try {
                Thread.sleep(new Random().nextInt(3000));
            } catch (InterruptedException iex) {
                iex.printStackTrace();
            }
        }
        return;
    }

}
