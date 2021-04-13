public class Departure implements Runnable{

    private CarParkManager carParkManager;

    //Default constructor
    public Departure(CarParkManager carParkManager) {
        this.carParkManager = carParkManager;
    }

    @Override
    public void run() {
        this.carParkManager.removeVehicle("Ground");
        this.carParkManager.removeVehicle("First");
        this.carParkManager.removeVehicle("Second");
    }
}
