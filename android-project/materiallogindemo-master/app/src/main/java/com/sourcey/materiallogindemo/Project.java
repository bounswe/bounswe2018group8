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
    private int client;
    private String [] freelancer;

    public Project(int id, String title, String description, Date deadline, int maxprice, int minprice, String status, int client, String[] freelancer) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.maxprice = maxprice;
        this.minprice = minprice;
        this.status = status;
        this.client = client;
        this.freelancer = freelancer;
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

    public int getMaxprice() {
        return maxprice;
    }

    public void setMaxprice(int maxprice) {
        this.maxprice = maxprice;
    }

    public int getMinprice() {
        return minprice;
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

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    public String[] getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(String[] freelancer) {
        this.freelancer = freelancer;
    }
}
