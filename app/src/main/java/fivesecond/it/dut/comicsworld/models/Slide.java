package fivesecond.it.dut.comicsworld.models;

public class Slide {
    String id;
    String url;

    public Slide(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public Slide() {

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

    @Override
    public String toString() {
        return "Slide{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
