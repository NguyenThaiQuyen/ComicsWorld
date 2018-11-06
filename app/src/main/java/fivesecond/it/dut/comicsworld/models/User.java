package fivesecond.it.dut.comicsworld.models;

public class User {
    private int id;
    private String name;
    private String password;
    private String email;
    private int idSave;
    private int idLove;

    public User(int id, String name, String password, String email, int idSave, int idLove) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.idSave = idSave;
        this.idLove = idLove;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdSave() {
        return idSave;
    }

    public void setIdSave(int idSave) {
        this.idSave = idSave;
    }

    public int getIdLove() {
        return idLove;
    }

    public void setIdLove(int idLove) {
        this.idLove = idLove;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", idSave=" + idSave +
                ", idLove=" + idLove +
                '}';
    }
}
