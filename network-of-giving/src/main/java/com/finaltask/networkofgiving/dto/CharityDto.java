package com.finaltask.networkofgiving.dto;

public class CharityDto {

    private Long id;
    private String owner;
    private String title;
    private String image;
    private String description;
    private Double requiredBudget;
    private Double currentBudget = 0.0;
    private Integer requiredVolunteers;
    private Integer currentVolunteers = 0;

    public CharityDto(){};

    public CharityDto(String title, String image, String description, Double requiredBudget, Integer requiredVolunteers) {
        this.title = title;
        this.image = image;
        this.description = description;
        this.requiredBudget = requiredBudget;
        this.requiredVolunteers = requiredVolunteers;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setRequiredBudget(Double requiredBudget) {
        this.requiredBudget = requiredBudget;
    }
    public void setRequiredVolunteers(Integer requiredVolunteers) {
        this.requiredVolunteers = requiredVolunteers;
    }
    public void setCurrentBudget(Double currentBudget) {
        this.currentBudget = currentBudget;
    }
    public void setCurrentVolunteers(Integer currentVolunteers) {
        this.currentVolunteers = currentVolunteers;
    }

    public Long getId() {
        return id;
    }
    public String getOwner() {
        return owner;
    }
    public String getTitle() {
        return this.title;
    }
    public String getImage() {
        return this.image;
    }
    public String getDescription() {
        return this.description;
    }
    public Double getRequiredBudget() {
        return this.requiredBudget;
    }
    public Integer getRequiredVolunteers() {
        return this.requiredVolunteers;
    }
    public Double getCurrentBudget() {
        return currentBudget;
    }
    public Integer getCurrentVolunteers() {
        return currentVolunteers;
    }
}
