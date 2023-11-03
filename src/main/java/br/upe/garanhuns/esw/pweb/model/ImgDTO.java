package br.upe.garanhuns.esw.pweb.model;

public class ImgDTO {
	
	private String id;
	private String author;
	private int width;
	private int height;
	private String url;
	private String downloadUrl;
	
	public ImgDTO(String id, String author, int width, int height, String url, String downloadUrl) {
		super();
		this.id = id;
		this.author = author;
		this.width = width;
		this.height = height;
		this.url = url;
		this.downloadUrl = downloadUrl;
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

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
}
