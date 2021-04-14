public class Departure implements Runnable{

    private CarParkManager carParkManager;
    private String floor;
    private int numOfVehiclesInCurrentFloor;

    //Default constructor
    public Departure(CarParkManager carParkManager, String currentFloor, int numOfVehicles) {
        this.carParkManager = carParkManager;
        this.floor = currentFloor;
        this.numOfVehiclesInCurrentFloor = numOfVehicles;
    }

    @Override
    public void run() {

        for  (int i=0 ; i<numOfVehiclesInCurrentFloor ; i++){
            this.carParkManager.removeVehicle(floor);
        }
    }
}
