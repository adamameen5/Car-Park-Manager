

import java.math.BigDecimal;
import java.util.Queue;

public interface CarParkManager {
	
	public static final int MAX=20; //Number of slots available in the Car Park
	
	public void addVehicle(Vehicle obj, String floor);
	public void deleteVehicle(String IdPlate);
	public void printcurrentVehicles();
	public void printVehiclePercentage();
	public void printLongestPark();
	public void printLatestPark();
	public void printVehicleByDay(DateTime entryTime);
	public BigDecimal calculateChargers(String plateID, DateTime currentTime);

	//newly added method definitions
	public void removeVehicle(String floorLevel);
	public int getNumOfVehicles();
	public void printVehicleAddedMsg(String floor, Vehicle vehicle, int slotsRem);
	public void printVehicleRemovedMsg(String floor, Vehicle vehicle, int slotsRem);
	public void removeVehicleFromList(String vehicleID);
	public Queue<Vehicle> getCarQueue();
	public Queue<Vehicle> getVanQueue();
	public Queue<Vehicle> getMotorbikeQueue();
}
