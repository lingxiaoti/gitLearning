package gitTest;

public class Guo {

    private String username;
    private Long number;
    private String password;
    private String email;
    private String asd;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Guo{" +
                "username='" + username + '\'' +
                ", number=" + number +
                ", password='" + password + '\'' +
                '}';
    }
}
