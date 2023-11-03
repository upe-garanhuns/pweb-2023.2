package br.upe.garanhus.esw.pweb.model;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({"id", "url", "width", "height"})
public class DogTO {
    @JsonbProperty("id")
    private String id;

    @JsonbProperty("url")
    private String url;

    @JsonbProperty("width")
    private String width;

    @JsonbProperty("height")
    private String height;

    public DogTO() {}

    public DogTO(String id) {
        this.id = id;
    }

    public DogTO(String id, String url, String width, String height) {
        this.id = id;
        this.url = url;
        this.width = width;
        this.height = height;
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

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "DogTO{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                '}';
    }
}
