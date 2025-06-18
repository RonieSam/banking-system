package com.bank;

import com.bank.model.Account;
import com.bank.service.BankingService;
import java.math.BigDecimal;
import java.util.Scanner;

public class BankingApplication {
    private static BankingService bankingService = new BankingService();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=== Welcome to Online Banking System ===");
        
        // Add some sample accounts
        bankingService.createAccount("John Doe", new BigDecimal("1000.00"));
        bankingService.createAccount("Jane Smith", new BigDecimal("2000.00"));
        
        while (true) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    viewAccount();
                    break;
                case 3:
                    transfer();
                    break;
                case 4:
                    viewAllAccounts();
                    break;
                case 5:
                    System.out.println("Thank you for using Banking System!");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
    private static void showMenu() {
        System.out.println("\n=== Banking Menu ===");
        System.out.println("1. Create Account");
        System.out.println("2. View Account");
        System.out.println("3. Transfer Money");
        System.out.println("4. View All Accounts");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }
    
    private static void createAccount() {
        System.out.print("Enter account holder name: ");
        String name = scanner.nextLine();
        System.out.print("Enter initial balance: ");
        BigDecimal balance = new BigDecimal(scanner.nextLine());
        
        Account account = bankingService.createAccount(name, balance);
        System.out.println("Account created successfully!");
        System.out.println("Account Number: " + account.getAccountNumber());
    }
    
    private static void viewAccount() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        
        Account account = bankingService.getAccount(accountNumber);
        if (account != null) {
            System.out.println("Account Details:");
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Account Holder: " + account.getAccountHolderName());
            System.out.println("Balance: $" + account.getBalance());
        } else {
            System.out.println("Account not found!");
        }
    }
    
    private static void transfer() {
        System.out.print("Enter from account number: ");
        String fromAccount = scanner.nextLine();
        System.out.print("Enter to account number: ");
        String toAccount = scanner.nextLine();
        System.out.print("Enter amount to transfer: ");
        BigDecimal amount = new BigDecimal(scanner.nextLine());
        
        boolean success = bankingService.transfer(fromAccount, toAccount, amount);
        if (success) {
            System.out.println("Transfer successful!");
        } else {
            System.out.println("Transfer failed! Check account numbers and balance.");
        }
    }
    
    private static void viewAllAccounts() {
        System.out.println("\n=== All Accounts ===");
        bankingService.getAllAccounts().forEach(account -> {
            System.out.println("Account: " + account.getAccountNumber() + 
                             " | Holder: " + account.getAccountHolderName() + 
                             " | Balance: $" + account.getBalance());
        });
    }
}