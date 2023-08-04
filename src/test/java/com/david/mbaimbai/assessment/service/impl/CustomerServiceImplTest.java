package com.david.mbaimbai.assessment.service.impl;

import com.david.mbaimbai.assessment.entity.Customer;
import com.david.mbaimbai.assessment.model.AccountDTO;
import com.david.mbaimbai.assessment.model.AccountResponseDTO;
import com.david.mbaimbai.assessment.model.AccountType;
import com.david.mbaimbai.assessment.model.CustomerRequestDTO;
import com.david.mbaimbai.assessment.repository.CustomerRepository;
import com.david.mbaimbai.assessment.service.AccountService;
import com.david.mbaimbai.assessment.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import({CustomerServiceImpl.class})
public class CustomerServiceImplTest {
    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepo;

    @MockBean
    private AccountService account;
    Customer customer;
    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setFirstName("Tatenda");
        customer.setEmail("tatenda.mbaimbai@gmail.com");
        customer.setMsisdn("0733933218");
        customer.setIdNumber("EN217158");
        customer.setLastName("Mbaimbai");
        customer.setId(UUID.randomUUID());
        when(customerRepo.findById(any(UUID.class))).thenReturn(Optional.of(customer));
    }

    @Test
    void saveCustomer() {

        when(account.createAccounts(any()))
                .thenReturn(AccountResponseDTO.builder()
                        .accountDTOList(List.of(AccountDTO.builder()
                                .accountNumber(1l)
                                .accountType(AccountType.SAVING)
                                .balance(BigDecimal.TEN)
                                .currency("R")
                                .id(UUID.randomUUID())
                                .build(), AccountDTO.builder()
                                .accountNumber(2l)
                                .accountType(AccountType.CURRENT)
                                .balance(BigDecimal.ONE)
                                .currency("R")
                                .id(UUID.randomUUID())
                                .build()))
                        .build());
        when(customerRepo.saveAndFlush(any())).thenReturn(customer);
        var response =
                customerService.saveCustomer(
                        CustomerRequestDTO.builder()
                                .email("tinotenda.mbaimbai@gmail.com").build());
        assertNotNull(response);
        assertEquals(2, response.getAccountResponseDTO().getAccountDTOList().size());
    }

    @Test
    void findCustomerById() {
        var response = customerRepo.findById(UUID.randomUUID());
        assertFalse(response.isEmpty());
        var customer = response.get();
        assertEquals("Tinotenda", customer.getFirstName());
    }
}
