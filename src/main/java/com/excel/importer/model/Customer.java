package com.excel.importer.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "customers")
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long customerId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "booking_date")
    private Date bookingDate;

    @Column(name = "opportunity_id")
    private String opportunityId;

    @Column(name = "booking_type")
    private String bookingType;

    @Column(name = "total")
    private String total;

    @Column(name = "account_executive")
    private String accountExecutive;

    @Column(name = "sales_organization")
    private String salesOrganization;

    @Column(name = "team")
    private String team;

    @Column(name = "product")
    private String product;

    @Column(name = "renewable")
    private String renewable;

}
