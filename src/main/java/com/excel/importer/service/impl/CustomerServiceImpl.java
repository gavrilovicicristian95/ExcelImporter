package com.excel.importer.service.impl;

import com.excel.importer.model.Customer;
import com.excel.importer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> findByCriteria(String team, String product, String bookingType) {
        return customerRepository.findAll(new Specification<>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (team != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("team"), team)));
                }

                if (product != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("product"), product)));
                }

                if (bookingType != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("bookingType"), bookingType)));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }
}
