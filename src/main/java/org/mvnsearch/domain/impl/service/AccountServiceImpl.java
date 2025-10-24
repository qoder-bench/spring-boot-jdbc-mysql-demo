package org.mvnsearch.domain.impl.service;

import org.mvnsearch.domain.model.Account;
import org.mvnsearch.domain.repository.AccountRepository;
import org.mvnsearch.domain.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Implementation of AccountService
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createAccount(Account account) {
        // Validate username uniqueness
        if (account.getUsername() != null && accountRepository.findAccountByUsername(account.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists: " + account.getUsername());
        }

        // Validate email uniqueness
        if (account.getEmail() != null && accountRepository.findAccountByEmail(account.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists: " + account.getEmail());
        }

        // Set timestamps
        LocalDateTime now = LocalDateTime.now();
        if (account.getCreatedAt() == null) {
            account.setCreatedAt(now);
        }
        if (account.getUpdatedAt() == null) {
            account.setUpdatedAt(now);
        }

        // Set default status if not provided
        if (account.getStatus() == null) {
            account.setStatus(Account.AccountStatus.ACTIVE);
        }

        return accountRepository.save(account);
    }

    @Override
    public void updateAccount(Account account) {
        accountRepository.save(account);
    }
}
