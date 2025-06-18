package com.bank.service;

import com.bank.model.Account;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class BankingService {
    private final ConcurrentHashMap<String, Account> accounts = new ConcurrentHashMap<>();
    private final AtomicInteger accountCounter = new AtomicInteger(1000);
    
    public Account createAccount(String accountHolderName, BigDecimal initialBalance) {
        String accountNumber = "ACC" + accountCounter.incrementAndGet();
        Account account = new Account(accountNumber, accountHolderName, initialBalance);
        accounts.put(accountNumber, account);
        System.out.println("Account created: " + accountNumber);
        return account;
    }
    
    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }
    
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }
    
    public boolean transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        Account fromAccount = accounts.get(fromAccountNumber);
        Account toAccount = accounts.get(toAccountNumber);
        
        if (fromAccount == null || toAccount == null) {
            System.out.println("One or both accounts not found");
            return false;
        }
        
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            System.out.println("Insufficient funds");
            return false;
        }
        
        // Perform transfer
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        
        System.out.println("Transfer successful: $" + amount + " from " + 
                          fromAccountNumber + " to " + toAccountNumber);
        return true;
    }
    
    public BigDecimal getBalance(String accountNumber) {
        Account account = accounts.get(accountNumber);
        return account != null ? account.getBalance() : BigDecimal.ZERO;
    }
    
    public boolean deposit(String accountNumber, BigDecimal amount) {
        Account account = accounts.get(accountNumber);
        if (account != null && amount.compareTo(BigDecimal.ZERO) > 0) {
            account.setBalance(account.getBalance().add(amount));
            return true;
        }
        return false;
    }
    
    public boolean withdraw(String accountNumber, BigDecimal amount) {
        Account account = accounts.get(accountNumber);
        if (account != null && account.getBalance().compareTo(amount) >= 0) {
            account.setBalance(account.getBalance().subtract(amount));
            return true;
        }
        return false;
    }
}