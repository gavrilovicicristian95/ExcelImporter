package com.excel.importer.service.impl;

import com.excel.importer.model.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> findByCriteria(String team, String product, String bookingType);
}
