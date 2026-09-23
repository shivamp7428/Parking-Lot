package middleware;

import java.util.HashMap;
import model.Floor;

public class FloorValidation {

    public synchronized boolean priceValidation(int price) {
        if (price <= 0) {
            System.out.println("PRICE NOT FOUND");
            return false;
        }
        if (price > 10000) {
            System.out.println("INVALID PRICE");
            return false;
        }
        return true;
    }

    public boolean isFloorExist(HashMap<Integer, Floor> floors, int floor_id) {
        floors.get(floor_id).lock.lock();
        try {
            if (!floors.containsKey(floor_id)) {
                System.out.println("FLOOR NOT FOUND");
                return false;
            }
            return true;
        } finally {
            floors.get(floor_id).lock.unlock();
        }
    }

    public int getSpot(HashMap<Integer, Floor> floors, int floor_id, String type) {
        floors.get(floor_id).lock.lock();
        try {
            if (type.equals("CAR")) {
                return floors.get(floor_id).carSpot;
            }
            if (type.equals("BIKE")) {
                return floors.get(floor_id).bikeSpot;
            }
            if (type.equals("TRUCK")) {
                return floors.get(floor_id).truckSpot;
            }
            System.out.println("SPOT NOT FOUND");
            return -1;
        } finally {
            floors.get(floor_id).lock.unlock();
        }
    }

    public boolean isSpotExists(HashMap<Integer, Floor> floors, int floor_id, String type, int assignSpot) {
        floors.get(floor_id).lock.lock();
        try {
            int vehicleSpot = getSpot(floors, floor_id, type.toUpperCase());
            return assignSpot >= 1 && assignSpot <= vehicleSpot;
        } finally {
            floors.get(floor_id).lock.unlock();
        }
    }

    public boolean isSpotCorrect(int spot) {
        if (spot <= 0) {
            return false;
        }
        return true;
    }
}
