package models;

public class Product {
    private int id;
    private String name;
    private double price;
    private String category;
    private String description;
    private String photo; // Ini bisa jadi path, atau base64 string kalau mau embed
    private String status; // Added status field

    // Constructor
    public Product() {
    }

    public Product(int id, String name, double price, String category, String description, String photo) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.photo = photo;
        this.status = "available"; // Default status
    }

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    // Added getter and setter for status
    public String getStatus() {
        return status != null ? status : "available";
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
