package org.mvnsearch.web.rest;

import org.jspecify.annotations.NonNull;
import org.mvnsearch.domain.model.Account;
import org.mvnsearch.domain.repository.AccountRepository;
import org.mvnsearch.domain.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.StreamSupport;

/**
 * REST controller for Account management
 */
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository, AccountService accountService) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

    /**
     * Request DTO for creating account
     */
    public record CreateAccountRequest(
            String username,
            String email,
            String password,
            String firstName,
            String lastName,
            String phone
    ) {
    }

    /**
     * Request DTO for credential verification
     */
    public record VerifyCredentialsRequest(
            String username,
            String password
    ) {
    }

    /**
     * Response DTO for account information (excludes sensitive data)
     */
    public record AccountResponse(
            Long id,
            String username,
            String email,
            String firstName,
            String lastName,
            String phone,
            String status,
            LocalDateTime createdAt,
            LocalDateTime lastLoginAt
    ) {
        public static AccountResponse from(Account account) {
            return new AccountResponse(
                    account.getId(),
                    account.getUsername(),
                    account.getEmail(),
                    account.getFirstName(),
                    account.getLastName(),
                    account.getPhone(),
                    account.getStatus() != null ? account.getStatus().name() : null,
                    account.getCreatedAt(),
                    account.getLastLoginAt()
            );
        }
    }

    /**
     * Response DTO for verification result
     */
    public record VerifyResponse(
            boolean verified,
            String message,
            AccountResponse account
    ) {
    }

    /**
     * Create a new account
     *
     * @param request the account creation request
     * @return the created account information
     */
    @PostMapping
    public ResponseEntity<@NonNull AccountResponse> createAccount(@RequestBody CreateAccountRequest request) {
        // Build account entity
        Account account = new Account();
        account.setUsername(request.username());
        account.setEmail(request.email());
        account.setPassword(request.password()); // Note: In production, password should be encrypted
        account.setFirstName(request.firstName());
        account.setLastName(request.lastName());
        account.setPhone(request.phone());

        // Create account via service
        Account createdAccount = accountService.createAccount(account);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AccountResponse.from(createdAccount));
    }

    /**
     * Verify account credentials (username and password)
     *
     * @param request the verification request
     * @return verification result with account info if successful
     */
    @PostMapping("/verify")
    public ResponseEntity<@NonNull VerifyResponse> verifyCredentials(@RequestBody VerifyCredentialsRequest request) {
        Account account = accountRepository.findAccountByUsername(request.username());

        if (account == null) {
            return ResponseEntity.ok(new VerifyResponse(
                    false,
                    "Username not found",
                    null
            ));
        }


        // Verify password (Note: In production, use BCrypt or similar for comparison)
        if (!account.getPassword().equals(request.password())) {
            return ResponseEntity.ok(new VerifyResponse(
                    false,
                    "Invalid password",
                    null
            ));
        }

        // Check if account is active
        if (!account.isActive()) {
            return ResponseEntity.ok(new VerifyResponse(
                    false,
                    "Account is not active",
                    null
            ));
        }

        // Update last login timestamp
        account.setLastLoginAt(LocalDateTime.now());
        accountRepository.save(account);

        return ResponseEntity.ok(new VerifyResponse(
                true,
                "Credentials verified successfully",
                AccountResponse.from(account)
        ));
    }

    @GetMapping("")
    public List<AccountResponse> getAllAccounts() {
        return StreamSupport.stream(accountRepository.findAll().spliterator(), false)
                .map(AccountResponse::from)
                .toList();
    }
}