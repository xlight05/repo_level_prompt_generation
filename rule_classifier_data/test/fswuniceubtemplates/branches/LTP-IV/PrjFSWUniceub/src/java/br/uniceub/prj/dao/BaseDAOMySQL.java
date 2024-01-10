package br.uniceub.prj.dao;

import java.sql.*;

/**
 *
 * @author Hiragi
 *
 * Sera' a base (extends) para qualquer DAO, ja' tera dispon�vel a conex�o
 * com o banco de dados MySQL.
 *
 */

public abstract class BaseDAOMySQL {

    /**
     * Vari�vel que representa a conex�o disponibilizada aos DAOs que s�o
     * herdeiras desta classe.
     */
    protected Connection cnn = null;
    protected String SQL ="";

    /** Creates a new instance of Database */
    public BaseDAOMySQL() throws Exception {
        cnn=ConexaoMySQL.getInstance();
    }

}
