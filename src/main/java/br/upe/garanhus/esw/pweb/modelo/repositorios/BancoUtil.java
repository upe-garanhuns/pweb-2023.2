package br.upe.garanhus.esw.pweb.modelo.repositorios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import br.upe.garanhus.esw.pweb.modelo.RickMortyException;

public class BancoUtil {

  static final String DRIVER = "org.postgresql.Driver";

  static final String URL = "jdbc:postgresql://localhost:5432/rickAndMorty";

  static final String USUARIO = "postgres";

  static final String SENHA = "uriel";

  static final Logger logger = Logger.getLogger(BancoUtil.class.getName());

  static final String MSG_ERRO_DRIVER =
      "Ocorreu um erro na especificação do driver do banco de dados";

  static final String MSG_ERRO_SQL = "Ocorreu um erro no processamento da SQL";

  static final String MSG_ERRO_CLOSE =
      "Ocorreu um erro no fechamento dos elementos de conexão com o banco de dados";

  private BancoUtil() {}

  static void especificarDriver() {
    try {
      Class.forName(DRIVER);
    } catch (Exception e) {
      tratarErros(MSG_ERRO_DRIVER, e);
    }
  }

  static void fecharElementos(Connection con, PreparedStatement pst, ResultSet rst) {
    try {
      if (con != null) {
        con.close();
      }
      if (pst != null) {
        pst.close();
      }
      if (rst != null) {
        rst.close();
      }
    } catch (Exception e) {
      tratarErros(MSG_ERRO_CLOSE, e);
    }
  }

  static void tratarErros(String mensagem, Exception e) {
    logger.log(Level.SEVERE, mensagem, e);
    throw new RickMortyException(mensagem, e);
  }

}
