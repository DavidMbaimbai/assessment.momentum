package com.assessment.controllers;

import com.assessment.entity.Investor;
import com.assessment.service.InvestorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/investors")
public class InvestorController {
        private final InvestorService investorService;

        @Autowired
        public InvestorController(InvestorService investorService) {
            this.investorService = investorService;
        }

        @GetMapping("/all")
        public ResponseEntity<List<Investor>> getAllInvestors() {
            List<Investor> investors = investorService.getAllInvestors();
            return ResponseEntity.ok(investors);
        }

        @GetMapping("/{id}")
        public ResponseEntity<Optional<Investor>> getInvestorById(@PathVariable Long id) {
            Optional<Investor> investor = investorService.getInvestorById(id);
            if (investor != null) {
                return ResponseEntity.ok(investor);
            }
            return ResponseEntity.notFound().build();
        }

        @PostMapping("/create")
        public ResponseEntity<Investor> createInvestor(@RequestBody Investor investor) {
            Investor createdInvestor = investorService.createInvestor(investor);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdInvestor);
        }

        @PutMapping("/update")
        public ResponseEntity<Investor> updateInvestor(@PathVariable Long id, @RequestBody Investor updatedInvestor) {
            Investor updatedInvestorDb = investorService.updateInvestor(id, updatedInvestor);
            if (updatedInvestorDb != null) {
                return ResponseEntity.ok(updatedInvestorDb);
            }
            return ResponseEntity.notFound().build();
        }

        @DeleteMapping("/delete")
        public ResponseEntity<Void> deleteInvestor(@PathVariable Long id) {
            investorService.deleteInvestor(id);
            return ResponseEntity.noContent().build();
        }
    }

