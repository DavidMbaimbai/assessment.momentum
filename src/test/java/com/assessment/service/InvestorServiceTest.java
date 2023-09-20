package com.assessment.service;

import com.assessment.entity.Investor;
import com.assessment.entity.Product;
import com.assessment.enums.ProductType;
import com.assessment.repository.InvestorRepository;
import com.assessment.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InvestorServiceTest {

    @Mock
    private InvestorRepository investorRepository;

    @Mock
    private ProductRepository productRepository;

    private InvestorService investorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        investorService = new InvestorService(investorRepository, null, productRepository);
    }

    @Test
    public void testCreateInvestor() {
        Investor newInvestor = new Investor();
        newInvestor.setName("John");
        newInvestor.setSurname("Doe");
        newInvestor.setDateOfBirth(LocalDate.of(1990, 1, 1));
        newInvestor.setAddress("123 Main St");
        newInvestor.setMobileNumber("555-555-5555");
        newInvestor.setEmailAddress("john@example.com");
        newInvestor.setRegistrationDate(LocalDate.now());
        when(investorRepository.existsByEmailAddress(newInvestor.getEmailAddress())).thenReturn(false);
        when(investorRepository.save(newInvestor)).thenReturn(newInvestor);
        Investor createdInvestor = investorService.createInvestor(newInvestor);
        assertNotNull(createdInvestor);
        assertEquals(newInvestor, createdInvestor);
    }


    @Test
    public void testUpdateInvestor() {
        Long investorId = 1L;
        Investor updatedInvestor = new Investor();
        updatedInvestor.setName("Updated Name");
        updatedInvestor.setSurname("Updated Surname");
        updatedInvestor.setDateOfBirth(LocalDate.of(1995, 1, 1));
        updatedInvestor.setAddress("Updated Address");
        updatedInvestor.setMobileNumber("555-555-5555");
        updatedInvestor.setEmailAddress("updated@example.com");
        updatedInvestor.setRegistrationDate(LocalDate.now());
        Investor existingInvestor = new Investor();
        existingInvestor.setId(investorId);
        existingInvestor.setName("John");
        existingInvestor.setSurname("Doe");
        existingInvestor.setDateOfBirth(LocalDate.of(1990, 1, 1));
        existingInvestor.setAddress("123 Main St");
        existingInvestor.setMobileNumber("555-555-5555");
        existingInvestor.setEmailAddress("john@example.com");
        existingInvestor.setRegistrationDate(LocalDate.now());
        when(investorRepository.findById(investorId)).thenReturn(Optional.of(existingInvestor));
        when(investorRepository.save(existingInvestor)).thenReturn(updatedInvestor);
        Investor updatedInvestorDb = investorService.updateInvestor(investorId, updatedInvestor);
        assertNotNull(updatedInvestorDb);
        assertEquals(updatedInvestor, updatedInvestorDb);
    }


    @Test
    public void testDeleteInvestor() {
        Long investorId = 1L;

        investorService.deleteInvestor(investorId);

        verify(investorRepository, times(1)).deleteById(investorId);
    }

    @Test
    public void testInitiateWithdrawal() {
        Long investorId = 1L;
        Long productId = 2L;
        BigDecimal withdrawalAmount = BigDecimal.valueOf(500.0);
        Investor investor = new Investor();
        investor.setId(investorId);
        investor.setName("John");
        investor.setSurname("Doe");
        Product product = new Product();
        product.setId(productId);
        product.setProductId("P1");
        product.setType(ProductType.RETIREMENT);
        product.setName("Retirement Fund");
        product.setCurrentBalance(BigDecimal.valueOf(1000.0));
        product.setBalance(BigDecimal.valueOf(1500.0));
        when(investorRepository.findById(investorId)).thenReturn(Optional.of(investor));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        boolean withdrawalInitiated = investorService.initiateWithdrawal(investorId, productId, withdrawalAmount);
        assertTrue(withdrawalInitiated);
    }


    @Test
    public void testIsInvestorEligibleForRetirement() {
        Investor eligibleInvestor = new Investor();
        eligibleInvestor.setDateOfBirth(LocalDate.of(1950, 1, 1));
        Investor ineligibleInvestor = new Investor();
        ineligibleInvestor.setDateOfBirth(LocalDate.of(1990, 1, 1));
        assertTrue(investorService.isInvestorEligibleForRetirement(eligibleInvestor));
        assertFalse(investorService.isInvestorEligibleForRetirement(ineligibleInvestor));
    }

}
