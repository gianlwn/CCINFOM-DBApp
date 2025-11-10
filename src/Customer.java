public class Customer {
    // Variables
    private int customer_id;
    private String first_name;
    private String last_name;
    private String contact_number;
    private String email;

    public Customer() {}

    public Customer(String first_name, String last_name, String contact_number, String email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.contact_number = contact_number;
        this.email = email;
    }

    // Customer ID
    public int getCustomerId() {
        return customer_id;
    }
    public void setCustomerId(int customer_id) {
        this.customer_id = customer_id;
    }

    // First Name
    public String getFirstName() {
        return first_name;
    }
    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    // Last Name
    public String getLastName() {
        return last_name;
    }
    public void setLastName(String last_name) {
        this.last_name = last_name;
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
}
