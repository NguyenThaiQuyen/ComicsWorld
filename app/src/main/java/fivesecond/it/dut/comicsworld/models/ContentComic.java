package fivesecond.it.dut.comicsworld.models;

public class ContentComic {
    String id;
    String url;

    public ContentComic() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ContentComic(String id, String url) {
        this.id = id;
        this.url = url;
    }

    @Override
    public String toString() {
        return "ContentComic{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
