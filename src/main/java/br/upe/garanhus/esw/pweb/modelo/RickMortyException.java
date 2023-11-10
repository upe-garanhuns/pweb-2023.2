package br.upe.garanhus.esw.pweb.modelo;

public class RickMortyException extends RuntimeException {

  private static final long serialVersionUID = 8674906927966954109L;

  public RickMortyException(String mensagem) {
    super(mensagem);
  }

  public RickMortyException(String mensagem, Exception origem) {
    super(mensagem, origem);
  }

}
