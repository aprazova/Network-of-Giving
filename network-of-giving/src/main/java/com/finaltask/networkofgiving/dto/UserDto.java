package com.finaltask.networkofgiving.dto;

import com.finaltask.networkofgiving.model.UsersTransaction;

import java.util.Set;

public class UserDto {

    private Long id;
    private String name;
    private String username;
    private String password;
    private Integer age;
    private String gender;
    private String location;

    Set<UsersTransaction> usersTransactions;

    public UserDto(){};

    public UserDto(String name, String username, String password, Integer age, String gender, String location){
        this.setName(name);
        this.setUsername(username);
        this.setPassword(password);
        this.setAge(age);
        this.setGender(gender);
        this.setLocation(location);
    }

    public void setId(Long id) {
        this.id = id;
    }
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
    public void setUsersTransactions(Set<UsersTransaction> usersTransactions) {
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
