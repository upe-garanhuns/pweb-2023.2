package br.upe.garanhus.esw.pweb.modelo;

import java.util.List;
import java.util.Map;
import jakarta.json.bind.annotation.JsonbProperty;

public class RespostaListaPersonagensTO {

  private int quantidade;

  @JsonbProperty("results")
  private List<PersonagemTO> personagens;

  @JsonbProperty("info")
  public void setInfo(Map<String, String> info) {
    this.quantidade = Integer.parseInt(info.get("count")); // mudança de .valueOf.intValue para
                                                           // .parseInt
  }

  public List<PersonagemTO> getPersonagens() {
    return personagens;
  }

  public void setPersonagens(List<PersonagemTO> personagens) {
    this.personagens = personagens;
  }

  public int getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(int quantidade) {
    this.quantidade = quantidade;
  }



}
