package com.ps.dealership_api_starter.controllers;

import com.ps.dealership_api_starter.data.VehicleDAO;
import com.ps.dealership_api_starter.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
@CrossOrigin
public class VehicleController {

    private VehicleDAO vehicleDao;

    @Autowired
    public VehicleController(VehicleDAO vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    @GetMapping
    public List<Vehicle> searchVehicles(
            @RequestParam(name = "minPrice", required = false) Double minPrice,
            @RequestParam(name = "maxPrice", required = false) Double maxPrice,
            @RequestParam(name = "make", required = false) String make,
            @RequestParam(name = "model", required = false) String model,
            @RequestParam(name = "minYear", required = false) Integer minYear,
            @RequestParam(name = "maxYear", required = false) Integer maxYear,
            @RequestParam(name = "color", required = false) String color,
            @RequestParam(name = "minMiles", required = false) Integer minMiles,
            @RequestParam(name = "maxMiles", required = false) Integer maxMiles,
            @RequestParam(name = "type", required = false) String type
    ) {
        try {
            return vehicleDao.getAll(minPrice, maxPrice, make, model, minYear, maxYear, color, minMiles, maxMiles, type);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while searching for vehicles");
        }
    }

    @GetMapping("/{id}")
    public Vehicle getVehicleById(@PathVariable int id) {
        try {
            Vehicle vehicle = vehicleDao.getById(id);
            if (vehicle == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found");
            }
            return vehicle;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while retrieving vehicle");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicle createVehicle(@RequestBody Vehicle vehicle) {
        try {
            return vehicleDao.create(vehicle);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while creating vehicle");
        }
    }

    @PutMapping("/{id}")
    public Vehicle updateVehicle(@PathVariable int id, @RequestBody Vehicle vehicle) {
        try {
            vehicleDao.update(id, vehicle);
            return vehicleDao.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while updating vehicle");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVehicle(@PathVariable int id) {
        try {
            vehicleDao.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while deleting vehicle");
        }
    }
}