package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yearup.models.Profile;
import org.yearup.data.ProfileDao;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class MySqlProfileDao extends MySqlDaoBase implements ProfileDao {
    @Autowired
    public MySqlProfileDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Profile getByUserId(int userId) {
        String sql = """
                SELECT user_id, first_name, last_name, phone, email, address, city, state, zip
                FROM profiles 
                WHERE user_id = ?
                """;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            ResultSet row = statement.executeQuery();

            if (row.next()) {
                Profile p = new Profile();
                p.setUserId(row.getInt("user_id"));
                p.setFirstName(row.getString("first_name"));
                p.setLastName(row.getString("last_name"));
                p.setPhone(row.getString("phone"));
                p.setEmail(row.getString("email"));
                p.setAddress(row.getString("address"));
                p.setCity(row.getString("city"));
                p.setState(row.getString("state"));
                p.setZip(row.getString("zip"));
                return p;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting profile", e);
        }
    }

    @Override
    public void update(int userId, Profile profile) {
        String sql = """
                    UPDATE profiles
                    SET first_name = ?,
                        last_name  = ?,
                        phone      = ?,
                        email      = ?,
                        address    = ?,
                        city       = ?,
                        state      = ?,
                        zip        = ?
                    WHERE user_id = ?
                """;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, profile.getFirstName());
            statement.setString(2, profile.getLastName());
            statement.setString(3, profile.getPhone());
            statement.setString(4, profile.getEmail());
            statement.setString(5, profile.getAddress());
            statement.setString(6, profile.getCity());
            statement.setString(7, profile.getState());
            statement.setString(8, profile.getZip());
            statement.setInt(9, userId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating profile", e);
        }
    }


    @Override
    public Profile create(Profile profile) {
        String sql = "INSERT INTO profiles (user_id, first_name, last_name, phone, email, address, city, state, zip) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, profile.getUserId());
            ps.setString(2, profile.getFirstName());
            ps.setString(3, profile.getLastName());
            ps.setString(4, profile.getPhone());
            ps.setString(5, profile.getEmail());
            ps.setString(6, profile.getAddress());
            ps.setString(7, profile.getCity());
            ps.setString(8, profile.getState());
            ps.setString(9, profile.getZip());

            ps.executeUpdate();

            return profile;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
