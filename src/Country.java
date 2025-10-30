public class Country {
    // Variables
    private int country_id;
    private String country_name;

    // Constructors
    public Country() {}

    public Country(int country_id, String country_name) {
        this.country_id = country_id;
        this.country_name = country_name;
    }

    // Country ID
    public int getCountryId() {
        return country_id;
    }
    public void setCountryId(int country_id) {
        this.country_id = country_id;
    }

    // Country Name
    public String getCountryName() {
        return country_name;
    }
    public void setCountryName(String country_name) {
        this.country_name = country_name;
    }
}
