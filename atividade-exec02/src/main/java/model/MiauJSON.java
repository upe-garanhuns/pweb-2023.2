package model;

import jakarta.json.bind.annotation.JsonbProperty;

public class MiauJSON {

  @JsonbProperty("id")
  private String id;
  @JsonbProperty("url")
  private String url;
  @JsonbProperty("width")
  private int width;
  @JsonbProperty("height")
  private int height;

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

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

}
