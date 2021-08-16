package com.chabo.testcontainersdemo;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class HomepageController {

    private static final Logger logger = LoggerFactory.getLogger(HomepageController.class);

    private CustomerDAO customerDao;

    @GetMapping("/")
    public List<Customer> customers() {
        List<Customer> customers = customerDao.findAll();
        customers.forEach(c -> logger.info("Found a customer: {}", c));
        return customers;
    }
}
