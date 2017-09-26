package com.bhtask.nhs.backend.service;

import java.util.List;

import com.bhtask.nhs.backend.dao.Condition;

public interface ConditionsService {

    List<Condition> search(String query);

    List<String> getAllLinks();

    long getCount();

}
