public class Departure implements Runnable{

    private CarParkManager carParkManager;
    private String floor;
    private int numOfVehiclesInQueue;

    //Default constructor
    public Departure(CarParkManager carParkManager, String currentFloor, int numOfVehicles) {
        this.carParkManager = carParkManager;
        this.floor = currentFloor;
        this.numOfVehiclesInQueue = numOfVehicles;
    }

    @Override
    public void run() {

        for  (int i=0 ; i<numOfVehiclesInQueue ; i++){
            this.carParkManager.removeVehicle(floor);
        }
    }
}
