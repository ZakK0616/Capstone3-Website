package org.yearup.data.mysql;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {

    private final ProductDao productDao;

    @Autowired
    public MySqlShoppingCartDao(DataSource dataSource, ProductDao productDao) {
        super(dataSource);
        this.productDao = productDao;
    }


    @Override
    public ShoppingCart getByUserId(int userId) {

        ShoppingCart cart = new ShoppingCart();

        String sql = """
            SELECT product_id, quantity
            FROM shopping_cart
            WHERE user_id = ?
        """;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            ResultSet row = statement.executeQuery();

            while (row.next()) {
                int productId = row.getInt("product_id");
                int quantity = row.getInt("quantity");

                Product product = productDao.getById(productId);

                ShoppingCartItem item = new ShoppingCartItem();
                item.setProduct(product);
                item.setQuantity(quantity);
                item.setDiscountPercent(BigDecimal.ZERO);

                cart.add(item);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException("Error getting shopping cart", e);
        }

        return cart;
    }


    @Override
    public void addProduct(int userId, int productId) {

        String checkSql = """
            SELECT quantity
            FROM shopping_cart
            WHERE user_id = ? AND product_id = ?
        """;

        String insertSql = """
            INSERT INTO shopping_cart (user_id, product_id, quantity)
            VALUES (?, ?, 1)
        """;

        String updateSql = """
            UPDATE shopping_cart
            SET quantity = quantity + 1
            WHERE user_id = ? AND product_id = ?
        """;

        try (Connection connection = getConnection();
             PreparedStatement check = connection.prepareStatement(checkSql)) {

            check.setInt(1, userId);
            check.setInt(2, productId);

            ResultSet row = check.executeQuery();

            if (row.next()) {
                try (PreparedStatement update = connection.prepareStatement(updateSql)) {
                    update.setInt(1, userId);
                    update.setInt(2, productId);
                    update.executeUpdate();
                }
            }
            else {
                try (PreparedStatement insert = connection.prepareStatement(insertSql)) {
                    insert.setInt(1, userId);
                    insert.setInt(2, productId);
                    insert.executeUpdate();
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException("Error adding product to cart", e);
        }
    }


    @Override
    public void updateQuantity(int userId, int productId, int quantity) {

        String sql = """
            UPDATE shopping_cart
            SET quantity = ?
            WHERE user_id = ? AND product_id = ?
        """;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, quantity);
            statement.setInt(2, userId);
            statement.setInt(3, productId);

            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException("Error updating cart quantity", e);
        }
    }


    @Override
    public void clearCart(int userId) {

        String sql = """
            DELETE FROM shopping_cart
            WHERE user_id = ?
        """;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException("Error clearing shopping cart", e);
        }
    }
}

