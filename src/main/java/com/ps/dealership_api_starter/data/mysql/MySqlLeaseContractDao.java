package com.ps.dealership_api_starter.data.mysql;

import com.ps.dealership_api_starter.data.LeaseContractDao;
import com.ps.dealership_api_starter.models.LeaseContract;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MySqlLeaseContractDao extends MySqlDaoBase implements LeaseContractDao {

    public MySqlLeaseContractDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public LeaseContract getById(int contractId) {
        String sql = "SELECT * FROM lease_contracts WHERE contract_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, contractId);
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
    public LeaseContract create(LeaseContract leaseContract) {
        String sql = "INSERT INTO lease_contracts (vehicle_id, customer_id, contract_date, price) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, leaseContract.getVin());
            statement.setInt(2, leaseContract.getContractId());
            statement.setDate(3, java.sql.Date.valueOf(leaseContract.getContractDate()));
            statement.setDouble(4, leaseContract.getTotalPrice());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int contractId = generatedKeys.getInt(1);
                    return getById(contractId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return null;
    }

    private LeaseContract mapRow(ResultSet rs) throws SQLException {
        LeaseContract leaseContract = new LeaseContract();
        leaseContract.setContractId(rs.getInt("contract_id"));
        leaseContract.setVin(rs.getInt("vehicle_id"));
        leaseContract.setCustomerName(String.valueOf(rs.getInt("customer_id")));
        leaseContract.setContractDate(String.valueOf(rs.getDate("contract_date").toLocalDate()));
        leaseContract.setTotalPrice(rs.getDouble("price"));
        return leaseContract;
    }
}
