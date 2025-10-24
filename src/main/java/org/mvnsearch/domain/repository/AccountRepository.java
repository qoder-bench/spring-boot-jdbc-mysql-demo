package org.mvnsearch.domain.repository;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.mvnsearch.domain.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * Repository interface for Account entity
 */
@Repository
public interface AccountRepository extends CrudRepository<@NonNull Account, @NonNull Long> {
    @Nullable
    Account findAccountByUsername(String username);

    @Nullable
    Account findAccountByEmail(String email);

}
