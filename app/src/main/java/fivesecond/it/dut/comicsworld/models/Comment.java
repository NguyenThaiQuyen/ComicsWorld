package fivesecond.it.dut.comicsworld.models;

public class Comment {
    private int id;
    private String content;
    private int idUser;
    private int idComment;

    public Comment(int id, String content, int idUser, int idComment) {
        this.id = id;
        this.content = content;
        this.idUser = idUser;
        this.idComment = idComment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", idUser=" + idUser +
                ", idComment=" + idComment +
                '}';
    }
}
