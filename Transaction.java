package com.example.mobilebanking.models;

public class Transaction {
    private String id;
    private String userId;
    private String type; // send, cashout, recharge
    private double amount;
    private double fee;
    private String toNumber;
    private String fromNumber;
    private String agentNumber;
    private String operator;
    private String method; // bkash, nagad, rocket, dbbl
    private String status; // success, pending, failed
    private long timestamp;

    public Transaction() {
        // Empty constructor for Firestore
    }

    // Send Money Constructor
    public Transaction(String userId, String type, double amount, String toNumber, 
                      String fromNumber, String method, String status) {
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.toNumber = toNumber;
        this.fromNumber = fromNumber;
        this.method = method;
        this.status = status;
        this.timestamp = System.currentTimeMillis();
    }

    // Cash Out Constructor
    public Transaction(String userId, String type, double amount, double fee, 
                      String agentNumber, String method, String status) {
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.fee = fee;
        this.agentNumber = agentNumber;
        this.method = method;
        this.status = status;
        this.timestamp = System.currentTimeMillis();
    }

    // Recharge Constructor
    public Transaction(String userId, String type, double amount, String toNumber, 
                      String operator, String method, String status) {
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.toNumber = toNumber;
        this.operator = operator;
        this.method = method;
        this.status = status;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public double getFee() { return fee; }
    public void setFee(double fee) { this.fee = fee; }

    public String getToNumber() { return toNumber; }
    public void setToNumber(String toNumber) { this.toNumber = toNumber; }

    public String getFromNumber() { return fromNumber; }
    public void setFromNumber(String fromNumber) { this.fromNumber = fromNumber; }

    public String getAgentNumber() { return agentNumber; }
    public void setAgentNumber(String agentNumber) { this.agentNumber = agentNumber; }

    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
