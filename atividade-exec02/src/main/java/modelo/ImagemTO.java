package modelo;

public class ImagemTO {

  private String id;
  private String author;
  private String width;
  private String height;
  private String url;
  private String download_url;

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
