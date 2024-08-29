package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportingServiceImpl implements ReportingService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingServiceImpl.class);

    @Autowired
    private EmployeeService employeeService;

    // Returns Reporting structure of an employee and all of their direct reports
    // employee value contains all the employee objects that report to the parent employee
    // numberOfReports value contains the total number of employees that report to the parent employee
    @Override
    public ReportingStructure read(String id) {
        LOG.debug("Fetching employee reporting structure with id [{}]", id);

        Employee employee = employeeService.read(id);
        int reportingEmployeeCount = employee.getDirectReports().size() + getReportingEmployees(employee);
        return new ReportingStructure(employee, reportingEmployeeCount);

    }

    // Get total number of employees that report to the parent employee and all of their direct reports
    // First use the parent employee stream to get the ids of the direct reports and then call db to get their employee object values
    // Next add them all to the parent employee
    // Finally stream through the direct reports, filter out employees that do not have any reporters, and recurse through the function
    // Keep a running total of reports and return the value out of the stream when recursion ends
    private int getReportingEmployees(Employee parentEmployee) {
        List<Employee> directReports = parentEmployee.getDirectReports()
                .stream()
                .map(Employee::getEmployeeId)
                .map(employeeService::read)
                .collect(Collectors.toList());
        parentEmployee.getDirectReports().clear();
        parentEmployee.getDirectReports().addAll(directReports);

        return directReports.stream()
                .filter(employee -> employee.getDirectReports() != null)
                .mapToInt(employee -> employee.getDirectReports().size() + getReportingEmployees(employee))
                .sum();
    }

}
