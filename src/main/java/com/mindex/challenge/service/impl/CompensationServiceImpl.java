package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CompensationRepository compensationRepository;

    /*
        Creates a new compensation for an employee and inserts into the database
        Uses the employeeId to find the employee in the database
        Ensures that the employee exists, fetch the record, create compensation, and insert
     */
    @Override
    public Compensation create(String id, double salary, String effectiveDate) {
        LOG.debug("Creating compensation for existing employee id [{}]", id);
        Compensation compensation = new Compensation();
        Employee employee = employeeService.read(id);
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }
        compensation.setEmployee(employee);
        compensation.setSalary(salary);
        compensation.setEffectiveDate(effectiveDate);
        compensationRepository.insert(compensation);
        return compensation;
    }
        // Fetch the compensation from the database using findByEmployeeEmployeeId
        // Retrieves a compensation record from the database based on the ID of the employee it belongs to
    @Override
    public List<Compensation> read(String id) {
        LOG.debug("Fetching compensation for employee id [{}]", id);
        List<Compensation> employeeCompensation = compensationRepository.findByEmployeeEmployeeId(id);
        if(employeeCompensation == null) {
            throw new RuntimeException("No compensation found for employee id: " + id + ". Are you using the correct employee id?");
        }
        return employeeCompensation;
    }
}
