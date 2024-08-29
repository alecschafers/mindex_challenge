package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;

import java.util.List;

public interface CompensationService {
    Compensation create(String id, double salary, String effectiveDate);
    List<Compensation> read(String id);
}
