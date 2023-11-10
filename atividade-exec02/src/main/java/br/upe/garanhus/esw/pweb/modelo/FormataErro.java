package br.upe.garanhus.esw.pweb.modelo;

public class FormataErro {

  private FormataErro() {}


  public static String paraJson(int status, String msg, String teste) {
    return "{\"codigo\":" + status + ", \"mensagem\":\"" + msg + "\", \"detalhe\":\"" + teste
        + "\"}";
  }

}
