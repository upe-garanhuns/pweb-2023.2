package br.upe.garanhuns.esw.pweb.modelo;

import br.upe.garanhus.esw.pweb.controle.AplicacaoException;

public class ErrorResponse {

  public String generateJSONResponse(AplicacaoException appException) {
    String errorResponse = "{ \"codigo\": \"" + appException.getCodigo() + "\", \"mensagem\": \""
        + appException.getMensagem() + "\", \"detalhe\": \"" + appException.getDetalhe() + "\" }";

    return errorResponse;
  }
}