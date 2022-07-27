package bank;

public class Admin {
    private final String name;
    private Boolean isAdmin;

    public Admin(String name, Boolean isAdmin) {
        this.name = name;
        this.isAdmin = isAdmin;
    }

    public String getName() {
        return name;
    }
}