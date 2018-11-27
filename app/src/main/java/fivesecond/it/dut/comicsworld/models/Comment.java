package fivesecond.it.dut.comicsworld.models;

public class Comment {
    private String id;
    private String content;
    private String idComic;
    private String idUser;
    private String userName;

    public Comment(){

    }

    public Comment(String id, String content, String idComic, String idUser, String userName) {
        this.id = id;
        this.content = content;
        this.idComic = idComic;
        this.idUser = idUser;
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIdComic() {
        return idComic;
    }

    public void setIdComic(String idComic) {
        this.idComic = idComic;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", idComic='" + idComic + '\'' +
                ", idUser='" + idUser + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
