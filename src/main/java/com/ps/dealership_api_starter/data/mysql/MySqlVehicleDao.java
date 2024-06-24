package com.ps.dealership_api_starter.data.mysql;

import com.ps.dealership_api_starter.data.VehicleDAO;
import com.ps.dealership_api_starter.models.Vehicle;

import javax.sql.DataSource;
import java.util.List;

public class MySqlVehicleDao extends MySqlDaoBase implements VehicleDAO {
    public MySqlVehicleDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Vehicle> getAll(Double minPrice, Double maxPrice, String make, String model, Integer minYear, Integer maxYear, String color, Integer minMiles, Integer maxMiles, String type) {
        return List.of();
    }

    @Override
    public Vehicle getById(int vehicleId) {
        return null;
    }

    @Override
    public Vehicle create(Vehicle vehicle) {
        return null;
    }

    @Override
    public void update(int vehicleId, Vehicle vehicle) {

    }

    @Override
    public void delete(int vehicleId) {

    }
}
