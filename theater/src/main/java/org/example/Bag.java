package org.example;

public class Bag {
    public Bag(Long amount) {
        this(amount, null);
    }
    public Bag(Long amount, Invitation invitation) {
        this.amount = amount;
        this.invitation = invitation;
    }
    private Long amount;
    private Invitation invitation;
    private Ticket ticket;

    public boolean hasInvitation(){
        return invitation != null;
    }

    public boolean hasAmount(){
        return amount != null;
    }

    public boolean hasTicket(){
        return ticket != null;
    }

    public Long plusAmount(Long amount) {
        this.amount += amount;
        return this.amount;
    }
    public Long minusAmount(Long amount) {
        this.amount -= amount;
        return this.amount;
    }

    public void setTicket(Ticket ticket){
        this.ticket = ticket;
    }

}
