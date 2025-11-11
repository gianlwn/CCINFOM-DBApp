public class Category {
    // Variables
    private int category_id;
    private String category_name;

    // Constructors
    public Category() {}

    public Category(String category_name) {
        this.category_name = category_name;
    }

    // Category ID
    public int getCategoryId() {
        return category_id;
    }
    public void setCategoryId(int category_id) {
        this.category_id = category_id;
    }

    // Category Name
    public String getCategoryName() {
        return category_name;
    }
    public void setCategoryName(String category_name) {
        this.category_name = category_name;
    }
}
