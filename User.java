package com.example.mobilebanking.models;

public class User {
    private String id;
    private String name;
    private String mobile;
    private String email;
    private String pin;
    private double balance;
    private String accountNumber;
    private String joinDate;

    public User() {
        // Empty constructor for Firestore
    }

    public User(String name, String mobile, String email, String pin) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.pin = pin;
        this.balance = 500.0; // Welcome bonus
        this.accountNumber = generateAccountNumber();
        this.joinDate = String.valueOf(System.currentTimeMillis());
    }

    private String generateAccountNumber() {
        return "4582" + (int)(Math.random() * 9000 + 1000);
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getJoinDate() { return joinDate; }
    public void setJoinDate(String joinDate) { this.joinDate = joinDate; }
}
