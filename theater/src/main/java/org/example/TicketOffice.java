package org.example;

import java.util.ArrayList;
import java.util.List;

public class TicketOffice {
    private Long amount;
    private List<Ticket> tickets = new ArrayList<>();

    public Ticket getTicket(){
        return tickets.remove(0);
    }

    private Long plusAmount(Long amount) {
        this.amount += amount;
        return this.amount;
    }
    private Long minusAmount(Long amount) {
        this.amount -= amount;
        return this.amount;
    }

    public void sellTicketTo(Audience audience) {
        plusAmount(audience.buy(getTicket()));
    }
}
