import java.sql.*;
import java.util.ArrayList;

public class CategoryDAO {
    public boolean insertCategory(Category category) {
        String sql = "INSERT INTO categories (category_name) VALUES (?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, category.getCategoryName());

            int rows = stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    category.setCategoryId((rs.getInt(1)));
                }
            }

            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Category getCategoryById(int id) {
        String sql = "SELECT * FROM categories WHERE category_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Category c = new Category();
                c.setCategoryId(rs.getInt("category_id"));
                c.setCategoryName(rs.getString("category_name"));

                return c;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateCategory(Category category) {
        String sql = "UPDATE categories SET category_name = ? WHERE category_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getCategoryName());
            stmt.setInt(2, category.getCategoryId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories ORDER BY category_id";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Category c = new Category();
                c.setCategoryId(rs.getInt("category_id"));
                c.setCategoryName(rs.getString("category_name"));
                categories.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return categories;
    }
}
