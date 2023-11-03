package br.upe.garanhus.esw.pweb.modelo;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.util.logging.Logger;
import java.util.logging.Level;

public class AplicacaoException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private int codigo;
    private String mensagem;
    private String detalhe;
    private static final Logger logger = Logger.getLogger(AplicacaoException.class.getName());

    public AplicacaoException(int codigo, String mensagem, String detalhe) {
        super(mensagem);
        this.codigo = codigo;
        this.mensagem = mensagem;
        this.detalhe = detalhe;
        logError();
    }

    public int getCodigo() {
        return codigo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getDetalhe() {
        return detalhe;
    }

    public String toJson() {
        try {
            ErrorInfo errorInfo = new ErrorInfo(codigo, mensagem, detalhe);
            Jsonb jsonb = JsonbBuilder.create();
            return jsonb.toJson(errorInfo);
        } catch (Exception e) {
            return "{\"error\": \"Erro ao criar JSON de exceção\"}";
        }
    }

    private void logError() {
        logger.log(Level.SEVERE, "Exceção não tratada: Código " + codigo + ", Mensagem " + mensagem,
                this);
    }

    private static class ErrorInfo {
        private int codigo;
        private String mensagem;
        private String detalhe;

        public ErrorInfo(int codigo, String mensagem, String detalhe) {
            this.codigo = codigo;
            this.mensagem = mensagem;
            this.detalhe = detalhe;
        }
    }
}