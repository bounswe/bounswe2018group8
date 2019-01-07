/**
 * This class describes a Bid. A bid has id, freelancer id and username, and amount.
 *
 * @author  Berkay Kozan github.com/leblebi1
 * @version 1.0
 * @since   2018 October
 */
package com.sourcey.materiallogindemo;

public class Bid {
    int id;
    int freelancer_id;
    String freelancer_username;
    int amount;

    public Bid(int id, int freelancer_id, String freelancer_username, int amount) {
        this.id = id;
        this.freelancer_id = freelancer_id;
        this.freelancer_username = freelancer_username;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFreelancer_id() {
        return freelancer_id;
    }

    public void setFreelancer_id(int freelancer_id) {
        this.freelancer_id = freelancer_id;
    }

    public String getFreelancer_username() {
        return freelancer_username;
    }

    public void setFreelancer_username(String freelancer_username) {
        this.freelancer_username = freelancer_username;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
