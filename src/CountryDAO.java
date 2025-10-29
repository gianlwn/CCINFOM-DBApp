import java.sql.*;

public class CountryDAO {
    public boolean insertCountry(Country country) {
        String sql = "INSERT INTO countries (country_id, country_name) VALUES (?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, country.getCountryId());
            stmt.setString(2, country.getCountryName());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Country getCountryById(int id) {
        String sql = "SELECT * FROM countries WHERE country_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Country c = new Country();
                c.setCountryId(rs.getInt("country_id"));
                c.setCountryName(rs.getString("country_name"));

                return c;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateCountry(Country country) {
        String sql = "UPDATE countries SET country_name=? WHERE country_id=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, country.getCountryName());
            stmt.setInt(2, country.getCountryId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteCountry(int id) {
        String sql = "DELETE FROM countries WHERE country_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
}
