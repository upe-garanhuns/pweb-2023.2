package br.upe.garanhus.esw.pweb.model.domain.cat;

import br.upe.garanhus.esw.pweb.model.dto.CatDTO;

public class Cat {

  private final String id;
  private final String url;
  private final int width;
  private final int height;

  public Cat(CatDTO data) {
    this.id = data.id();
    this.url = data.url();
    this.width = data.width();
    this.height = data.height();
  }

  public String getId() {
    return id;
  }

  public String getUrl() {
    return url;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}
