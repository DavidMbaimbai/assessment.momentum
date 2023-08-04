package com.assessment.repository;

import com.assessment.entity.WithdrawalProcess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawalProcessRepository extends JpaRepository<WithdrawalProcess, Long> {
}
