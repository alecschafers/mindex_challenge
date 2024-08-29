package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String compensationUrl;
    private String compensationIdUrl;
    private String employeeUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensation/{id}/{salary}/{effectiveDate}";
        compensationIdUrl = "http://localhost:" + port + "/compensation/{id}";
        employeeUrl = "http://localhost:" + port + "/employee";
    }

    @Test
    public void testCreateRead() {
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");
        Compensation testCompensation = new Compensation();
        testCompensation.setSalary(1000.00);
        testCompensation.setEffectiveDate("01-01-2020");

        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();

        List<String> uriVariables = new ArrayList<>();
        uriVariables.add(createdEmployee.getEmployeeId());
        uriVariables.add(String.valueOf(testCompensation.getSalary()));
        uriVariables.add(testCompensation.getEffectiveDate());

        // Create checks
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, null, Compensation.class, uriVariables.toArray()).getBody();


        assertNotNull(createdCompensation.getEmployee().getEmployeeId());
        assertEmployeeEquivalence(testEmployee, createdEmployee);
        assertCompensationEquivalence(testCompensation, createdCompensation);


//         Read checks
        ResponseEntity<List<Compensation>> readResponse = restTemplate.exchange(compensationIdUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Compensation>>() {}, createdEmployee.getEmployeeId());
        List<Compensation> readCompensations = readResponse.getBody();
        assertNotNull(readCompensations.get(0).getEmployee().getEmployeeId());
        assertCompensationEquivalence(testCompensation, readCompensations.get(0));
    }

    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }
    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
        assertEquals(expected.getSalary(), actual.getSalary(), 0);
    }
}
