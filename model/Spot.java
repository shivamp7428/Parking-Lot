package model;

import java.util.concurrent.locks.ReentrantLock;

public class Spot {

    public int floorId;
    public String vehicleType;
    public int spotNumber;

    public final ReentrantLock lock;

    public Spot(int floorId, String vehicleType, int spotNumber) {
        this.floorId = floorId;
        this.lock = new ReentrantLock();
        this.vehicleType = vehicleType;
        this.spotNumber = spotNumber;
    }
}
