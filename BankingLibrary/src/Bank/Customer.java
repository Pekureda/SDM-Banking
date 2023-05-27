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

    public String getUsername() {
        return username;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
}
