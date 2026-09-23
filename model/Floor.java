package model;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Floor{
    public int carPrice;
    public int bikePrice;
    public int truckPrice;
    public int carSpot;
    public int truckSpot;
    public int bikeSpot;
    public int id;
    public HashMap<Integer,Integer> cars;
    public HashMap<Integer,Integer> bikes;
    public HashMap<Integer,Integer> trucks;
    public ReentrantLock lock;
    public Floor(int carPrice ,int bikePrice , int truckPrice , int carSpot , int truckSpot, int bikeSpot){
         this.carPrice = carPrice;
         this.bikePrice = bikePrice;
         this.truckPrice = truckPrice;
         this.carSpot = carSpot;
         this.bikeSpot = bikeSpot;
         this.truckSpot = truckSpot;
         this.id = ID_GENERATOR.incrementAndGet();
         this.cars = new HashMap<>();
         this.bikes = new HashMap<>();
         this.lock = new ReentrantLock();
         this.trucks = new HashMap<>();
    }

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);
}