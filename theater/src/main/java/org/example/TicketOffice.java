package org.example;

import java.util.ArrayList;
import java.util.List;

public class TicketOffice {
    private Long amount;
    private List<Ticket> tickets = new ArrayList<>();

    public Ticket getTicket(){
        return tickets.remove(0);
    }

    public Long plusAmount(Long amount) {
        this.amount += amount;
        return this.amount;
    }
    public Long minusAmount(Long amount) {
        this.amount -= amount;
        return this.amount;
    }

}
