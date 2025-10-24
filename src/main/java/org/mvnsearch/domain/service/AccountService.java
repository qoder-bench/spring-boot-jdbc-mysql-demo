package org.mvnsearch.domain.service;

import org.mvnsearch.domain.model.Account;

/**
 * Service interface for Account domain operations
 */
public interface AccountService {

    /**
     * Create a new account
     *
     * @param account the account to create
     * @return the created account with generated ID
     * @throws IllegalArgumentException if username or email already exists
     */
    Account createAccount(Account account);

    /**
     * Update an existing account
     *
     * @param account the account to update
     * @return the updated account
     * @throws IllegalArgumentException if account ID is null or account doesn't exist
     */
    void updateAccount(Account account);

}
