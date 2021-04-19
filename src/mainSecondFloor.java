import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class mainSecondFloor {

    private static BambaCarParkManager bambaCarParkManager = BambaCarParkManager.getInstance();


    /*
     * This main method will be executed to test the scenario when both the ground floor and the first floor is full
     * Therefore, for a vehicle to enter the car park, it should either go to the second floor or wait for a vehicle to be
     * removed from either ground floor or first floor to enter the ground floor or first floor respectively
     */
    public static void main(String[] args) {

        //Get current system date and time
        DateTime dateTime = new DateTime(2020,3,20,10,10,10);

        //before filling vehicles to the second floor, there should be no empty slots in the ground floor and first floor
        //which means available slots in ground floor and first floor should be zero
        bambaCarParkManager.fillAllSlotsInGroundFloor(0);
        bambaCarParkManager.fillAllSlotsInFirstFloor(0);


        //In-order to have no empty slots in the ground floor and first floor, we can fill the slots in the ground floor
        //and first floor with cars, just for an example
        Queue<Vehicle> vehiclesParkedInGFQueue = new LinkedList<Vehicle>();

        for (int i=0 ; i< 80 ; i++){
            Car carObjGF = new Car("car-GFParked "+i,"Honda","Fit",dateTime,4, Color.BLUE);
            Car carObjFF = new Car("car-FFParked "+i,"Honda","Fit",dateTime,4, Color.BLUE);
            vehiclesParkedInGFQueue.offer(carObjGF);
        }
        bambaCarParkManager.assignVehiclesInGroundFloor(vehiclesParkedInGFQueue);

        Queue<Vehicle> vehiclesParkedInFFQueue = new LinkedList<Vehicle>();
        for (int i=0 ; i< 60 ; i++){
            Car carObjFF = new Car("car-FFParked "+i,"Honda","Fit",dateTime,4, Color.BLUE);
            vehiclesParkedInFFQueue.offer(carObjFF);
        }
        bambaCarParkManager.assignVehiclesInFirstFloor(vehiclesParkedInFFQueue);

        /* Now we can create vehicle objects to be added to the car park when running this main method, first the
        vehicles assigned to the second thread will be added to a slot bcs the ground floor & first floor is full.
        The program will then wait until the ground floor vehicles leave to add a vehicle to the ground floor */

        //Vehicles list for Ground Floor to add vehicle objects
        Queue<Vehicle> carQueueGF = new LinkedList<Vehicle>();
        Queue<Vehicle> vanQueueGF = new LinkedList<Vehicle>();
        Queue<Vehicle> motorbikeQueueGF = new LinkedList<Vehicle>();


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


        //Vehicles list for first Floor to add vehicle objects
        Queue<Vehicle> carQueueFF = new LinkedList<Vehicle>();
        Queue<Vehicle> vanQueueFF = new LinkedList<Vehicle>();
        Queue<Vehicle> motorbikeQueueFF = new LinkedList<Vehicle>();

        //Creating car objects and adding them to the queue to be parked in the first floor
        Car car6 = new Car("car-ff1","Honda","Fit",dateTime,4, Color.BLUE);
        Car car7 = new Car("car-ff2","Toyota","Camry",dateTime,4, Color.CYAN);
        Car car8 = new Car("car-ff3","Benz","MG",dateTime,4, Color.DARK_GRAY);
        carQueueFF.offer(car6);
        carQueueFF.offer(car7);
        carQueueFF.offer(car8);

        //Creating van objects and adding them to the queue to be parked in the first floor
        Van van7 = new Van("van-ff1","Toyota","KDH",dateTime,30.0);
        Van van8 = new Van("van-ff2","Nissan","KMKH",dateTime,60.0);
        Van van9 = new Van("van-ff3","Nissan","KMKH",dateTime,60.0);
        vanQueueFF.offer(van7);
        vanQueueFF.offer(van8);
        vanQueueFF.offer(van9);

        //Creating motorbike objects and adding them to the queue to be parked in the first floor
        MotorBike mbk3 = new MotorBike("mbk-ff1","Yamaha","FZ",dateTime,"500cc");
        MotorBike mbk4 = new MotorBike("mbk-ff2","Honda","Dio",dateTime,"110cc");
        motorbikeQueueFF.offer(mbk3);
        motorbikeQueueFF.offer(mbk4);

        //Creating the threads for first floor (entry and exit) gates
        Runnable ffArrival = new Arrival(bambaCarParkManager,"First",carQueueFF,vanQueueFF,motorbikeQueueFF);
        Runnable ffDeparture = new Departure(bambaCarParkManager,"First", (carQueueFF.size() + vanQueueFF.size() + motorbikeQueueFF.size()));

        //Initializing the threads for entry and exit gates in first floor
        threads[8] = new Thread(ffArrival,"First Floor - Western Entry Gate 1");
        threads[9] = new Thread(ffArrival,"First Floor - Western Exit Gate 1");

        threads[10] = new Thread(ffDeparture,"First Floor - Eastern Exit Gate 1");
        threads[11] = new Thread(ffDeparture,"First Floor - Eastern Exit Gate 2");


        //Vehicles list for second Floor to add vehicle objects
        Queue<Vehicle> carQueueSF = new LinkedList<Vehicle>();
        Queue<Vehicle> vanQueueSF = new LinkedList<Vehicle>();
        Queue<Vehicle> motorbikeQueueSF = new LinkedList<Vehicle>();

        //Creating car objects and adding them to the queue to be parked in the second floor
        Car car9 = new Car("car-sf1","Honda","Fit",dateTime,4, Color.BLUE);
        Car car10 = new Car("car-sf2","Toyota","Camry",dateTime,4, Color.CYAN);
        Car car11 = new Car("car-sf3","Benz","MG",dateTime,4, Color.DARK_GRAY);
        carQueueSF.offer(car9);
        carQueueSF.offer(car10);
        carQueueSF.offer(car11);

        //Creating van objects and adding them to the queue to be parked in the second floor
        Van van10 = new Van("van-sf1","Toyota","KDH",dateTime,30.0);
        Van van11 = new Van("van-sf2","Nissan","KMKH",dateTime,60.0);
        Van van12 = new Van("van-sf3","Nissan","KMKH",dateTime,60.0);
        vanQueueSF.offer(van10);
        vanQueueSF.offer(van11);
        vanQueueSF.offer(van12);


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
