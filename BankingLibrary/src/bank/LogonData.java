package bank;

public class LogonData {
    public LogonData(String username, String password) {
        this.username = username;
        this.password = password;
    }
    private final String username;
    private final String password;

    public boolean isValid() {
        return this.username.length() > 0 && !this.username.matches("\s") && this.password.length() > 6 && !this.password.matches("\s");
    }
}