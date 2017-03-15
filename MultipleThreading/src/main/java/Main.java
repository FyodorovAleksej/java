import Client.ClientThread;
import Client.WorkQueue;

/**
 * Created by Alexey on 15.03.2017.
 */
public class Main {
    public static void main(String[] args) {
        WorkQueue queue = new WorkQueue(1);
        queue.execute(new ClientThread());
        queue.execute(new ClientThread());
        queue.execute(new ClientThread());
        queue.execute(new ClientThread());
        queue.execute(new ClientThread());
        queue.execute(new ClientThread());
        queue.execute(new ClientThread());
        queue.execute(new ClientThread());
        queue.execute(new ClientThread());
    }
}
