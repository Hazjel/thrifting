package models.user;

public class User {
    private int id;
    private String name; // Menambahkan atribut name
    private String username;
    private String email;
    private String password;
    private String roleType; // "customer" or "seller"

    public User() {
    }

    public User(int id, String name, String username, String email, String password, String roleType) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roleType = roleType;
    }

    // Konstruktor tanpa name untuk kompatibilitas dengan kode lama
    public User(int id, String username, String email, String password, String roleType) {
        this.id = id;
        this.name = username; // Gunakan username sebagai name jika tidak disediakan
        this.username = username;
        this.email = email;
        this.password = password;
        this.roleType = roleType;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Alias untuk setPassword() dengan nama setPasswords() untuk menyesuaikan dengan kode di UserDAO
    public void setPasswords(String password) {
        this.password = password;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
}
