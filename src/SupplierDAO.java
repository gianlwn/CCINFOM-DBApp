import java.sql.*;
import java.util.ArrayList;

public class SupplierDAO {
    public boolean insertSupplier(Supplier supplier) {
        String sql = "INSERT INTO suppliers (supplier_id, supplier_name, contact_person, contact_number, email, address, last_delivery_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, supplier.getSupplierId());
            stmt.setString(2, supplier.getSupplierName());
            stmt.setString(3, supplier.getContactPerson());
            stmt.setString(4, supplier.getContactNumber());
            stmt.setString(5, supplier.getEmail());
            stmt.setString(6, supplier.getAddress());
            stmt.setDate(7, supplier.getLastDeliveryDate());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Supplier getSupplierById(int id) {
        String sql = "SELECT * FROM suppliers WHERE supplier_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Supplier s = new Supplier();
                s.setSupplierId(rs.getInt("supplier_id"));
                s.setSupplierName(rs.getString("supplier_name"));
                s.setContactPerson(rs.getString("contact_person"));
                s.setContactNumber(rs.getString("contact_number"));
                s.setEmail(rs.getString("email"));
                s.setAddress(rs.getString("address"));
                s.setLastDeliveryDate(rs.getDate("last_delivery_date"));

                return s;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateSupplier(Supplier supplier) {
        String sql = "UPDATE suppliers SET supplier_name=?, contact_person=?, contact_number=?, email=?, address=?, last_delivery_date=? WHERE supplier_id=?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, supplier.getSupplierName());
            stmt.setString(2, supplier.getContactPerson());
            stmt.setString(3, supplier.getContactNumber());
            stmt.setString(4, supplier.getEmail());
            stmt.setString(5, supplier.getAddress());
            stmt.setDate(6, supplier.getLastDeliveryDate());
            stmt.setInt(7, supplier.getSupplierId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteSupplier(int id) {
        String sql = "DELETE FROM suppliers WHERE supplier_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }

    public ArrayList<Supplier> getAllSuppliers() {
        ArrayList<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM suppliers ORDER BY supplier_id";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Supplier s = new Supplier();
                s.setSupplierId(rs.getInt("supplier_id"));
                s.setSupplierName(rs.getString("supplier_name"));
                s.setContactPerson(rs.getString("contact_person"));
                s.setContactNumber(rs.getString("contact_number"));
                s.setEmail(rs.getString("email"));
                s.setAddress(rs.getString("address"));
                s.setLastDeliveryDate(rs.getDate("last_delivery_date"));
                suppliers.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return suppliers;
    }
}
