package com.finaltask.networkofgiving.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity(name = "User")
@Table( name="usersInfo")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotBlank
    @Length(max=64)
    private String name;

    @Column(unique = true, name = "username")
    @NotBlank
    @Length(max=64)
    private String username;

    @Column(name = "password")
    @NotBlank
    @Length(max=64)
    private String password;

    @Column(name = "age")
    @NotNull
    private Integer age;

    @Column(name = "gender")
    @Length(max=64)
    private String gender;

    @Column(name = "location")
    @Length(max=64)
    private String location;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,  orphanRemoval = true)
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JsonIgnore
    Set<UsersTransaction> usersTransactions;

    public User(){};

    public User(String name, String username, String password, Integer age, String gender, String location){
        this.setName(name);
        this.setUsername(username);
        this.setPassword(password);
        this.setAge(age);
        this.setGender(gender);
        this.setLocation(location);
    }

    public void setId(Long id) {this.id = id;};
    public void setName(String name) {
        this.name = name;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setUsersTransactions( Set<UsersTransaction> usersTransactions) {
        this.usersTransactions = usersTransactions;
    }

    public String getName() {
        return this.name;
    }
    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
    public Integer getAge() {
        return this.age;
    }
    public String getGender() {
        return this.gender;
    }
    public String getLocation() {
        return this.location;
    }
    public Long getId() {return this.id; };

    public Set<UsersTransaction> getUsersTransactions() {
        return usersTransactions;
    }
}
