package br.upe.garanhus.esw.pweb.modelo;

import javax.json.bind.annotation.JsonbProperty;

public class PicsumObj {
  @JsonbProperty("id")
  int id;
  @JsonbProperty("author")
  String author;
  @JsonbProperty("width")
  int width;
  @JsonbProperty("height")
  int height;
  @JsonbProperty("url")
  String url;
  @JsonbProperty("download_url")
  String download_url;
  
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getAuthor() {
    return author;
  }
  public void setAuthor(String author) {
    this.author = author;
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
  public String getUrl() {
    return url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
  public String getDownload_url() {
    return download_url;
  }
  public void setDownload_url(String download_url) {
    this.download_url = download_url;
  }
}
