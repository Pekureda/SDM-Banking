package Bank;

public class Customer {
    private String username;
    private String name;
    private String surname;
    Customer(String name, String surname, String username) {
        this.name = name;
        this.surname = surname;
        this.username = username;
    }

    String getUsername() {
        return username;
    }
}
