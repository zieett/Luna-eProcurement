package com.example.accountservice.repository;

import com.example.accountservice.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team,String> {

}
