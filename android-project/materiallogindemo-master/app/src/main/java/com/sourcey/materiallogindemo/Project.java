/**
 * This class describes a Project. A project has id, title, description, deadline, price range, status, client and freelancer id and username, bid count, average bid and the list of bids.
 *
 * @author  Berkay Kozan github.com/leblebi1
 * @version 1.0
 * @since   2018 October
 */

package com.sourcey.materiallogindemo;

import java.util.ArrayList;
import java.util.Date;

public class Project {
    private int id;
    private String title;
    private String description;
    private Date deadline;
    private int maxprice;
    private int minprice;
    private String status;
    private int client_id;
    private String client_username;
    private String freelancer_id;
    private String freelancer_username;
    private int bid_count;
    private double average_bid;
    ArrayList<Bid> bidList = new ArrayList<>();


    public Project(int bid_count, double average_bid, int id, String title, String description, Date deadline, int maxprice, int minprice, String status, int client_id, String freelancer_id, String freelancer_username, String client_username, ArrayList<Bid> bidList) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.maxprice = maxprice;
        this.minprice = minprice;
        this.status = status;
        this.client_id = client_id;
        this.freelancer_id = freelancer_id;
        this.client_username= client_username;
        this.freelancer_username= freelancer_username;
        this.bid_count= bid_count;
        this.average_bid= average_bid;
        this.bidList= bidList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getMaxprice() {
        return maxprice+"";
    }

    public void setMaxprice(int maxprice) {
        this.maxprice = maxprice;
    }

    public String getMinprice() {
        return minprice+"";
    }

    public void setMinprice(int minprice) {
        this.minprice = minprice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client) {
        this.client_id = client_id;
    }

    public String getFreelancer_id() {
        return freelancer_id;
    }

    public void setFreelancer_id(String freelancer) {
        this.freelancer_id = freelancer_id;
    }

    public String getClient_username() {
        return client_username;
    }

    public void setClient_username(String client_username) {
        this.client_username = client_username;
    }

    public String getFreelancer_username() {
        return freelancer_username;
    }

    public void setFreelancer_username(String freelancer_username) {
        this.freelancer_username = freelancer_username;
    }

    public int getBid_count() {
        return bid_count;
    }

    public void setBid_count(int bid_count) {
        this.bid_count = bid_count;
    }

    public double getAverage_bid() {
        return average_bid;
    }

    public void setAverage_bid(double average_bid) {
        this.average_bid = average_bid;
    }

    public ArrayList<Bid> getBidList() {
        return bidList;
    }

    public void setBidList(ArrayList<Bid> bidList) {
        this.bidList = bidList;
    }
}
