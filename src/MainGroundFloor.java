import javax.print.attribute.standard.RequestingUserName;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class MainGroundFloor {

    private static BambaCarParkManager bambaCarParkManager = BambaCarParkManager.getInstance();

    public static void main(String[] args) {

        //Vehicles list for Ground Floor
        Queue<Vehicle> carQueueGF = new LinkedList<Vehicle>();
        Queue<Vehicle> vanQueueGF = new LinkedList<Vehicle>();
        Queue<Vehicle> motorbikeQueueGF = new LinkedList<Vehicle>();

        //Get current system date and time
        DateTime dateTime = new DateTime(2020,3,20,10,10,10);

        //Creating car objects and adding them to the queue to be parked in the ground floor
        Car car1 = new Car("car-gf1","Honda","Fit",dateTime,4, Color.BLUE);
        Car car2 = new Car("car-gf2","Toyota","Camry",dateTime,4, Color.CYAN);
        Car car3 = new Car("car-gf3","Benz","MG",dateTime,4, Color.DARK_GRAY);
        Car car4 = new Car("car-gf4","Toyota","Camry",dateTime,4, Color.CYAN);
        Car car5 = new Car("car-gf5","Benz","MG",dateTime,4, Color.DARK_GRAY);
        carQueueGF.offer(car1);
        carQueueGF.offer(car2);
        carQueueGF.offer(car3);
        carQueueGF.offer(car4);
        carQueueGF.offer(car5);

        //Creating van objects and adding them to the queue to be parked in the ground floor
        Van van1 = new Van("van-gf1","Toyota","KDH",dateTime,30.0);
        Van van2 = new Van("van-gf2","Nissan","KMKH",dateTime,60.0);
        Van van3 = new Van("van-gf3","Nissan","KMKH",dateTime,60.0);
        Van van4 = new Van("van-gf4","Toyota","KDH",dateTime,30.0);
        Van van5 = new Van("van-gf5","Nissan","KMKH",dateTime,60.0);
        Van van6 = new Van("van-gf6","Nissan","KMKH",dateTime,60.0);
        vanQueueGF.offer(van1);
        vanQueueGF.offer(van2);
        vanQueueGF.offer(van3);
        vanQueueGF.offer(van4);
        vanQueueGF.offer(van5);
        vanQueueGF.offer(van6);

        //Creating motorbike objects and adding them to the queue to be parked in the ground floor
        MotorBike mbk1 = new MotorBike("mbk-gf1","Yamaha","FZ",dateTime,"500cc");
        MotorBike mbk2 = new MotorBike("mbk-gf2","Honda","Dio",dateTime,"110cc");
        motorbikeQueueGF.offer(mbk1);
        motorbikeQueueGF.offer(mbk2);

        //Creating the threads for ground floor (departure and arrival) gates
        Runnable gfArrival = new Arrival(bambaCarParkManager,"Ground",carQueueGF,vanQueueGF,motorbikeQueueGF);
        Runnable gfDeparture = new Departure(bambaCarParkManager,"Ground", (carQueueGF.size() + vanQueueGF.size() + motorbikeQueueGF.size()));

        //Creating a thread array of size 50
        Thread[] threads = new Thread[50];

        //Assigning maximum priority for the northern gates
        Thread northernGate1 = new Thread(gfArrival,"Ground Floor - Northern Entry Gate 1");
        Thread northernGate2 = new Thread(gfDeparture,"Ground Floor - Northern Entry Gate 2");
        northernGate1.setPriority(Thread.MAX_PRIORITY);
        northernGate2.setPriority(Thread.MAX_PRIORITY);

        //Assigning the northern gates to the first few threads
        threads[0] = northernGate1;
        threads[1] = northernGate2;

        //Initializing the threads for other enter and exit gate in ground floor
        threads[2] = new Thread(gfArrival,"Ground Floor - Western Entry Gate 1");
        threads[3] = new Thread(gfDeparture,"Ground Floor - Western Exit Gate 1");

        threads[4] = new Thread(gfArrival,"Ground Floor - Southern Entry Gate 1");
        threads[5] = new Thread(gfDeparture,"Ground Floor - Southern Exit Gate 1");

        threads[6] = new Thread(gfArrival,"Ground Floor - Eastern Entry Gate 1");
        threads[7] = new Thread(gfDeparture,"Ground Floor - Eastern Exit Gate 1");


        //Vehicles list for first Floor
        //--this test will have zero vehicles to be parked in the first floor
        Queue<Vehicle> carQueueFF = new LinkedList<Vehicle>();
        Queue<Vehicle> vanQueueFF = new LinkedList<Vehicle>();
        Queue<Vehicle> motorbikeQueueFF = new LinkedList<Vehicle>();

        //Creating the threads for first floor (entry and exit) gates
        Runnable ffArrival = new Arrival(bambaCarParkManager,"First",carQueueFF,vanQueueFF,motorbikeQueueFF);
        Runnable ffDeparture = new Departure(bambaCarParkManager,"First", (carQueueFF.size() + vanQueueFF.size() + motorbikeQueueFF.size()));

        //Initializing the threads for entry and exit gates in first floor
        threads[8] = new Thread(ffArrival,"First Floor - Western Entry Gate 1");
        threads[9] = new Thread(ffArrival,"First Floor - Western Exit Gate 1");

        threads[10] = new Thread(ffDeparture,"First Floor - Eastern Exit Gate 1");
        threads[11] = new Thread(ffDeparture,"First Floor - Eastern Exit Gate 2");


        //Vehicles list for second Floor
        //--this test will have zero vehicles to be parked in the second floor
        Queue<Vehicle> carQueueSF = new LinkedList<Vehicle>();
        Queue<Vehicle> vanQueueSF = new LinkedList<Vehicle>();
        Queue<Vehicle> motorbikeQueueSF = new LinkedList<Vehicle>();


        //Creating the threads for first floor (departure and arrival) gates
        Runnable sfArrivalLift = new Arrival(bambaCarParkManager,"Second",carQueueSF,vanQueueSF,motorbikeQueueSF);
        Runnable sfDepartureLift = new Departure(bambaCarParkManager,"Second", (carQueueSF.size() + vanQueueSF.size() + motorbikeQueueSF.size()));

        //Initializing 12 threads for the lifts to the second floor
        for (int i = 12; i < 24; i++) {
            threads[i] = new Thread(sfArrivalLift, "Second Floor Entry Lift #" + (i-11) + " created");
        }

        for (int i = 24; i < 36; i++) {
            threads[i] = new Thread(sfDepartureLift, "Second Floor Entry Lift #" + (i-23)+ " created");
        }

        //Here, we start all the threads
        for (Thread thread: threads){
            if (thread != null && !thread.isAlive()){
                System.out.println(thread.getName());
                thread.start();
            }
        }
    }
}
