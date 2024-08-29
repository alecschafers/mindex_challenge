package com.mindex.challenge.controller;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReportingController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingController.class);

    @Autowired
    private ReportingService reportingService;

    @GetMapping("/reportStructure/{id}")
    public ReportingStructure getReportStructure(@PathVariable String id) {
        LOG.debug("Received employee reporting structure request for id [{}]", id);

        return reportingService.read(id);
    }

}
