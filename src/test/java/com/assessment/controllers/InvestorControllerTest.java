package com.assessment.controllers;

import com.assessment.controllers.InvestorController;
import com.assessment.entity.Investor;
import com.assessment.service.InvestorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class InvestorControllerTest {

    @Mock
    private InvestorService investorService;

    private InvestorController investorController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        investorController = new InvestorController(investorService);
    }

    @Test
    public void testGetAllInvestors() {
        List<Investor> investors = new ArrayList<>();

        Investor investor1 = new Investor();
        investor1.setId(1L);
        investor1.setName("John Doe");
        investor1.setEmailAddress("john@example.com");
        investors.add(investor1);
        when(investorService.getAllInvestors()).thenReturn(investors);
        ResponseEntity<List<Investor>> response = investorController.getAllInvestors();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(investors, response.getBody());
    }


    @Test
    public void testGetInvestorById() {
        Long investorId = 1L;
        Investor sampleInvestor = new Investor();
        sampleInvestor.setId(investorId);
        sampleInvestor.setName("David Mbaimbai");
        sampleInvestor.setEmailAddress("davy@gmail.com");

        Optional<Investor> investor = Optional.of(sampleInvestor);
        when(investorService.getInvestorById(investorId)).thenReturn(investor);
        ResponseEntity<Optional<Investor>> response = investorController.getInvestorById(investorId);
        if (investor.isPresent()) {
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(investor, response.getBody());
        } else {
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }
    }


    @Test
    public void testCreateInvestor() {
        Investor newInvestor = new Investor();
        newInvestor.setName("David");
        newInvestor.setSurname("Mbaimbai");
        newInvestor.setDateOfBirth(LocalDate.of(1990, 1, 1));
        newInvestor.setAddress("123 Main St");
        newInvestor.setMobileNumber("555-555-5555");
        newInvestor.setEmailAddress("davy@gmail.com");
        newInvestor.setRegistrationDate(LocalDate.now());
        Investor createdInvestor = newInvestor;
        when(investorService.createInvestor(newInvestor)).thenReturn(createdInvestor);
        ResponseEntity<Investor> response = investorController.createInvestor(newInvestor);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdInvestor, response.getBody());
    }


    @Test
    public void testUpdateInvestor() {
        Long investorId = 1L;
        Investor updatedInvestor = new Investor();
        updatedInvestor.setName("Davie");
        updatedInvestor.setEmailAddress("davie.email@gmail.com");
        Investor updatedInvestorDb = new Investor();
        updatedInvestorDb.setId(investorId);
        updatedInvestorDb.setName("Davie");
        updatedInvestorDb.setSurname("Doe");
        updatedInvestorDb.setDateOfBirth(LocalDate.of(1990, 1, 1));
        updatedInvestorDb.setAddress("123 Main St");
        updatedInvestorDb.setMobileNumber("555-555-5555");
        updatedInvestorDb.setEmailAddress("davie.email@gmail.com");
        updatedInvestorDb.setRegistrationDate(LocalDate.now());
        when(investorService.updateInvestor(investorId, updatedInvestor)).thenReturn(updatedInvestorDb);
        ResponseEntity<Investor> response = investorController.updateInvestor(investorId, updatedInvestor);
        if (updatedInvestorDb != null) {
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(updatedInvestorDb, response.getBody());
        } else {
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }
    }


    @Test
    public void testDeleteInvestor() {
        Long investorId = 1L;

        ResponseEntity<Void> response = investorController.deleteInvestor(investorId);

        verify(investorService, times(1)).deleteInvestor(investorId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
