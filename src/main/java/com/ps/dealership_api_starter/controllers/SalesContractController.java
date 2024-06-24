package com.ps.dealership_api_starter.controllers;

import com.ps.dealership_api_starter.data.SalesContractDao;
import com.ps.dealership_api_starter.models.SalesContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/sales-contracts")
@CrossOrigin
public class SalesContractController {

    private SalesContractDao salesContractDao;

    @Autowired
    public SalesContractController(SalesContractDao salesContractDao) {
        this.salesContractDao = salesContractDao;
    }

    @GetMapping("/{id}")
    public SalesContract getSalesContractById(@PathVariable int id) {
        try {
            SalesContract salesContract = salesContractDao.getById(id);
            if (salesContract == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sales contract not found");
            }
            return salesContract;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while retrieving sales contract");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SalesContract createSalesContract(@RequestBody SalesContract salesContract) {
        try {
            return salesContractDao.create(salesContract);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while creating sales contract");
        }
    }
}
