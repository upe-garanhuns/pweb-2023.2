package br.upe.garanhus.esw.pweb.modelo;

import jakarta.json.bind.annotation.JsonbProperty;

public class IdDTO {

    @JsonbProperty("id")
    private String id;
    
    public IdDTO() {

    }
    
    public IdDTO(String id) {
      this.id = id;
    }

    public String getId() {
      return id;
    }
    
    public void setId(String id) {
      this.id = id;
    }
  
}
