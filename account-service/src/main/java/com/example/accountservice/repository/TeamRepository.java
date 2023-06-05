package com.example.accountservice.repository;

import com.example.accountservice.entity.Team;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team,String> {
    List<Team> findAllByDepartmentCode(String departmentCode);
}
