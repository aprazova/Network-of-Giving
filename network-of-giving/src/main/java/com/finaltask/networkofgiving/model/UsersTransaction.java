package com.finaltask.networkofgiving.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "usersTransactions")
public class UsersTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "charity_id")
    private Charity charity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "donate", columnDefinition = "float default 0.0")
    private Double donation;

    @Column(name = "isVolunteer", columnDefinition = "boolean default false")
    private boolean isVolunteer;

    @Column(name="date", columnDefinition = "date default current_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date registeredAt;

    public UsersTransaction(){
        this.registeredAt = new Date();
        this.isVolunteer = false;
        this.donation = 0.0;
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
