package com.finaltask.networkofgiving.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finaltask.networkofgiving.model.Charity;
import com.finaltask.networkofgiving.model.User;

import java.util.Date;

public class TransactionDto {

    private Long id;
    private Charity charity;
    private User user;
    private Double donation;
    private boolean isVolunteer;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date registeredAt;

    public TransactionDto(){
        this.registeredAt = new Date();
        this.isVolunteer = false;
        this.donation = 0.0;
    }

    public TransactionDto(Charity charity, User user, Double donation, boolean isVolunteer) {
        this.charity = charity;
        this.user = user;
        this.donation = donation;
        this.isVolunteer = isVolunteer;
        this.registeredAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Charity getCharity() {
        return charity;
    }

    public void setCharity(Charity charity) {
        this.charity = charity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getDonation() {
        return donation;
    }

    public void setDonation(Double donation) {
        this.donation = donation;
    }

    public boolean getIsVolunteer() {
        return isVolunteer;
    }

    public void setIsVolunteer(boolean isVolunteer) {
        this.isVolunteer = isVolunteer;
    }

    public Date getRegisteredAt() {
        return registeredAt;
    }
    public void setRegisteredAt(Date registeredAt) {
        this.registeredAt = registeredAt;
    }

}
