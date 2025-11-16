
/**
 * Represents a Customer in the Souvenir Shop Management System.
 * 
 * <p> This class stores basic information about a customer, such
 * as their ID, first name, last name, contact number, and email. </p>
 * 
 * <p> The Customer object is typically used with CustomerDAO to
 * interact with the database. </p>
 */

public class Customer {
    // Variables (private fields to store customer data)
    private int customer_id;
    private String first_name;
    private String last_name;
    private String contact_number;
    private String email; 

    /**
     * Default constructor.
     * 
     * <p> Creates an empty Customer object. Fields can be set later
     * using setter methods. </p>
     */
    public Customer() {}

    /**
     * Parameterized constructor.
     * 
     * <p> Creates a Customer object with all basic information. </p>
     * @param first_name        The customer's first name
     * @param last_name         The customer's last name
     * @param contact_number    The customer's contact number (can be null)
     * @param email             The customer's email address (can be null)
     */
    public Customer(String first_name, String last_name, String contact_number, String email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.contact_number = contact_number;
        this.email = email;
    }

    /**
     * Gets the customer's unique ID.
     * @return The customer ID
     */
    public int getCustomerId() {
        return customer_id;
    }

    /**
     * Sets the customer's unique ID.
     * @param customer_id The ID to set
     */
    public void setCustomerId(int customer_id) {
        this.customer_id = customer_id;
    }

    /**
     * Gets the customer's first name.
     * @return The first name
     */
    public String getFirstName() {
        return first_name;
    }

    /**
     * Sets the customer's first name.
     * @param first_name The first name to set
     */
    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    /**
     * Gets the customer's last name.
     * @return The last name
     */
    public String getLastName() {
        return last_name;
    }

    /**
     * Sets the customer's last name.
     * @param last_name The last name to set
     */
    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    /**
     * Gets the customer's contact number.
     * @return The contact number
     */
    public String getContactNumber() {
        return contact_number;
    }

    /**
     * Sets the customer's contact number.
     * @param contact_number The contact number to set
     */
    public void setContactNumber(String contact_number) {
        this.contact_number = contact_number;
    }

    /**
     * Gets the customer's email address.
     * @return The email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the customer's email address.
     * @param email The email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
