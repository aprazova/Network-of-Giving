package com.finaltask.networkofgiving.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name="charity")
public class Charity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner")
    @NotBlank
    @Length(max = 64)
    private String owner;

    @Column(name = "title")
    @NotBlank
    @Length(max = 64)
    private String title;

    @Column(name = "image")
    @NotBlank
    @Length(max = 32)
    private String image;

    @Column(name = "description")
    @NotBlank
    @Length(max = 1024)
    private String description;

    @Column(name = "requiredBudget")
    private Double requiredBudget;

    @Column(name = "currentBudget", columnDefinition = "float default 0.0")
    private Double currentBudget;

    @Column(name = "requiredVolunteers")
    private Integer requiredVolunteers;

    @Column(name = "currentVolunteers", columnDefinition = "integer default 0")
    private Integer currentVolunteers;

    @OneToMany(mappedBy = "charity",  cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JsonIgnore
    @OnDelete(action= OnDeleteAction.CASCADE)
    Set<UsersTransaction> usersTransactions;

    public Charity() {
    }

    public Charity(String title, String owner, String image, String description, Double requiredBudget, Integer requitedVolunteers) {
        this.setTitle(title);
        this.setOwner(owner);
        this.setImage(image);
        this.setDescription(description);
        this.setRequiredBudget(requiredBudget);
        this.setRequiredVolunteers(requitedVolunteers);
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

    public Integer getCurrentVolunteers() {
        return this.currentVolunteers;
    }

    public Double getCurrentBudget() {
        return this.currentBudget;
    }
}

