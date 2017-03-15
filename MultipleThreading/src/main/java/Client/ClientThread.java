package Client;

import static java.lang.Thread.sleep;

/**
 * Created by Alexey on 15.03.2017.
 */
public class ClientThread implements Runnable{

    public void run() {
        System.out.println("run...");
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
