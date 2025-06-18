package com.bank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Account {
    private String accountNumber;
    private String accountHolderName;
    private BigDecimal balance;
    private LocalDateTime createdAt;
    
    public Account() {
        this.createdAt = LocalDateTime.now();
    }
    
    public Account(String accountNumber, String accountHolderName, BigDecimal balance) {
        this();
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }
    
    // Getters and Setters
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public String getAccountHolderName() {
        return accountHolderName;
    }
    
    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }
    
    public BigDecimal getBalance() {
        return balance;
    }
    
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", accountHolderName='" + accountHolderName + '\'' +
                ", balance=" + balance +
                ", createdAt=" + createdAt +
                '}';
    }
}