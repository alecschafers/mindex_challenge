package com.mindex.challenge.data;

import javax.persistence.*;

import java.time.format.DateTimeFormatter;

public class Compensation {
    @ManyToOne
    @JoinColumn(name = "employeeId")
    private Employee employee;
    private double salary;
    private String effectiveDate;

    public Employee getEmployee() {
        return employee;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    public double getSalary() {
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }
    public String getEffectiveDate() {
        return effectiveDate;
    }
    public void setEffectiveDate(String effectiveDate) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        try {
            dateFormat.parse(effectiveDate);
            this.effectiveDate = effectiveDate;
        } catch (Exception e) {
            throw new RuntimeException("Use Date format MM-dd-yyyy");
        }
    }
    public Compensation buildCompensation(Employee employee, Compensation compensation) {
        compensation.setEmployee(employee);
        return compensation;
    }
}
