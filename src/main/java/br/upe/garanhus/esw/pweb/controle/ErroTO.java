package br.upe.garanhus.esw.pweb.controle;

public class ErroTO {
  private String erro;
  private String codigo;
  private String detalhe;

  public ErroTO() {}

  public ErroTO(int codigo, Exception erro) {
    this();
    this.erro = erro.getMessage();
    this.codigo = String.valueOf(codigo);
    this.detalhe = erro.getClass().getName();
  }

  public String getErro() {
    return erro;
  }

  public void setErro(String erro) {
    this.erro = erro;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public String getDetalhe() {
    return detalhe;
  }

  public void setDetalhe(String detalhe) {
    this.detalhe = detalhe;
  }


}
