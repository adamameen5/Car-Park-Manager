

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class BambaCarParkManager implements CarParkManager {

    private ArrayList<Vehicle> listOfVehicle = new ArrayList<Vehicle>();
    private static BambaCarParkManager instance = null;
    private double chargePerHour = 300;
    private double addCharge = 100;
    private double maxCharge = 3000;
    private int addFromthisHour = 3;

    private int totalAvailableSlots = 0;
    private int availableSlots = totalAvailableSlots;

    private final int TOTAL_SLOTS_IN_GF = 80; //Total number of slots in ground floor (constant variable)
    private int availableSlotsInGf = TOTAL_SLOTS_IN_GF; //No of available slots in ground floor

    private final int TOTAL_SLOTS_IN_FF = 60; //Total number of slots in first floor (constant variable)
    private int availableSlotsInFf = TOTAL_SLOTS_IN_FF; //No of available slots in first floor

    private final int TOTAL_SLOTS_IN_SF = 70; //Total number of slots in second floor (constant variable)
    private int availableSlotsInSf = TOTAL_SLOTS_IN_SF; //No of available slots in second floor

    //Initially all slots will be free/empty, therefore totalAvailableSlotsInAllFloors will be equal
    //to the total slots in all floors
    private int totalAvailableSlotsInAllFloors = TOTAL_SLOTS_IN_GF + TOTAL_SLOTS_IN_FF + TOTAL_SLOTS_IN_SF;

    //Queues to store vehicles parked in each floor
    private Queue<Vehicle> vehiclesInGF = new LinkedList<Vehicle>();
    private Queue<Vehicle> vehiclesInFF = new LinkedList<Vehicle>();
    private Queue<Vehicle> vehiclesInSF = new LinkedList<Vehicle>();

    //Queues to store the vehicles of each type waiting to be parked
    private Queue<Vehicle> motorbikeQueue = new LinkedList<Vehicle>();
    private Queue<Vehicle> carQueue = new LinkedList<Vehicle>();
    private Queue<Vehicle> vanQueue = new LinkedList<Vehicle>();

    //private constructor
    private BambaCarParkManager() {
    }

    //method which returns an object of same type
    public static BambaCarParkManager getInstance() {
        if (instance == null) {
            synchronized (BambaCarParkManager.class) {
                if (instance == null) {
                    instance = new BambaCarParkManager();
                }
            }
        }
        return instance;
    }


    @Override
    public synchronized void addVehicle(Vehicle obj, String floor) {

        //int numOfMotorbikes = 0;
        int numOfMotorbikesCurrentlyParked = 0;
        int numOfMotorbikesCurrentlyParkedInGF = 0;
        int numOfMotorbikesCurrentlyParkedInFF = 0;
        boolean vehicleParked = false;

        //check whether the vehicle is already parked or not
        /*for (Vehicle item : listOfVehicle) {
            if (item.getIdPlate().equals(obj.getIdPlate())) {
                System.out.println("This vehicle is already parked.");
                return;
            }
        }*/

        //this section will control the entry of the vehicles to the carpark
        if (obj instanceof Van || obj instanceof Car) {
            while (((availableSlotsInGf) < 1) && (floor.equals("Ground"))){
                try {
                    wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            for (Vehicle item : listOfVehicle) {
                if (item instanceof MotorBike) {
                    numOfMotorbikesCurrentlyParked++;
                }
            }
            while ((availableSlotsInGf < 1) && ((numOfMotorbikesCurrentlyParked%3) == 0)){
                try {
                    wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        //This is where the vehicle is added to a parking lot
        if (obj instanceof Van) {
                if (availableSlotsInGf >= 2){
                    listOfVehicle.add(obj);
                    availableSlotsInGf -= 2;
                    vehiclesInGF.offer(obj);
                    printVehicleAddedMsg("Ground",obj,availableSlotsInGf);
                    vehicleParked = true;
                } else if (availableSlotsInFf >= 2){
                    listOfVehicle.add(obj);
                    availableSlotsInFf -= 2;
                    vehiclesInFF.offer(obj);
                    printVehicleAddedMsg("First",obj,availableSlotsInFf);
                    vehicleParked = true;
                } else if (availableSlotsInSf >= 2){
                    listOfVehicle.add(obj);
                    availableSlotsInSf -= 2;
                    vehiclesInSF.offer(obj);
                    printVehicleAddedMsg("Second",obj,availableSlotsInSf);
                    vehicleParked = true;
                }
        }

        if (obj instanceof Car) {
            if (availableSlotsInGf >= 1){
                listOfVehicle.add(obj);
                availableSlotsInGf --;
                vehiclesInGF.offer(obj);
                printVehicleAddedMsg("Ground",obj,availableSlotsInGf);
                vehicleParked = true;
            } else if (availableSlotsInFf >= 1){
                listOfVehicle.add(obj);
                availableSlotsInFf --;
                vehiclesInFF.offer(obj);
                printVehicleAddedMsg("First",obj,availableSlotsInFf);
                vehicleParked = true;
            } else if (availableSlotsInSf >= 1){
                listOfVehicle.add(obj);
                availableSlotsInSf --;
                vehiclesInSF.offer(obj);
                printVehicleAddedMsg("Second",obj,availableSlotsInSf);
                vehicleParked = true;
            }
        }

        if (obj instanceof MotorBike) {
            for (Vehicle vehicle:vehiclesInGF){
                if(vehicle instanceof MotorBike){
                    numOfMotorbikesCurrentlyParkedInGF++;
                }
            }
            for (Vehicle vehicle:vehiclesInFF){
                if(vehicle instanceof MotorBike){
                    numOfMotorbikesCurrentlyParkedInFF++;
                }
            }
            if (availableSlotsInGf == 0 && (numOfMotorbikesCurrentlyParkedInGF%3 != 0)){
                listOfVehicle.add(obj);
                vehiclesInGF.offer(obj);
                printVehicleAddedMsg("Ground",obj,availableSlotsInGf);
                vehicleParked = true;
            } else if (availableSlotsInGf > 0 && (numOfMotorbikesCurrentlyParkedInGF%3 == 0)){
                listOfVehicle.add(obj);
                availableSlotsInGf --;
                vehiclesInGF.offer(obj);
                printVehicleAddedMsg("Ground",obj,availableSlotsInGf);
                vehicleParked = true;
            } else if (availableSlotsInGf > 0 && (numOfMotorbikesCurrentlyParkedInGF%3 != 0)){
                listOfVehicle.add(obj);
                vehiclesInGF.offer(obj);
                printVehicleAddedMsg("Ground",obj,availableSlotsInGf);
                vehicleParked = true;
            } else if (availableSlotsInFf == 0 || (numOfMotorbikesCurrentlyParkedInFF%3 != 0)){
                listOfVehicle.add(obj);
                vehiclesInFF.offer(obj);
                printVehicleAddedMsg("First",obj,availableSlotsInFf);
                vehicleParked = true;
            } else if (availableSlotsInFf > 0 && (numOfMotorbikesCurrentlyParkedInFF%3 == 0)){
                listOfVehicle.add(obj);
                availableSlotsInFf--;
                vehiclesInFF.offer(obj);
                printVehicleAddedMsg("First",obj,availableSlotsInFf);
                vehicleParked = true;
            } else if (availableSlotsInFf > 0 && (numOfMotorbikesCurrentlyParkedInFF%3 != 0)){
                listOfVehicle.add(obj);
                vehiclesInFF.offer(obj);
                printVehicleAddedMsg("First",obj,availableSlotsInFf);
                vehicleParked = true;
            }
        }

        if (vehicleParked){
            System.out.println("****************************");
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notifyAll();

        // Check whether there is sufficient space available for any vehicle to park
        /*if (availableSlotsInFf > 0 && availableSlots <= totalAvailableSlots) {
            if (obj instanceof Van) {
                if (availableSlots >= 2) {
                    listOfVehicle.add(obj);
                    availableSlots -= 2;
                    System.out.println("Available slots : " + availableSlots);
                    System.out.println("\n");
                } else {
                    System.out.println("Sorry..There are no slots available to park your Van." + "\n");
                    System.out.println("\n");
                }
            }
            if (obj instanceof Car) {
                listOfVehicle.add(obj);
                availableSlots--;
                System.out.println("Available slots : " + availableSlots);
                System.out.println("\n");
            }
            if (obj instanceof MotorBike) {
                for (Vehicle item : listOfVehicle) {
                    if (item instanceof MotorBike) {
                        numOfMotorbikes++;
                    }
                }
                if (numOfMotorbikes % 3 == 0) {
                    availableSlots--;
                }
                listOfVehicle.add(obj);
                System.out.println("Available slots : " + availableSlots);
                System.out.println("\n");
            }
        } else {
            System.out.println("Sorry...There is not enough space available for parking...");
            System.out.println("\n");
        }*/
    }

    //region Sir's Code
    // /*@Override
    //    public void addVehicle(Vehicle obj) {
    //        //check whether the vehicle is already parked or not
    //        for(Vehicle item : listOfVehicle) {
    //            if(item.equals(obj)) {
    //                System.out.println("This vehicle is already parked.");
    //                return;
    //            }
    //        }
    //        // Check whether there are sufficient space available for any vehicle to park
    //        if(listOfVehicle.size()<20) {
    //            if(obj instanceof Van ) {
    //                if(listOfVehicle.size()<19) {
    //                    listOfVehicle.add(obj);
    //                    availableSlots -=2;
    //                    System.out.println("Available slots : "+availableSlots);
    //                    System.out.println("\n");
    //                }else {
    //                    System.out.println("Sorry..There are no slots available to park your Van."+"\n");
    //                }
    //            }
    //            if(obj instanceof MotorBike || obj instanceof Car) {
    //                listOfVehicle.add(obj);
    //                availableSlots --;
    //                System.out.println("Available slots : "+availableSlots);
    //            }
    //        }else {
    //            System.out.println("Sorry...There are not space availble for parking...");
    //        }
    //    }*/
    // endregion


    public synchronized void removeVehicle(String floorLevel){

        boolean vehicleRemoved = false;
        int totalAvailableSlotsInAllFloors = availableSlotsInGf + availableSlotsInFf + availableSlotsInSf;
        int TOTAL_SLOTS = TOTAL_SLOTS_IN_GF + TOTAL_SLOTS_IN_FF + TOTAL_SLOTS_IN_SF;
        int numOfMotorbikesCurrentlyParked = 0;
        int bikeListIndex = 0;
        String removedVehicleID = "";

        while (totalAvailableSlotsInAllFloors == TOTAL_SLOTS){
            try {
                //if there are no vehicles parked in any of the slots, then exit thread has to wait
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //remove vehicles from the ground floor
        if (floorLevel.equals("Ground") && (availableSlotsInGf < TOTAL_SLOTS_IN_GF)){
            Vehicle vehicleToBeRemoved = vehiclesInGF.peek();
            removedVehicleID = vehicleToBeRemoved.getIdPlate();

            if (vehicleToBeRemoved instanceof Car){
                availableSlotsInGf++;
                printVehicleRemovedMsg("Ground",vehicleToBeRemoved,availableSlotsInGf);
                vehiclesInGF.poll();
                vehicleRemoved = true;
            }
            if (vehicleToBeRemoved instanceof Van){
                availableSlotsInGf +=2;
                printVehicleRemovedMsg("Ground",vehicleToBeRemoved,availableSlotsInGf);
                vehiclesInGF.poll();
                vehicleRemoved = true;
            }
            if (vehicleToBeRemoved instanceof MotorBike){
                for (Vehicle item : listOfVehicle) {
                    if (item instanceof MotorBike) {
                        numOfMotorbikesCurrentlyParked++;
                    }
                }
                if (((numOfMotorbikesCurrentlyParked-1)%3) == 0) {
                    availableSlotsInGf++;
                    printVehicleRemovedMsg("Ground", vehicleToBeRemoved, availableSlotsInGf);
                    vehiclesInGF.poll();
                    vehicleRemoved = true;
                }
            }
        }

        //remove vehicles from the First floor
        if (floorLevel.equals("First") && (availableSlotsInFf < TOTAL_SLOTS_IN_FF)){
            Vehicle vehicleToBeRemoved = vehiclesInFF.peek();
            removedVehicleID = vehicleToBeRemoved.getIdPlate();

            if (vehicleToBeRemoved instanceof Car){
                availableSlotsInFf++;
                printVehicleRemovedMsg("First",vehicleToBeRemoved,availableSlotsInFf);
                vehiclesInFF.poll();
                vehicleRemoved = true;
            }
            if (vehicleToBeRemoved instanceof Van){
                availableSlotsInFf +=2;
                printVehicleRemovedMsg("First",vehicleToBeRemoved,availableSlotsInFf);
                vehiclesInFF.poll();
                vehicleRemoved = true;
            }
            if (vehicleToBeRemoved instanceof MotorBike){
                for (Vehicle item : listOfVehicle) {
                    if (item instanceof MotorBike) {
                        numOfMotorbikesCurrentlyParked++;
                    }
                }
                if (((numOfMotorbikesCurrentlyParked-1)%3) == 0) {
                    availableSlotsInFf++;
                    printVehicleRemovedMsg("First", vehicleToBeRemoved, availableSlotsInFf);
                    vehiclesInFF.poll();
                    vehicleRemoved = true;
                }
            }
        }

        //remove vehicles from the Second floor
        if (floorLevel.equals("Second") && (availableSlotsInFf < TOTAL_SLOTS_IN_FF)){
            Vehicle vehicleToBeRemoved = vehiclesInSF.peek();
            removedVehicleID = vehicleToBeRemoved.getIdPlate();

            if (vehicleToBeRemoved instanceof Car){
                availableSlotsInFf++;
                printVehicleRemovedMsg("Second",vehicleToBeRemoved,availableSlotsInFf);
                vehiclesInFF.poll();
                vehicleRemoved = true;
            }
            if (vehicleToBeRemoved instanceof Van){
                availableSlotsInFf +=2;
                printVehicleRemovedMsg("Second",vehicleToBeRemoved,availableSlotsInFf);
                vehiclesInFF.poll();
                vehicleRemoved = true;
            }
        }

        if (vehicleRemoved){
            removeVehicleFromList(removedVehicleID);
            System.out.println("****************************");
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notifyAll();
    }

    @Override
    public void deleteVehicle(String IdPlate) {

        int numOfMotorbikes = 0;
        boolean vehicleFound = false;
        int listIndex = 0;

        for (Vehicle item : listOfVehicle) {
            //Checking for a particular vehicle with its' plate ID
            if (item.getIdPlate().equals(IdPlate)) {
                System.out.println("Vehicle Found.");
                if (item instanceof Van) {
                    availableSlots += 2;
                    System.out.println("Space cleared after deleting a Van.\nAvailable Slots : "
                            + availableSlots);
                    System.out.println("\n");
                    vehicleFound = true;
                    listOfVehicle.remove(listIndex);
                    break;
                }
                if (item instanceof Car) {
                    availableSlots++;
                    System.out.println("Space cleared after deleting a Car.\nAvailable Slots : "
                            + availableSlots);
                    System.out.println("\n");
                    vehicleFound = true;
                    listOfVehicle.remove(listIndex);
                    break;
                } else {
                    for (Vehicle tempItem : listOfVehicle) {
                        if (tempItem instanceof MotorBike) {
                            numOfMotorbikes++;
                        }
                    }

                    int numOfBikesAfterDeleting = numOfMotorbikes - 1;

                    if (numOfBikesAfterDeleting % 3 == 0) {
                        availableSlots++;
                    }
                    System.out.println("Space cleared after deleting a Motorbike.\nAvailable Slots : "
                            + availableSlots);
                    System.out.println("\n");
                    vehicleFound = true;
                    listOfVehicle.remove(listIndex);
                    break;
                }
            }
            listIndex ++;
        }

        if (!vehicleFound) {
            System.out.println("Vehicle not found.");
            System.out.println("\n");
        }
    }


    @Override
    public void printcurrentVehicles() {
        Collections.sort(listOfVehicle, Collections.reverseOrder());

        //Collections.reverse(listOfVehicle);
        int i = 1;
        if (listOfVehicle.size() != 0) {
            for (Vehicle item : listOfVehicle) {
                if (item instanceof Van) {
                    System.out.println(i + ". Vehicle Type is a Van");
                } else if (item instanceof MotorBike) {
                    System.out.println(i + ". Vehicle Type is a MotorBike");
                } else {
                    System.out.println(i + ". Vehicle Type is a Car.");
                }
                System.out.println("******************");
                System.out.println("ID Plate : " + item.getIdPlate());
                System.out.println("Entry Time : "
                        + item.getEntryDate().getHours() + ":" + item.getEntryDate().getMinutes()
                        + ":" + item.getEntryDate().getSeconds() + "-" + item.getEntryDate().getDate()
                        + "/" + item.getEntryDate().getMonth() + "/" + item.getEntryDate().getYear());
                System.out.println("\n");
                i++;
            }
        } else {
            System.out.println("..............................................");
            System.out.println("The currently parked car is not available.\nThe car park is empty.");
            System.out.println("..............................................");
            System.out.println("\n");
        }

    }

    @Override
    public void printLongestPark() {

        if (listOfVehicle.size() != 0) {
            //sort to the ascending order
            Collections.sort(listOfVehicle);
            System.out.println("The longest parked vehicle is : ");
            System.out.println("................................................");
            System.out.println("ID Plate : " + listOfVehicle.get(0).getIdPlate());
            if (listOfVehicle.get(0) instanceof Car) {
                System.out.println("Vehicle Type is a Car.");
            } else if (listOfVehicle.get(0) instanceof Van) {
                System.out.println("Vehicle Type is a Van.");
            } else {
                System.out.println("Vehicle Type is a MotorBike.");
            }
            System.out.println("Parked Time : " + listOfVehicle.get(0).getEntryDate().getHours()
                    + ":" + listOfVehicle.get(0).getEntryDate().getMinutes()
                    + ":" + listOfVehicle.get(0).getEntryDate().getSeconds());
            System.out.println("Parked Date  : " + listOfVehicle.get(0).getEntryDate().getDate()
                    + "/" + listOfVehicle.get(0).getEntryDate().getMonth()
                    + "/" + listOfVehicle.get(0).getEntryDate().getYear());
            System.out.println("\n");
        } else {
            System.out.println("..............................................");
            System.out.println("The longest available car parked is not found.\nThe car park is empty.");
            System.out.println("..............................................");
            System.out.println("\n");
        }

    }

    @Override
    public void printLatestPark() {
        // sort to the descending order
        if (listOfVehicle.size() != 0) {
            Collections.sort(listOfVehicle, Collections.reverseOrder());
            System.out.println("The latest parked vehicle is : ");
            System.out.println("..............................................");
            System.out.println("ID Plate : " + listOfVehicle.get(0).getIdPlate());
            if (listOfVehicle.get(0) instanceof Car) {
                System.out.println("Vehicle Type is a Car.");
            } else if (listOfVehicle.get(0) instanceof Van) {
                System.out.println("Vehicle Type is a Van.");
            } else {
                System.out.println("Vehicle Type is a MotorBike.");
            }
            System.out.println("Parked Time : " + listOfVehicle.get(0).getEntryDate().getHours()
                    + ":" + listOfVehicle.get(0).getEntryDate().getMinutes()
                    + ":" + listOfVehicle.get(0).getEntryDate().getSeconds());
            System.out.println("Parked Date  : " + listOfVehicle.get(0).getEntryDate().getDate()
                    + "/" + listOfVehicle.get(0).getEntryDate().getMonth()
                    + "/" + listOfVehicle.get(0).getEntryDate().getYear());
            System.out.println("\n");
        } else {
            System.out.println("..............................................");
            System.out.println("The latest parked car is not available.\nThe car park is empty.");
            System.out.println("..............................................");
            System.out.println("\n");
        }

    }


    @Override
    public void printVehicleByDay(DateTime givenDate) {
        if (listOfVehicle.size() != 0) {
            for (Vehicle item : listOfVehicle) {
                if (givenDate.getYear() == item.getEntryDate().getYear() &&
                        givenDate.getMonth() == item.getEntryDate().getMonth() &&
                        givenDate.getDate() == item.getEntryDate().getDate()) {

                    System.out.println("ID Plate : " + item.getIdPlate());

                    System.out.println("Parked Date and Time : " + item.getEntryDate().getDate() + "/" +
                            item.getEntryDate().getMonth() + "/" + item.getEntryDate().getHours() + "-"
                            + item.getEntryDate().getHours() + ":" + item.getEntryDate().getMinutes()
                            + ":" + item.getEntryDate().getYear());

                    if (item instanceof Van) {
                        System.out.println("Vehicle Type is a Van");
                    } else if (item instanceof MotorBike) {
                        System.out.println("Vehicle Type is a Motor Bike.");
                    } else {
                        System.out.println("Vehicle Type is a Car.");
                    }
                    System.out.println("--------------------------");
                    System.out.println("\n");
                } else {
                    System.out.println("..............................................");
                    System.out.println("No records to show on the given date.");
                    System.out.println("..............................................");
                    System.out.println("\n");
                }
            }
        } else {
            System.out.println("..............................................");
            System.out.println("No records to show on the given date.\nThe car park is empty.");
            System.out.println("..............................................");
            System.out.println("\n");
        }
    }

    @Override
    public void printVehiclePercentage() {
        int numCars = 0;
        int numBikes = 0;
        int numVans = 0;
        double carPercentage = 0.0;
        double bikePercentage = 0.0;
        double vanPercentage = 0.0;

        for (Vehicle item : listOfVehicle) {
            if (item instanceof Car) {
                numCars++;
            } else if (item instanceof MotorBike) {
                numBikes++;
            } else {
                numVans++;
            }
        }

        if (listOfVehicle.size() != 0) {
            carPercentage = (Double.valueOf(numCars) / listOfVehicle.size() * 100);
            bikePercentage = (Double.valueOf(numBikes) / listOfVehicle.size() * 100);
            vanPercentage = (Double.valueOf(numVans) / listOfVehicle.size() * 100);
        }

        System.out.println("..............................................");
        System.out.printf("Car Percentage is : %.2f ", carPercentage);
        System.out.printf("\nBike Percentage is : %.2f ", bikePercentage);
        System.out.printf("\nVan Percentage is : %.2f ", vanPercentage);
        System.out.println("\n..............................................");
        System.out.println("\n");
    }

    @Override
    public BigDecimal calculateChargers(String plateID, DateTime currentTime) {
        boolean found = false;
        BigDecimal charges = null;
        for (Vehicle item : listOfVehicle) {
            if (item.getIdPlate().equals(plateID)) {
                System.out.println("Vehicle found.");
                //vehicle parked time
                System.out.println("Parked Time : " + item.getEntryDate().getDate() + "/"
                        + item.getEntryDate().getMonth() + "/" + item.getEntryDate().getDate()
                        + "-" + item.getEntryDate().getHours() + ":" + item.getEntryDate().getMinutes()
                        + ":" + item.getEntryDate().getSeconds());
                //making the charges
                found = true;
                DateTime entryDateTime = item.getEntryDate();
                int differenceInSeconds = currentTime.compareTo(entryDateTime);
                double differenceInHours = differenceInSeconds / (60.0 * 60.0);

                double dayCharge = 0;
                double hourCharge = 0;
                double totalCost = 0;
                double days = differenceInHours / 24;

                if (days > 1) {
                    dayCharge = maxCharge;
                }
                if (differenceInHours >= 3) {
                    double additional = (differenceInHours - addFromthisHour);
                    hourCharge = (additional * addCharge) + (addFromthisHour * chargePerHour);
                    System.out.printf("hour Charge : %.2f", hourCharge);
                } else if (differenceInHours < 1) {
                    hourCharge = chargePerHour;
                } else {
                    hourCharge = (differenceInHours * chargePerHour);
                }

                totalCost = dayCharge + hourCharge;
                BigDecimal vehicleCharge = new BigDecimal(totalCost);
                System.out.printf("Total charge for the vehicle is LKR %.2f", vehicleCharge);
                System.out.println("\n");
            }
        }
        if (!found) {
            System.out.println("Vehicle not found\n");
        }
        return charges;
    }


    public int getNumOfVehicles() {
        if (listOfVehicle.size() != 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public void printVehicleAddedMsg(String floor, Vehicle vehicle, int slotsRem){
        if (floor.equals("Ground")){
            System.out.println("Vehicle number : "+ vehicle.getIdPlate() + " is parked in the Ground floor.");
            System.out.println("Remaining slots in the Ground floor : " + slotsRem);
        } else if (floor.equals("First")){
            System.out.println("Vehicle number : "+ vehicle.getIdPlate() + " is parked in the First floor.");
            System.out.println("Remaining slots in the First floor : " + slotsRem);
        } else if (floor.equals("Second")){
            System.out.println("Vehicle number : "+ vehicle.getIdPlate() + " is parked in the Second floor.");
            System.out.println("Remaining slots in the Second floor : " + slotsRem);
        }
    }

    public void printVehicleRemovedMsg(String floor, Vehicle vehicle, int slotsRem){
        if (floor.equals("Ground")){
            System.out.println("Vehicle number : "+ vehicle.getIdPlate() + " is removed from the Ground floor.");
            System.out.println("Remaining slots in the Ground floor : " + slotsRem);
        } else if (floor.equals("First")){
            System.out.println("Vehicle number : "+ vehicle.getIdPlate() + " is removed from the First floor.");
            System.out.println("Remaining slots in the First floor : " + slotsRem);
        } else if (floor.equals("Second")){
            System.out.println("Vehicle number : "+ vehicle.getIdPlate() + " is removed from the Second floor.");
            System.out.println("Remaining slots in the Second floor : " + slotsRem);
        }
    }

    public void removeVehicleFromList(String vehicleID){
        int vehicleListIndex = 0;

        for (Vehicle item : listOfVehicle) {
            if ( item.getIdPlate().equals(vehicleID) ){
                listOfVehicle.remove(vehicleListIndex);
                break;
            }
            vehicleListIndex++;
        }
    }

    public Queue<Vehicle> getCarQueue(){
        return carQueue;
    }

    public Queue<Vehicle> getVanQueue(){
        return vanQueue;
    }

    public Queue<Vehicle> getMotorbikeQueue(){
        return motorbikeQueue;
    }
}
