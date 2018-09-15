package fivesecond.it.dut.comicsworld.models;

public class Comic {
    private int id;
    private String name;
    private int idType;
    private String thumbnail;
    private int chaps;
    private String description;
    private int view;
    private float rated;
    private int idComment;
    private String author;

    public Comic(int id, String name, int idType, String thumbnail, int chaps, String description, int view, float rated, int idComment, String author) {
        this.id = id;
        this.name = name;
        this.idType = idType;
        this.thumbnail = thumbnail;
        this.chaps = chaps;
        this.description = description;
        this.view = view;
        this.rated = rated;
        this.idComment = idComment;
        this.author = author;
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

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getChaps() {
        return chaps;
    }

    public void setChaps(int chaps) {
        this.chaps = chaps;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public float getRated() {
        return rated;
    }

    public void setRated(float rated) {
        this.rated = rated;
    }

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Comic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", idType=" + idType +
                ", thumbnail='" + thumbnail + '\'' +
                ", chaps=" + chaps +
                ", description='" + description + '\'' +
                ", view=" + view +
                ", rated=" + rated +
                ", idComment=" + idComment +
                ", author='" + author + '\'' +
                '}';
    }
}
