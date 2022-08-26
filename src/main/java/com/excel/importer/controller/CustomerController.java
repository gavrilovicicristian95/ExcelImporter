package com.excel.importer.controller;


import com.excel.importer.model.Customer;
import com.excel.importer.model.FileMetadata;
import com.excel.importer.repository.CustomerRepository;
import com.excel.importer.repository.FileMetadataRepository;
import com.excel.importer.service.impl.CustomerService;
import com.excel.importer.util.ExcelHelper;
import com.excel.importer.util.UtilFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private FileMetadataRepository fileMetadataRepository;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("range") String range) {
        String message = "";

        UtilFunctions utilFunctions = new UtilFunctions();
        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                FileMetadata fileMetadata = utilFunctions.getMetadataFile(file);
                fileMetadataRepository.save(fileMetadata);
            } catch (IOException e){
                e.printStackTrace();
            }

            try {
                InputStream targetStream = new BufferedInputStream(file.getInputStream());
                customerRepository.saveAll(ExcelHelper.excelToCustomers(targetStream, range));

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(message);
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }
        }

        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @GetMapping("/opportunity")
    public List<Customer> getCustomerList(@RequestParam("team") Optional<String> team, @RequestParam("product") Optional<String> product, @RequestParam("bookingType") Optional<String> bookingType,
                                          @RequestParam Optional<Date> bookingDateStart, @RequestParam Optional<Date> bookingDateEnd){

        return customerService.findByCriteria(team.isPresent()?team.get() : null, product.isPresent()?product.get() : null, bookingType.isPresent()?bookingType.get() : null);
    }

    @GetMapping("/opportunity/{dateStart}/{dateEnd}")
    public List<Customer> getCustomerListByDateBeetween(@PathVariable Date dateStart, @PathVariable Date dateEnd){
        return customerRepository.findCustomerByBookingDateBetween(dateStart, dateEnd);
    }

}
