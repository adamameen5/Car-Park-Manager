import java.util.Queue;

public class Arrival implements Runnable{

    private CarParkManager carParkManager;
    private Queue<Vehicle> carQueue;
    private Queue<Vehicle> vanQueue;
    private Queue<Vehicle> motorbikeQueue;

    //Default constructor
    public Arrival(CarParkManager carParkManager, Queue<Vehicle> carQueue, Queue<Vehicle> vanQueue, Queue<Vehicle> motorbikeQueue) {
        this.carParkManager = carParkManager;
        this.carQueue = carQueue;
        this.vanQueue = vanQueue;
        this.motorbikeQueue = motorbikeQueue;
    }

    @Override
    public void run() {

        //Giving highest priority for the cars
        if (carQueue.size() > 0){
            for (int i=0; i<carQueue.size() ; i++){
                this.carParkManager.addVehicle(carQueue.poll());
            }
        }

        //Giving next highest priority for the vans
        if (vanQueue.size() > 0){
            for (int i=0; i<vanQueue.size() ; i++){
                this.carParkManager.addVehicle(vanQueue.poll());
            }
        }

        //Giving the lowest priority for the motorbikes
        if (motorbikeQueue.size() > 0){
            for (int i=0; i<motorbikeQueue.size() ; i++){
                this.carParkManager.addVehicle(motorbikeQueue.poll());
            }
        }
    }
}
