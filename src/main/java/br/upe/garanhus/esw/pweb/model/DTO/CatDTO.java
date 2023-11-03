package br.upe.garanhus.esw.pweb.model.DTO;

public class CatDTO {

	private String id;
	private String url;
	private int width;
	private int height;
	
	public CatDTO() {}
	
	public CatDTO(String id, String url, int width, int height) {
		super();
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
