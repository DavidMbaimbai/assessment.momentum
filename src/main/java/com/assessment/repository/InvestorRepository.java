package com.assessment.repository;

import com.assessment.entity.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestorRepository extends JpaRepository<Investor, Long> {
    boolean existsByEmailAddress(String emailAddress);
}
