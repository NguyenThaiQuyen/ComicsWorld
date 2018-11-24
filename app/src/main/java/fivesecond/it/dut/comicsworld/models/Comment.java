package fivesecond.it.dut.comicsworld.models;

public class Comment {
    private String id;
    private String content;
    private String idComic;
    private String idUser;

    public Comment(){

    }

    public Comment(String id, String content, String idComic, String idUser) {
        this.id = id;
        this.content = content;
        this.idComic = idComic;
        this.idUser = idUser;
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

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", idComic='" + idComic + '\'' +
                ", idUser='" + idUser + '\'' +
                '}';
    }
}
