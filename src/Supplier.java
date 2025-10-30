import java.sql.Date;

public class Supplier {
    // Variables
    private int supplier_id;
    private String supplier_name;
    private String contact_person;
    private String contact_number;
    private String email;
    private String address;
    private Date last_delivery_date;

    // Constructors
    public Supplier() {}

    public Supplier(int supplier_id, String supplier_name, String contact_person, String contact_number, String email, String address, Date last_delivery_date) {
        this.supplier_id = supplier_id;
        this.supplier_name = supplier_name;
        this.contact_person = contact_person;
        this.contact_number = contact_number;
        this.email = email;
        this.address = address;
        this.last_delivery_date = last_delivery_date;
    }

    // Supplier ID
    public int getSupplierId() {
        return supplier_id;
    }
    public void setSupplierId(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    // Supplier Name
    public String getSupplierName() {
        return supplier_name;
    }
    public void setSupplierName(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    // Contact Person
    public String getContactPerson() {
        return contact_person;
    }
    public void setContactPerson(String contact_person) {
        this.contact_person = contact_person;
    }

    // Contact Number
    public String getContactNumber() {
        return contact_number;
    }
    public void setContactNumber(String contact_number) {
        this.contact_number = contact_number;
    }

    // Email
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    // Address
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    // Last Delivery Date
    public Date getLastDeliveryDate() {
        return last_delivery_date;
    }
    public void setLastDeliveryDate(Date last_delivery_date) {
        this.last_delivery_date = last_delivery_date;
    }
}
