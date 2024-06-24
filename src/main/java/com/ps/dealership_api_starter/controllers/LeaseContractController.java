package com.ps.dealership_api_starter.controllers;

import com.ps.dealership_api_starter.data.LeaseContractDao;
import com.ps.dealership_api_starter.data.SalesContractDao;
import com.ps.dealership_api_starter.models.LeaseContract;
import com.ps.dealership_api_starter.models.SalesContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

public class LeaseContractController {

    private LeaseContractDao leaseContractDao;

    @Autowired
    public LeaseContractController(LeaseContractDao leaseContractDao) {
        this.leaseContractDao = leaseContractDao;
    }

    @GetMapping("/{id}")
    public LeaseContract getLeaseContractById(@PathVariable int id) {
        try {
            LeaseContract leaseContract = leaseContractDao.getById(id);
            if (leaseContract == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lease contract not found");
            }
            return leaseContract;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while retrieving lease contract");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LeaseContract createSalesContract(@RequestBody LeaseContract leaseContract) {
        try {
            return leaseContractDao.create(leaseContract);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while creating lease contract");
        }
    }
}

