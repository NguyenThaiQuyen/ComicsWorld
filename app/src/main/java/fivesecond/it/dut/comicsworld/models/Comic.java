package fivesecond.it.dut.comicsworld.models;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class Comic implements Serializable {
    private int id;
    private String name;
    private int idType;
    private String thumbnail;
    private int chaps;
    private String description;
    private int view;
    private float rating;
    private int idComment;
    private String author;
    private String id_url;

    public Comic(int id, String name, int idType, String thumbnail, int chaps, String description, int view, float rating, int idComment, String author, String id_url) {
        this.id = id;
        this.name = name;
        this.idType = idType;
        this.thumbnail = thumbnail;
        this.chaps = chaps;
        this.description = description;
        this.view = view;
        this.rating = rating;
        this.idComment = idComment;
        this.author = author;
        this.id_url = id_url;
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

    public float getrating() {
        return rating;
    }

    public void setrating(float rating) {
        this.rating = rating;
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

    public String getIdUrl() {
        return id_url;
    }

    public void setIdUrl(String id_url) {
        this.id_url = id_url;
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
                ", rating=" + rating +
                ", idComment=" + idComment +
                ", author='" + author + '\'' +
                '}';
    }
}
