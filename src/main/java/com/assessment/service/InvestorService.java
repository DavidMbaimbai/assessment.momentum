package com.assessment.service;

import com.assessment.entity.*;
import com.assessment.enums.ProductType;
import com.assessment.enums.WithdrawalStatus;
import com.assessment.repository.InvestorRepository;
import com.assessment.repository.ProductRepository;
import com.assessment.repository.WithdrawalProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class InvestorService {
    private final InvestorRepository investorRepository;

    private final WithdrawalProcessRepository withdrawalProcessRepository;

    private final ProductRepository productRepository;

    @Autowired
    public InvestorService(InvestorRepository investorRepository, WithdrawalProcessRepository withdrawalProcessRepository, ProductRepository productRepository) {
        this.investorRepository = investorRepository;
        this.withdrawalProcessRepository = withdrawalProcessRepository;
        this.productRepository = productRepository;
    }

    public List<Investor> getAllInvestors() {
        return investorRepository.findAll();
    }

    public Optional<Investor> getInvestorById(Long id) {
        return investorRepository.findById(id);
    }

    public Investor createInvestor(Investor investor) {
        if (isEmailAlreadyRegistered(investor.getEmailAddress())) {
            throw new IllegalArgumentException("Email address is already registered.");
        }
        investor.setRegistrationDate(LocalDate.now());
        return investorRepository.save(investor);
    }

    public Investor updateInvestor(Long id, Investor updatedInvestor) {
        Optional<Investor> existingInvestorOptional = investorRepository.findById(id);
        if (existingInvestorOptional.isPresent()) {
            Investor existingInvestor = existingInvestorOptional.get();
            existingInvestor.setName(updatedInvestor.getName());
            existingInvestor.setSurname(updatedInvestor.getSurname());
            existingInvestor.setDateOfBirth(updatedInvestor.getDateOfBirth());
            existingInvestor.setAddress(updatedInvestor.getAddress());
            existingInvestor.setMobileNumber(updatedInvestor.getMobileNumber());
            existingInvestor.setEmailAddress(updatedInvestor.getEmailAddress());
            return investorRepository.save(existingInvestor);
        }
        return null;
    }

    public void deleteInvestor(Long id) {
        investorRepository.deleteById(id);
    }

    private boolean isEmailAlreadyRegistered(String emailAddress) {
        return investorRepository.existsByEmailAddress(emailAddress);
    }

    public boolean initiateWithdrawal(Long investorId, Long productId, BigDecimal withdrawalAmount) {
        Investor investor = investorRepository.findById(investorId).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);

        if (investor == null || product == null || withdrawalAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        if (product.getType() == ProductType.RETIREMENT && !isInvestorEligibleForRetirement(investor)) {
            return false;
        }

        BigDecimal currentBalance = product.getBalance();
        if (withdrawalAmount.compareTo(currentBalance.multiply(new BigDecimal("0.9"))) > 0) {
            return false;
        }
        WithdrawalProcess withdrawalProcess = new WithdrawalProcess();
        withdrawalProcess.setInvestor(investor);
        withdrawalProcess.setProduct(product);
        withdrawalProcess.setWithdrawalAmount(withdrawalAmount);
        withdrawalProcess.setStatus(WithdrawalStatus.STARTED);
        withdrawalProcess.setPreviousBalance(currentBalance);
        withdrawalProcess.setStatusChangedAt(LocalDateTime.now());
        withdrawalProcessRepository.save(withdrawalProcess);
        BigDecimal newBalance = currentBalance.subtract(withdrawalAmount);
        product.setBalance(newBalance);
        productRepository.save(product);
        withdrawalProcess.setStatus(WithdrawalStatus.DONE);
        withdrawalProcess.setPreviousBalance(currentBalance);
        withdrawalProcess.setStatusChangedAt(LocalDateTime.now());
        withdrawalProcessRepository.save(withdrawalProcess);

        return true;
    }
    public boolean isInvestorEligibleForRetirement(Investor investor) {
        if (investor == null || investor.getDateOfBirth() == null) {
            return false;
        }

        LocalDate today = LocalDate.now();
        LocalDate retirementAge = investor.getDateOfBirth().plusYears(65);
        Period period = Period.between(today, retirementAge);
        if (period.isNegative() || period.isZero()) {
            return true;
        }

        return false;
    }

    }


