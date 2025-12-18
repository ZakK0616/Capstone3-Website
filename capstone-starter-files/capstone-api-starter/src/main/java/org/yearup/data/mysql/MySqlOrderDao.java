package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yearup.data.OrderDao;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MySqlOrderDao extends MySqlDaoBase implements OrderDao
{
    @Autowired
    public MySqlOrderDao(DataSource dataSource)
    {
        super(dataSource);
    }

    @Override
    public int createOrder(int userId, BigDecimal total) {
       String sql = """
               INSERT INTO orders (user_id, total)
               VALUES (?, ?)
               """;
       try (Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS))
       {
           statement.setInt(1 , userId);
           statement.setBigDecimal(2, total);
           statement.executeUpdate();

           ResultSet keys = statement.getGeneratedKeys();
           if (keys.next()) {
               return keys.getInt(1);
           }
           throw new SQLException("Order ID not returned.");
       }
catch (SQLException e)
{
    throw new RuntimeException("Error catching your order", e);
}
    }

    @Override
    public void addLineItem(int orderId, int productId, int quantity, BigDecimal price) {
        String sql = """
                INSERT INTO order_line_items (order_id, product_id, price)
                VALUES (?, ?, ?)
                """;
        try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setInt(1, orderId);
            statement.setInt(2, productId);
            statement.setBigDecimal(3, price);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Error creating order line item", e);

        }

    }
}
