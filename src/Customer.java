public class Customer {
    // Variables
    private int customer_id;
    private String first_name;
    private String last_name;
    private String contact_number;
    private String email;
    private int country_id;

    public Customer() {}

    public Customer(int customer_id, String first_name, String last_name, String contact_number, String email, int country_id) {
        this.customer_id = customer_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.contact_number = contact_number;
        this.email = email;
        this.country_id = country_id;
    }

    public int getCustomerId() {
        return customer_id;
    }
    public void setCustomerId(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getFirstName() {
        return first_name;
    }
    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }
    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getContactNumber() {
        return contact_number;
    }
    public void setContactNumber(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public int getCountryId() {
        return country_id;
    }
    public void setCountryId(int country_id) {
        this.country_id = country_id;
    }
}
