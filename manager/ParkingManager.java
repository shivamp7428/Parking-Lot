package manager;

import enums.Payment;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import model.Spot;
import model.Ticket;


public class ParkingManager {

    TicketManager ticketManager;
    FloorManager floorManager;
    Payment type;
    HashMap<Integer, Ticket> carSpot;
    HashMap<Integer, Ticket> bikeSpot;
    HashMap<Integer, Ticket> truckSpot;

    private ParkingManager() {
        ticketManager = TicketManager.getInstance();
        floorManager = FloorManager.getInstance();
        carSpot = new HashMap<>();
        bikeSpot = new HashMap<>();
        truckSpot = new HashMap<>();
        type = new Payment();
    }

    public synchronized boolean spotValidation(String vehicleType, int assignSpot) {
        vehicleType = vehicleType.toUpperCase();
        if (vehicleType.equals("CAR")) {
            return carSpot.containsKey(assignSpot);
        }
        if (vehicleType.equals("BIKE")) {
            return bikeSpot.containsKey(assignSpot);
        }
        if (vehicleType.equals("TRUCK")) {
            return truckSpot.containsKey(assignSpot);
        }
        return false;
    }

    public void assignTicketSpot(Ticket ticket, String vehicleType, int assignSpot) {
        if (ticket == null) {
            return;
        }
        ticket.lock.lock();
        try {
            vehicleType = vehicleType.toUpperCase();
            if (vehicleType.equals("CAR")) {
                carSpot.put(assignSpot, ticket);
                return;
            }
            if (vehicleType.equals("BIKE")) {
                bikeSpot.put(assignSpot, ticket);
                return;
            }
            if (vehicleType.equals("TRUCK")) {
                truckSpot.put(assignSpot, ticket);
            }
        } finally {
            ticket.lock.unlock();
        }
    }

    public synchronized Ticket getTicket(String vehicleType, int assignSpot) {
        vehicleType = vehicleType.toUpperCase();
        if (vehicleType.equals("CAR")) {
            return carSpot.get(assignSpot);
        }
        if (vehicleType.equals("BIKE")) {
            return bikeSpot.get(assignSpot);
        }
        if (vehicleType.equals("TRUCK")) {
            return truckSpot.get(assignSpot);
        }
        return null;
    }

    public boolean getSpot(ConcurrentHashMap<Integer, Ticket> tickets, int priceSpot, String vehicleType, String vehicleNumber, int availableDays, Spot spot, String paymentType) {
        spot.lock.lock();
        try {
            if (spotValidation(spot.vehicleType, spot.spotNumber)) {
                System.out.println(spot.vehicleType.toUpperCase() + " SPOT IS ALREADY TAKEN");
                return false;
            }
            int totalAmount = priceSpot * availableDays;
            int ticket_id = ticketManager.generateTicket(tickets, vehicleNumber, totalAmount, availableDays, spot);
            Ticket getTicket = ticketManager.getTicket(tickets, ticket_id);
            System.out.println("Generated ID = " + ticket_id);
            System.out.println("Fetched Ticket = " + getTicket);
            if (getTicket == null) {
                System.out.println("TICKET NOT FOUND: " + ticket_id);
                return false;
            }
            assignTicketSpot(getTicket, spot.vehicleType, spot.spotNumber);
            if (type.getPaymentType(paymentType).pay(getTicket.totalAmount)) {
                ticketManager.markPaidAmount(tickets, ticket_id);
            }
            System.out.println("Ticket Generated Successfully");
            return true;
        } finally {
            spot.lock.unlock();
        }
    }

    public void getTicketById(ConcurrentHashMap<Integer, Ticket> tickets, int ticket_id) {
        if (!tickets.containsKey(ticket_id)) {
            return;
        }
        tickets.get(ticket_id).lock.lock();
        try {
            if (!tickets.containsKey(ticket_id)) {
                System.out.println("Ticket not found!");
                return;
            }
            Ticket ticket = ticketManager.getTicket(tickets, ticket_id);
            printTicket(ticket);
        } finally {
            tickets.get(ticket_id).lock.unlock();
        }
    }

    public void printTicket(Ticket ticket) {
        if (ticket == null) {
            System.out.println("Ticket not found!");
            return;
        }
        ticket.lock.lock();
        try {
            System.out.println("\n========================================");
            System.out.println("             PARKING TICKET");
            System.out.println("========================================");
            System.out.printf("%-18s : %s%n", "Ticket ID", ticket.ticket_Id);
            System.out.printf("%-18s : %s%n", "Vehicle Number", ticket.vehicleNumber);
            System.out.printf("%-18s : %s%n", "Vehicle Type", ticket.spot.vehicleType);
            System.out.println("----------------------------------------");
            System.out.printf("%-18s : Floor %d%n", "Assigned Floor", ticket.spot.floorId);
            System.out.printf("%-18s : Spot", ticket.spot.spotNumber);
            System.out.println(ticket.spot.spotNumber);
            System.out.printf("%-18s : %s%n", "Entry Time", ticket.entryTime);
            System.out.printf("%-18s : %s%n", "Exit Time", ticket.exitTime != null ? ticket.exitTime : "Not Exited");
            System.out.println("----------------------------------------");
            System.out.printf("%-18s : %d%n", "Available Days", ticket.availableDays);
            System.out.printf("%-18s : %d%n", "Total Amount", ticket.totalAmount);
            System.out.printf("%-18s : %s%n", "Payment Status", ticket.isPaid ? "PAID" : "NOT PAID");
            System.out.println("========================================\n");
        } finally {
            ticket.lock.unlock();
        }
    }

    public synchronized void getTicketBySpot(int assignSpot, String vehicleType) {
        if (!spotValidation(vehicleType, assignSpot)) {
            System.out.println(vehicleType.toUpperCase() + " SPOT NOT FOUND");
            return;
        }
        Ticket ticket = getTicket(vehicleType, assignSpot);
        printTicket(ticket);
    }

    private volatile static ParkingManager instance = null;

    public static ParkingManager getInstance() {
        if (instance == null) {
            synchronized (ParkingManager.class) {
                if (instance == null) {
                    instance = new ParkingManager();
                }
            }
        }
        return instance;
    }
}
