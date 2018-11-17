package fivesecond.it.dut.comicsworld.models;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class Comic implements Serializable {

    private String id;
    private String author;
    private int chap;
    private String desc;
    private int idComment;
    private String idType;
    private String name;
    private float rating;
    private String thumb;
    private String url;
    private int numberRating;

    public Comic(){

    }

    public Comic(String id, String author, int chap, String desc, int idComment, String idType, String name, float rating, String thumb, String url, int numberRating) {
        this.id = id;
        this.author = author;
        this.chap = chap;
        this.desc = desc;
        this.idComment = idComment;
        this.idType = idType;
        this.name = name;
        this.rating = rating;
        this.thumb = thumb;
        this.url = url;
        this.numberRating = numberRating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getChap() {
        return chap;
    }

    public void setChap(int chap) {
        this.chap = chap;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNumberRating() {
        return numberRating;
    }

    public void setNumberRating(int numberRating) {
        this.numberRating = numberRating;
    }

    @Override
    public String toString() {
        return "Comic{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", chap=" + chap +
                ", desc='" + desc + '\'' +
                ", idComment=" + idComment +
                ", idType='" + idType + '\'' +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                ", thumb='" + thumb + '\'' +
                ", url='" + url + '\'' +
                ", numberRating=" + numberRating +
                '}';
    }
}
