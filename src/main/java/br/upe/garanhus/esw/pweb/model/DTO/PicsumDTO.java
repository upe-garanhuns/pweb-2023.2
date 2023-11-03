package br.upe.garanhus.esw.pweb.model.DTO;

public class PicsumDTO {
	
	private String id;
	private String author;
	private int width;
	private int height;
	private String url;
	private String download_url;
	
	public PicsumDTO(String id, String author, int width, int height, String url, String download_url) {
		super();
		this.id = id;
		this.author = author;
		this.width = width;
		this.height = height;
		this.url = url;
		this.download_url = download_url;
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

	public String getDownload_url() {
		return download_url;
	}

	public void setDownload_url(String download_url) {
		this.download_url = download_url;
	}
	
}
