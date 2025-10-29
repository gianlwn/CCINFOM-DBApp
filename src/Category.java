public class Category {
    // Variables
    private int category_id;
    private String category_name;

    // Constructors
    public Category() {}

    public Category(int category_id, String category_name) {
        this.category_id = category_id;
        this.category_name = category_name;
    }

    public int getCategoryId() {
        return category_id;
    }
    public void setCategoryId(int category_id) {
        this.category_id = category_id;
    }

    public String getCategoryName() {
        return category_name;
    }
    public void setCategoryName(String category_name) {
        this.category_name = category_name;
    }
}
