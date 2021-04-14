import javax.print.attribute.standard.RequestingUserName;
import java.awt.*;
import java.util.Queue;

public class Main {

    private static BambaCarParkManager bambaCarParkManager = BambaCarParkManager.getInstance();

    public static void main(String[] args) {

        //while starting the application, the queues will be empty
        Queue<Vehicle> carQueue = bambaCarParkManager.getCarQueue();
        Queue<Vehicle> vanQueue = bambaCarParkManager.getVanQueue();
        Queue<Vehicle> motorbikeQueue = bambaCarParkManager.getMotorbikeQueue();

        //Get current system date and time
        DateTime dateTime = new DateTime(2020,3,20,10,10,10);

        //Creating car objects and adding them to the queue to be parked
        Car car1 = new Car("car-1111","Honda","Fit",dateTime,4, Color.BLUE);
        Car car2 = new Car("car-2222","Toyota","Camry",dateTime,4, Color.CYAN);
        Car car3 = new Car("car-3333","Benz","MG",dateTime,4, Color.DARK_GRAY);
        Car car4 = new Car("car-4444","Toyota","Camry",dateTime,4, Color.CYAN);
        Car car5 = new Car("car-5555","Benz","MG",dateTime,4, Color.DARK_GRAY);
        carQueue.offer(car1);
        carQueue.offer(car2);
        carQueue.offer(car3);
        carQueue.offer(car4);
        carQueue.offer(car5);

        //Creating van objects and adding them to the queue to be parked
        Van van1 = new Van("van-1111","Toyota","KDH",dateTime,30.0);
        Van van2 = new Van("van-2222","Nissan","KMKH",dateTime,60.0);
        Van van3 = new Van("van-3333","Nissan","KMKH",dateTime,60.0);
        Van van4 = new Van("van-4444","Toyota","KDH",dateTime,30.0);
        Van van5 = new Van("van-5555","Nissan","KMKH",dateTime,60.0);
        Van van6 = new Van("van-6666","Nissan","KMKH",dateTime,60.0);
        vanQueue.offer(van1);
        vanQueue.offer(van2);
        vanQueue.offer(van3);
        vanQueue.offer(van4);
        vanQueue.offer(van5);
        vanQueue.offer(van6);

        //Creating motorbike objects and adding them to the queue to be parked
        MotorBike mbk1 = new MotorBike("mbk-1111","Yamaha","FZ",dateTime,"500cc");
        MotorBike mbk2 = new MotorBike("mbk-2222","Honda","Dio",dateTime,"110cc");
        motorbikeQueue.offer(mbk1);
        motorbikeQueue.offer(mbk2);

        //Creating the threads for concurrent programming (departure and arrival)
        Runnable arrival = new Arrival(bambaCarParkManager,"Ground",carQueue,vanQueue,motorbikeQueue);
        Runnable departure = new Departure(bambaCarParkManager,"Ground", (carQueue.size() + vanQueue.size() + motorbikeQueue.size()));

        /*
        Creating a thread group for the ground floor
         TODO
        */
        ThreadGroup threadForGroundFlr = new ThreadGroup("Ground");

        Thread[] threads = new Thread[26];

        //Assigning maximum priority for the northern gates
        Thread northernGate1 = new Thread(arrival,"Northern Gate 1");
        Thread northernGate2 = new Thread(arrival,"Northern Gate 2");
        northernGate1.setPriority(Thread.MAX_PRIORITY);
        northernGate2.setPriority(Thread.MAX_PRIORITY);

        //Assigning the northern gates to the first few threads
        threads[0] = northernGate1;
        threads[1] = northernGate2;

        //Initializing the threads for other enter and exit gate
        threads[2] = new Thread(arrival,"Ground west gate entry");
        threads[3] = new Thread(departure,"Ground west gate exit");

        threads[4] = new Thread(arrival,"Ground east gate entry");
        threads[5] = new Thread(departure,"Ground east gate exit");

        threads[6] = new Thread(arrival,"Ground South gate entry");
        threads[7] = new Thread(departure,"Ground South gate exit");

        threads[8] = new Thread(arrival,"Ground west gate entry 1");
        threads[9] = new Thread(arrival,"Ground west gate entry 2");

        threads[10] = new Thread(departure,"First floor east gate exit 1");
        threads[11] = new Thread(departure,"First floor east gate exit 2");

        //Here, we start all the threads
        for (Thread thread: threads){
            if (thread != null && !thread.isAlive()){
                System.out.println(thread.getName());
                thread.start();
            }
        }
    }
}
