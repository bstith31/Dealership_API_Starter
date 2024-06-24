package com.ps.dealership_api_starter.data;

import com.ps.dealership_api_starter.models.Vehicle;

import java.util.List;

public interface VehicleDAO {



    List<Vehicle> getAll( Double minPrice,
                          Double maxPrice,
                          String make,
                          String model,
                          Integer minYear,
                          Integer maxYear,
                          String color,
                          Integer minMiles,
                          Integer maxMiles,
                          String type);
    Vehicle getById (int vehicleId );
    Vehicle create(Vehicle vehicle);
    void update(int vehicleId, Vehicle vehicle);
    void delete(int vehicleId);
}
