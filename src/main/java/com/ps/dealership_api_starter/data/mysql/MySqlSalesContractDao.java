package com.ps.dealership_api_starter.data.mysql;

import com.ps.dealership_api_starter.data.SalesContractDao;
import com.ps.dealership_api_starter.models.SalesContract;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MySqlSalesContractDao extends MySqlDaoBase implements SalesContractDao {

    public MySqlSalesContractDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public SalesContract getById(int contractId) {
        String sql = "SELECT * FROM sales_contracts WHERE contract_id = ?";

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
    public SalesContract create(SalesContract salesContract) {
        String sql = "INSERT INTO sales_contracts (vehicle_id, customer_id, contract_date, price) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, salesContract.getVin());
            statement.setInt(2, salesContract.getContractId());
            statement.setDate(3, java.sql.Date.valueOf(salesContract.getContractDate()));
            statement.setDouble(4, salesContract.getTotalPrice());

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

    private SalesContract mapRow(ResultSet rs) throws SQLException {
        SalesContract salesContract = new SalesContract();
        salesContract.setContractId(rs.getInt("contract_id"));
        salesContract.setVin(rs.getInt("vehicle_id"));
        salesContract.setCustomerName(String.valueOf(rs.getInt("customer_id")));
        salesContract.setContractDate(String.valueOf(rs.getDate("contract_date").toLocalDate()));
        salesContract.setTotalPrice(rs.getDouble("price"));
        return salesContract;
    }
}
