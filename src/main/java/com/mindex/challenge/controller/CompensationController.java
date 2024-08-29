package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    @PostMapping("/compensation/{id}/{salary}/{effectiveDate}")
    public Compensation create(@PathVariable String id, @PathVariable String salary, @PathVariable String effectiveDate) {
        LOG.debug("Received Compensation create request with employee id [{}], salary [{}], and effective date [{}]", id, salary, effectiveDate);
        return compensationService.create(id, Double.parseDouble(salary), effectiveDate);
    }

    @GetMapping("/compensation/{id}")
    public List<Compensation> read(@PathVariable String id) {
        LOG.debug("Received compensation read request for id [{}]", id);

        return compensationService.read(id);
    }
}
