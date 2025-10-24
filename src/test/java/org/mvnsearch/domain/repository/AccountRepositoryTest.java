package org.mvnsearch.domain.repository;

import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;
import org.mvnsearch.ProjectBaseTest;
import org.mvnsearch.domain.model.Account;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountRepositoryTest extends ProjectBaseTest {
    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testAll() {
        final Iterable<@NonNull Account> allAccounts = accountRepository.findAll();
        assertThat(allAccounts).isNotEmpty();
    }
}
