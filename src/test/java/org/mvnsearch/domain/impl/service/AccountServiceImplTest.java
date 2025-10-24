package org.mvnsearch.domain.impl.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mvnsearch.ProjectBaseTest;
import org.mvnsearch.domain.model.Account;
import org.mvnsearch.domain.repository.AccountRepository;
import org.mvnsearch.domain.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for AccountServiceImpl
 */
@Transactional
public class AccountServiceImplTest extends ProjectBaseTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    private Account testAccount;

    @BeforeEach
    public void setUp() {
        // Create a test account for use in tests
        testAccount = new Account();
        testAccount.setUsername("testuser");
        testAccount.setEmail("test@example.com");
        testAccount.setPassword("password123");
        testAccount.setFirstName("Test");
        testAccount.setLastName("User");
        testAccount.setPhone("+1-555-9999");
        testAccount.setStatus(Account.AccountStatus.ACTIVE);
    }

    @Test
    public void testCreateAccount_Success() {
        // When
        Account created = accountService.createAccount(testAccount);

        // Then
        assertNotNull(created.getId());
        assertEquals("testuser", created.getUsername());
        assertEquals("test@example.com", created.getEmail());
        assertEquals("password123", created.getPassword());
        assertEquals("Test", created.getFirstName());
        assertEquals("User", created.getLastName());
        assertEquals("+1-555-9999", created.getPhone());
        assertEquals(Account.AccountStatus.ACTIVE, created.getStatus());
        assertNotNull(created.getCreatedAt());
        assertNotNull(created.getUpdatedAt());
    }

    @Test
    public void testCreateAccount_WithDefaultStatus() {
        // Given - no status set
        testAccount.setStatus(null);

        // When
        Account created = accountService.createAccount(testAccount);

        // Then
        assertEquals(Account.AccountStatus.ACTIVE, created.getStatus());
    }

    @Test
    public void testCreateAccount_DuplicateUsername() {
        // Given
        accountService.createAccount(testAccount);

        // When & Then
        Account duplicate = new Account();
        duplicate.setUsername("testuser");
        duplicate.setEmail("another@example.com");
        duplicate.setPassword("password");

        assertThrows(IllegalArgumentException.class, () -> {
            accountService.createAccount(duplicate);
        });
    }

    @Test
    public void testCreateAccount_DuplicateEmail() {
        // Given
        accountService.createAccount(testAccount);

        // When & Then
        Account duplicate = new Account();
        duplicate.setUsername("anotheruser");
        duplicate.setEmail("test@example.com");
        duplicate.setPassword("password");

        assertThrows(IllegalArgumentException.class, () -> {
            accountService.createAccount(duplicate);
        });
    }
}
