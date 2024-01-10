package br.uniceub.prj.dao;

import java.sql.*;

/**
 *
 * @author Hiragi
 *
 * Sera' a base (extends) para qualquer DAO, ja' tera disponível a conexão
 * com o banco de dados MySQL.
 *
 */

public abstract class BaseDAOMySQL {

    /**
     * Variável que representa a conexão disponibilizada aos DAOs que são
     * herdeiras desta classe.
     */
    protected Connection cnn = null;
    protected String SQL ="";

    /** Creates a new instance of Database */
    public BaseDAOMySQL() throws Exception {
        cnn=ConexaoMySQL.getInstance();
    }

}
