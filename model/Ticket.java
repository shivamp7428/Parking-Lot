package model;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Ticket {

    public String vehicleNumber;
    public int totalAmount;
    public boolean isPaid;
    public int availableDays;
    public int ticket_Id;
    public Spot spot;
    public LocalDateTime entryTime;
    public LocalDateTime exitTime;
    public ReentrantLock lock;

    public Ticket(String vehicleNumber, int totalAmount, int availableDays, Spot spot) {
        this.vehicleNumber = vehicleNumber;
        this.totalAmount = totalAmount;
        this.isPaid = false;
        this.availableDays = availableDays;
        this.ticket_Id = GENERA_ATOMIC_INTEGER.incrementAndGet();
        this.entryTime = LocalDateTime.now();
        this.spot = spot;
        this.exitTime = entryTime.plusDays(availableDays);
        this.lock = new ReentrantLock();
    }

    private static final AtomicInteger GENERA_ATOMIC_INTEGER = new AtomicInteger(0);
}
