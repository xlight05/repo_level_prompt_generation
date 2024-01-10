package dao;

import bean.AulaBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilerias.ConexionSQL;

public class AulaDao {

    public boolean insertar(String descripcion) throws SQLException {
        boolean insertOK = false;

        Connection conn = ConexionSQL.getConnection();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO aula (descripcion) VALUES (?) ");
        ps.setString(1, descripcion);
        
        if (ps.executeUpdate() == 1) {
            insertOK = true;
        }

        ps.close();
        conn.close();
        return insertOK;
    }

    public List consultarTodo() {
        List lista = new ArrayList();
        try {
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT aula_id, descripcion FROM aula");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("Recibiendo datos de aulas...");
                AulaBean B = new AulaBean();
                B.setAula_id(rs.getInt("aula_id"));
                B.setDescripcion(rs.getString("descripcion"));


                lista.add(B);
            }

            conn.close();
            ps.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(AulaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;

    }

    public boolean modificar(int id, String descripcion) {
        boolean autorizado = false;
        try {
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("update aula set descripcion=? where aula_id=?");
            ps.setString(1, descripcion);
            ps.setInt(2, id);


            if (ps.executeUpdate() == 1) {
                autorizado = true;
            }

            conn.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(AulaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return autorizado;

    }

    public List consultaPorId(int clave) {
        List lista = new ArrayList();
        AulaBean B = new AulaBean();

        try {
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT aula_id, descripcion FROM aula WHERE aula_id=?");
            ps.setInt(1, clave);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                B.setAula_id(rs.getInt("aula_id"));
                B.setDescripcion(rs.getString("descripcion"));

                lista.add(B);
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(AulaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
}
