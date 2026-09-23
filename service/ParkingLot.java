package service;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import manager.FloorManager;
import manager.ParkingManager;
import middleware.FloorValidation;
import model.Floor;
import model.Spot;
import model.Ticket;

public class ParkingLot {

    private final HashMap<Integer, Floor> floors;
    private final ConcurrentHashMap<Integer, Ticket> tickets;
    private final FloorManager floorManager;
    private final ParkingManager parkingManager;
    private final FloorValidation floorValidation;
    private final ConcurrentHashMap<String, Spot> spots;

    private ParkingLot() {
        this.floors = new HashMap();
        this.tickets = new ConcurrentHashMap<>();
        this.floorManager = FloorManager.getInstance();
        this.parkingManager = ParkingManager.getInstance();
        this.spots = new ConcurrentHashMap<>();
        this.floorValidation = new FloorValidation();
    }

    public void addFloor(int carPrice, int bikePrice, int truckPrice, int carSpot, int truckSpot, int bikeSpot) {
        if (floorValidation.priceValidation(carPrice) && floorValidation.priceValidation(bikePrice) && floorValidation.priceValidation(truckPrice)) {
            floorManager.addFloor(floors, carPrice, bikePrice, truckPrice, carSpot, truckSpot, bikeSpot);
        }
    }

    public void deleteFloor(int floor_id) {
        if (floorValidation.isFloorExist(floors, floor_id)) {
            floorManager.deleteFloor(floors, floor_id);
        }
    }

    public void updateCarPrice(int floor_id, int newCarPrice) {
        if (floorValidation.isFloorExist(floors, floor_id) && floorValidation.priceValidation(newCarPrice)) {
            floorManager.updateCarPrice(floors, floor_id, newCarPrice);
        }
    }

    public void updateBikePrice(int floor_id, int newBikePrice) {
        if (floorValidation.isFloorExist(floors, floor_id) && floorValidation.priceValidation(newBikePrice)) {
            floorManager.updateBikePrice(floors, floor_id, newBikePrice);
        }
    }

    public void updateTruckPrice(int floor_id, int newTruckPrice) {
        if (floorValidation.isFloorExist(floors, floor_id) && floorValidation.priceValidation(newTruckPrice)) {
            floorManager.updateTruckPrice(floors, floor_id, newTruckPrice);
        }
    }

    public void updateCarSpot(int floor_id, int newCarSpot) {
        if (floorValidation.isFloorExist(floors, floor_id) && floorValidation.isSpotCorrect(newCarSpot)) {
            floorManager.updateCarSpot(floors, floor_id, newCarSpot);
        }
    }

    public void updateBikeSpot(int floor_id, int newBikeSpot) {
        if (floorValidation.isFloorExist(floors, floor_id) && floorValidation.isSpotCorrect(newBikeSpot)) {
            floorManager.updateBikeSpot(floors, floor_id, newBikeSpot);
        }
    }

    public void updateTruckSpot(int floor_id, int newTruckSpot) {
        if (floorValidation.isFloorExist(floors, floor_id) && floorValidation.isSpotCorrect(newTruckSpot)) {
            floorManager.updateTruckSpot(floors, floor_id, newTruckSpot);
        }
    }

    public boolean generateTicket(int floor_id, String vehicleType, String vehicleNumber, int availableDays, int assignSpot, String paymentType) {
        vehicleType = vehicleType.toUpperCase();
        if (availableDays <= 0 || !floorValidation.isFloorExist(floors, floor_id) || !floorValidation.isSpotExists(floors, floor_id, vehicleType, assignSpot)) {
            System.out.println("BAD REQUEST");
            return false;
        }
        int priceSpot = floorManager.getSpotPrice(floors, floor_id, vehicleType);
        String finalVehicleType = vehicleType;
        String key = floor_id + ":" + finalVehicleType + ":" + assignSpot;
        Spot spot = spots.computeIfAbsent(key, k -> new Spot(floor_id, finalVehicleType, assignSpot));
        return parkingManager.getSpot(tickets, priceSpot, vehicleType, vehicleNumber, availableDays, spot, paymentType);
    }

    public void getTicketById(int ticket_id) {
        parkingManager.getTicketById(tickets, ticket_id);
    }

    public void getTicketBySpot(int assignSpot, String vehicleType) {
        parkingManager.getTicketBySpot(assignSpot, vehicleType);
    }

    private volatile static ParkingLot instance = null;

    public static ParkingLot getInstance() {
        if (instance == null) {
            synchronized (ParkingLot.class) {
                if (instance == null) {
                    instance = new ParkingLot();
                }
            }
        }
        return instance;
    }
}
