package com.ps.dealership_api_starter.data.mysql;

import com.ps.dealership_api_starter.data.VehicleDAO;
import com.ps.dealership_api_starter.models.Vehicle;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlVehicleDao extends MySqlDaoBase implements VehicleDAO {

    public MySqlVehicleDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Vehicle> getAll(Double minPrice, Double maxPrice, String make, String model, Integer minYear, Integer maxYear, String color, Integer minMiles, Integer maxMiles, String type) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles WHERE " +
                "(? IS NULL OR price >= ?) AND " +
                "(? IS NULL OR price <= ?) AND " +
                "(? IS NULL OR make LIKE ?) AND " +
                "(? IS NULL OR model LIKE ?) AND " +
                "(? IS NULL OR year >= ?) AND " +
                "(? IS NULL OR year <= ?) AND " +
                "(? IS NULL OR color LIKE ?) AND " +
                "(? IS NULL OR miles >= ?) AND " +
                "(? IS NULL OR miles <= ?) AND " +
                "(? IS NULL OR type LIKE ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, minPrice);
            statement.setObject(2, minPrice);
            statement.setObject(3, maxPrice);
            statement.setObject(4, maxPrice);
            statement.setObject(5, make != null ? "%" + make + "%" : null);
            statement.setObject(6, make != null ? "%" + make + "%" : null);
            statement.setObject(7, model != null ? "%" + model + "%" : null);
            statement.setObject(8, model != null ? "%" + model + "%" : null);
            statement.setObject(9, minYear);
            statement.setObject(10, minYear);
            statement.setObject(11, maxYear);
            statement.setObject(12, maxYear);
            statement.setObject(13, color != null ? "%" + color + "%" : null);
            statement.setObject(14, color != null ? "%" + color + "%" : null);
            statement.setObject(15, minMiles);
            statement.setObject(16, minMiles);
            statement.setObject(17, maxMiles);
            statement.setObject(18, maxMiles);
            statement.setObject(19, type != null ? "%" + type + "%" : null);
            statement.setObject(20, type != null ? "%" + type + "%" : null);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Vehicle vehicle = mapRow(rs);
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return vehicles;
    }

    @Override
    public Vehicle getById(int vehicleId) {
        String sql = "SELECT * FROM vehicles WHERE vehicle_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, vehicleId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Vehicle create(Vehicle vehicle) {
        String sql = "INSERT INTO vehicles (make, model, year, color, price, miles, type) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, vehicle.getMake());
            statement.setString(2, vehicle.getModel());
            statement.setInt(3, vehicle.getYear());
            statement.setString(4, vehicle.getColor());
            statement.setDouble(5, vehicle.getPrice());
            statement.setInt(6, vehicle.getOdometer());
            statement.setString(7, vehicle.getVehicleType());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int vehicleId = generatedKeys.getInt(1);
                    return getById(vehicleId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public void update(int vehicleId, Vehicle vehicle) {
        String sql = "UPDATE vehicles SET make = ?, model = ?, year = ?, color = ?, price = ?, miles = ?, type = ? WHERE vehicle_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, vehicle.getMake());
            statement.setString(2, vehicle.getModel());
            statement.setInt(3, vehicle.getYear());
            statement.setString(4, vehicle.getColor());
            statement.setDouble(5, vehicle.getPrice());
            statement.setInt(6, vehicle.getOdometer());
            statement.setString(7, vehicle.getVehicleType());
            statement.setInt(8, vehicleId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int vehicleId) {
        String sql = "DELETE FROM vehicles WHERE vehicle_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, vehicleId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Vehicle mapRow(ResultSet rs) throws SQLException {
        Vehicle vehicle = new Vehicle();
        vehicle.setVin(rs.getInt("vehicle_id"));
        vehicle.setMake(rs.getString("make"));
        vehicle.setModel(rs.getString("model"));
        vehicle.setYear(rs.getInt("year"));
        vehicle.setColor(rs.getString("color"));
        vehicle.setPrice(rs.getDouble("price"));
        vehicle.setOdometer(rs.getInt("miles"));
        vehicle.setVehicleType(rs.getString("type"));
        return vehicle;
    }
}