package com.example.accountservice.repository;

import com.example.accountservice.entity.Account;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);

    List<Account> findByLegalEntityCode(String legalEntityCode);

    void deleteAccountByEmail(String email);

    List<Account> findByDepartmentCode(String departmentCode);

    List<Account> findByTeamCode(String teamCode);
    Optional<Account> findByEmailAndLegalEntityCode(String userEmail,String entityCode);

}
