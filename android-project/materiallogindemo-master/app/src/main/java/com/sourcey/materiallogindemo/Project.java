package com.sourcey.materiallogindemo;

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

    public Project(int id, String title, String description, Date deadline, int maxprice, int minprice, String status, int client_id, String freelancer_id,String freelancer_username, String client_username) {
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
}
