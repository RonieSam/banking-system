package com.bank.service;

import com.bank.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Banking Service Tests")
public class BankingServiceTest {
    private BankingService bankingService;
    
    @BeforeEach
    void setUp() {
        bankingService = new BankingService();
    }
    
    @Test
    @DisplayName("Should create account successfully")
    void testCreateAccount() {
        // Given
        String name = "John Doe";
        BigDecimal initialBalance = new BigDecimal("1000.00");
        
        // When
        Account account = bankingService.createAccount(name, initialBalance);
        
        // Then
        assertNotNull(account);
        assertEquals(name, account.getAccountHolderName());
        assertEquals(initialBalance, account.getBalance());
        assertTrue(account.getAccountNumber().startsWith("ACC"));
        assertNotNull(account.getCreatedAt());
    }
    
    @Test
    @DisplayName("Should retrieve account by account number")
    void testGetAccount() {
        // Given
        Account createdAccount = bankingService.createAccount("Jane Smith", new BigDecimal("2000.00"));
        
        // When
        Account retrievedAccount = bankingService.getAccount(createdAccount.getAccountNumber());
        
        // Then
        assertNotNull(retrievedAccount);
        assertEquals(createdAccount.getAccountNumber(), retrievedAccount.getAccountNumber());
        assertEquals("Jane Smith", retrievedAccount.getAccountHolderName());
        assertEquals(new BigDecimal("2000.00"), retrievedAccount.getBalance());
    }
    
    @Test
    @DisplayName("Should return null for non-existent account")
    void testGetNonExistentAccount() {
        // When
        Account account = bankingService.getAccount("INVALID123");
        
        // Then
        assertNull(account);
    }
    
    @Test
    @DisplayName("Should transfer money successfully between accounts")
    void testSuccessfulTransfer() {
        // Given
        Account account1 = bankingService.createAccount("Alice", new BigDecimal("1000.00"));
        Account account2 = bankingService.createAccount("Bob", new BigDecimal("500.00"));
        BigDecimal transferAmount = new BigDecimal("200.00");
        
        // When
        boolean result = bankingService.transfer(
            account1.getAccountNumber(), 
            account2.getAccountNumber(), 
            transferAmount
        );
        
        // Then
        assertTrue(result);
        assertEquals(new BigDecimal("800.00"), account1.getBalance());
        assertEquals(new BigDecimal("700.00"), account2.getBalance());
    }
    
    @Test
    @DisplayName("Should fail transfer when insufficient funds")
    void testFailedTransferInsufficientFunds() {
        // Given
        Account account1 = bankingService.createAccount("Alice", new BigDecimal("100.00"));
        Account account2 = bankingService.createAccount("Bob", new BigDecimal("500.00"));
        BigDecimal transferAmount = new BigDecimal("200.00");
        
        // When
        boolean result = bankingService.transfer(
            account1.getAccountNumber(), 
            account2.getAccountNumber(), 
            transferAmount
        );
        
        // Then
        assertFalse(result);
        assertEquals(new BigDecimal("100.00"), account1.getBalance());
        assertEquals(new BigDecimal("500.00"), account2.getBalance());
    }
    
    @Test
    @DisplayName("Should fail transfer for non-existent accounts")
    void testFailedTransferNonExistentAccount() {
        // Given
        Account account1 = bankingService.createAccount("Alice", new BigDecimal("1000.00"));
        
        // When
        boolean result = bankingService.transfer(
            account1.getAccountNumber(), 
            "INVALID123", 
            new BigDecimal("100.00")
        );
        
        // Then
        assertFalse(result);
        assertEquals(new BigDecimal("1000.00"), account1.getBalance());
    }
    
    @Test
    @DisplayName("Should return all accounts")
    void testGetAllAccounts() {
        // Given
        bankingService.createAccount("User1", new BigDecimal("1000.00"));
        bankingService.createAccount("User2", new BigDecimal("2000.00"));
        bankingService.createAccount("User3", new BigDecimal("1500.00"));
        
        // When
        List<Account> accounts = bankingService.getAllAccounts();
        
        // Then
        assertEquals(3, accounts.size());
    }
    
    @Test
    @DisplayName("Should return correct balance")
    void testGetBalance() {
        // Given
        Account account = bankingService.createAccount("Test User", new BigDecimal("1500.00"));
        
        // When
        BigDecimal balance = bankingService.getBalance(account.getAccountNumber());
        
        // Then
        assertEquals(new BigDecimal("1500.00"), balance);
    }
    
    @Test
    @DisplayName("Should return zero balance for non-existent account")
    void testGetBalanceNonExistentAccount() {
        // When
        BigDecimal balance = bankingService.getBalance("INVALID123");
        
        // Then
        assertEquals(BigDecimal.ZERO, balance);
    }
    
    @Test
    @DisplayName("Should deposit money successfully")
    void testDeposit() {
        // Given
        Account account = bankingService.createAccount("Test User", new BigDecimal("1000.00"));
        
        // When
        boolean result = bankingService.deposit(account.getAccountNumber(), new BigDecimal("500.00"));
        
        // Then
        assertTrue(result);
        assertEquals(new BigDecimal("1500.00"), account.getBalance());
    }
    
    @Test
    @DisplayName("Should withdraw money successfully")
    void testWithdraw() {
        // Given
        Account account = bankingService.createAccount("Test User", new BigDecimal("1000.00"));
        
        // When
        boolean result = bankingService.withdraw(account.getAccountNumber(), new BigDecimal("300.00"));
        
        // Then
        assertTrue(result);
        assertEquals(new BigDecimal("700.00"), account.getBalance());
    }
    
    @Test
    @DisplayName("Should fail withdrawal with insufficient funds")
    void testWithdrawInsufficientFunds() {
        // Given
        Account account = bankingService.createAccount("Test User", new BigDecimal("100.00"));
        
        // When
        boolean result = bankingService.withdraw(account.getAccountNumber(), new BigDecimal("200.00"));
        
        // Then
        assertFalse(result);
        assertEquals(new BigDecimal("100.00"), account.getBalance());
    }
}