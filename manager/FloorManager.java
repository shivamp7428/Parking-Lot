package manager;

import java.util.*;
import model.Floor;

public class FloorManager {

    private FloorManager() {
    }

    ;
    public synchronized void addFloor(HashMap<Integer, Floor> floors, int carPrice, int bikePrice, int truckPrice, int carSpot, int truckSpot, int bikeSpot) {
        Floor newFloor = new Floor(carPrice, bikePrice, truckPrice, carSpot, truckSpot, bikeSpot);
        int id = newFloor.id;
        floors.put(id, newFloor);
        System.out.println("NEW FLOOR ADDED SUCCESSFULLY AND FLOOR ID NUMBER IS :- " + id);
    }

    public void deleteFloor(HashMap<Integer, Floor> floors, int floor_id) {
        floors.get(floor_id).lock.lock();
        try {
            floors.remove(floor_id);
            System.out.println("FLOOR DELETED SUCCESSFULLY AND DELETED FLOOR ID WAS :- " + floor_id);
        } finally {
            floors.get(floor_id).lock.unlock();
        }
    }

    public void updateCarPrice(HashMap<Integer, Floor> floors, int floor_id, int newPrice) {
        floors.get(floor_id).lock.lock();
        try {
            Floor floor = floors.get(floor_id);
            floor.carPrice = newPrice;
            floors.put(floor_id, floor);
            System.out.println("CAR PRICE UPDATED SUCCESSFULLY");
        } finally {
            floors.get(floor_id).lock.unlock();
        }
    }

    public int getSpotPrice(HashMap<Integer, Floor> floors, int floor_id, String vehicleType) {
        floors.get(floor_id).lock.lock();
        try {
            if (vehicleType.equals("CAR")) {
                return floors.get(floor_id).carPrice;
            }
            if (vehicleType.equals("BIKE")) {
                return floors.get(floor_id).bikePrice;
            }
            if (vehicleType.equals("TRUCK")) {
                return floors.get(floor_id).truckPrice;
            }
            System.out.println("SPOT NOT FOUND");
            return -1;
        } finally {
            floors.get(floor_id).lock.unlock();
        }
    }

    public void updateBikePrice(HashMap<Integer, Floor> floors, int floor_id, int newPrice) {
        floors.get(floor_id).lock.lock();
        try {
            Floor floor = floors.get(floor_id);
            floor.bikePrice = newPrice;
            floors.put(floor_id, floor);
            System.out.println("BIKE PRICE UPDATED SUCCESSFULLY");
        } finally {
            floors.get(floor_id).lock.unlock();
        }
    }

    public void updateTruckPrice(HashMap<Integer, Floor> floors, int floor_id, int newPrice) {
        floors.get(floor_id).lock.lock();
        try {
            Floor floor = floors.get(floor_id);
            floor.truckPrice = newPrice;
            floors.put(floor_id, floor);
            System.out.println("TRUCK PRICE UPDATED SUCCESSFULLY");
        } finally {
            floors.get(floor_id).lock.unlock();
        }
    }

    public void updateCarSpot(HashMap<Integer, Floor> floors, int floor_id, int newSpot) {
        floors.get(floor_id).lock.lock();
        try {
            Floor floor = floors.get(floor_id);
            floor.carSpot = newSpot;
            floors.put(floor_id, floor);
            System.out.println("CAR SPOT UPDATED SUCCESSFULLY");
        } finally {
            floors.get(floor_id).lock.unlock();
        }
    }

    public void updateBikeSpot(HashMap<Integer, Floor> floors, int floor_id, int newSpot) {
        floors.get(floor_id).lock.lock();
        try {
            Floor floor = floors.get(floor_id);
            floor.bikeSpot = newSpot;
            floors.put(floor_id, floor);
            System.out.println("BIKE SPOT UPDATED SUCCESSFULLY");
        } finally {
            floors.get(floor_id).lock.unlock();
        }
    }

    public void updateTruckSpot(HashMap<Integer, Floor> floors, int floor_id, int newSpot) {
        floors.get(floor_id).lock.lock();
        try {
            Floor floor = floors.get(floor_id);
            floor.truckSpot = newSpot;
            floors.put(floor_id, floor);
            System.out.println("TRUCK SPOT UPDATED SUCCESSFULLY");
        } finally {
          floors.get(floor_id).lock.unlock();
        }
    }

    private static volatile FloorManager instance = null;

    public static FloorManager getInstance() {
        if (instance == null) {
            synchronized (FloorManager.class) {
                if (instance == null) {
                    instance = new FloorManager();
                }
            }
        }
        return instance;
    }
}
