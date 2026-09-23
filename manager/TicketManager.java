package manager;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import model.Spot;
import model.Ticket;

public class TicketManager {

    private TicketManager() {
    }

    ;
    
    public int generateTicket(ConcurrentHashMap<Integer, Ticket> tickets,String vehicleNumber,int totalAmount, int availableDays, Spot spot) {
        Ticket newTicket = new Ticket( vehicleNumber.trim().toUpperCase(), totalAmount, availableDays, spot);
        tickets.put(newTicket.ticket_Id, newTicket);
        System.out.println( "TICKET GENERATED SUCCESSFULLY AND TICKET_ID :- " + newTicket.ticket_Id);
        return newTicket.ticket_Id;
    }

    public Ticket getTicket(ConcurrentHashMap<Integer, Ticket> tickets, int ticket_id) {
        if (!tickets.containsKey(ticket_id)) {
            return null;
        }
        tickets.get(ticket_id).lock.lock();
        try {
            return tickets.get(ticket_id);
        } finally {
            tickets.get(ticket_id).lock.unlock();
        }
    }

    public boolean markPaidAmount(ConcurrentHashMap<Integer, Ticket> tickets, int ticket_id) {
        if (!tickets.containsKey(ticket_id)) {
            return false;
        }
        tickets.get(ticket_id).lock.lock();
        try {
            tickets.get(ticket_id).isPaid = true;
            return true;
        } finally {
            tickets.get(ticket_id).lock.unlock();
        }
    }

    public boolean isPaid(HashMap<Integer, Ticket> tickets, int ticket_id) {
        if (!tickets.containsKey(ticket_id)) {
            return false;
        }
        tickets.get(ticket_id).lock.lock();
        try {
            return tickets.get(ticket_id).isPaid;
        } finally {
            tickets.get(ticket_id).lock.unlock();
        }
    }

    private static volatile TicketManager instance = null;

    public static TicketManager getInstance() {
        if (instance == null) {
            synchronized (TicketManager.class) {
                if (instance == null) {
                    instance = new TicketManager();
                }
            }
        }
        return instance;
    }
}
