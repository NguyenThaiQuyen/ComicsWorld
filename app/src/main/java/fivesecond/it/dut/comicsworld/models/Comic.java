package fivesecond.it.dut.comicsworld.models;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class Comic implements Serializable {

    private String author;
    private int chap;
    private String desc;
    private int id;
    private int idComment;
    private String idType;
    private String name;
    private int numberRating;
    private float rating;
    private String thumb;
    private String url;

    public Comic(){

    }

    public Comic(String author, int chap, String desc, int id, int idComment, String idType, String name, int numberRating, float rating, String thumb, String url) {
        this.author = author;
        this.chap = chap;
        this.desc = desc;
        this.id = id;
        this.idComment = idComment;
        this.idType = idType;
        this.name = name;
        this.numberRating = numberRating;
        this.rating = rating;
        this.thumb = thumb;
        this.url = url;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getNumberRating() {
        return numberRating;
    }

    public void setNumberRating(int numberRating) {
        this.numberRating = numberRating;
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

    @Override
    public String toString() {
        return "Comic{" +
                "author='" + author + '\'' +
                ", chap=" + chap +
                ", desc='" + desc + '\'' +
                ", id=" + id +
                ", idComment=" + idComment +
                ", idType='" + idType + '\'' +
                ", name='" + name + '\'' +
                ", numberRating=" + numberRating +
                ", rating=" + rating +
                ", thumb='" + thumb + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}