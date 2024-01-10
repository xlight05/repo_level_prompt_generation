/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.UbicacionBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilerias.ConexionSQL;

/**
 *
 * @author karlo0s
 */
public class UbicacionDao {

    public boolean insertar(int edificio_id, int aula_id) throws SQLException {
        boolean insertOK = false;

        Connection conn = ConexionSQL.getConnection();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO ubicacion (edificio_id, aula_id) values(?,?)");
        ps.setInt(1, edificio_id);
        ps.setInt(2, aula_id);

        if (ps.executeUpdate() == 1) {
            insertOK = true;
        }
        ps.close();
        conn.close();
        return insertOK;
    }

    public List consultarTodo() {
        List listaubicacion = new ArrayList();
        try {
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT ubicacion_id, ubicacion.edificio_id, ubicacion.aula_id, edificio.descripcion AS edificio, aula.descripcion AS aula FROM ubicacion INNER JOIN edificio ON ubicacion.edificio_id = edificio.edificio_id INNER JOIN aula ON ubicacion.aula_id = aula.aula_id;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("Recibiendo datos de ubicacion...");
                UbicacionBean ubicacion = new UbicacionBean();
                ubicacion.setUbicacion_id(rs.getInt("ubicacion_id"));
                ubicacion.setEdificio_id(rs.getInt("edificio_id"));
                ubicacion.setAula_id(rs.getInt("aula_id"));
                ubicacion.setAula(rs.getString("aula"));
                ubicacion.setEdificio(rs.getString("edificio"));

                listaubicacion.add(ubicacion);
            }
            conn.close();
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(UbicacionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaubicacion;

    }

    public boolean modificar(int ubicacion_id, int edificio_id, int aula_id) {
        boolean e = false;
        try {
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE ubicacion set edificio_id=?, aula_id=? where ubicacion_id = ?");
            ps.setInt(1, edificio_id);
            ps.setInt(2, aula_id);


            if (ps.executeUpdate() == 1) {
                e = true;
            }
            conn.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(UbicacionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return e;

    }

    public List consultaPorId(int clave) {
        List lista = new ArrayList();
        UbicacionBean ubicacion = new UbicacionBean();
        try {
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT ubicacion_id,  edificio_id, aula_id from ubicacion where id_ubicacion=?");
            ps.setInt(1, clave);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ubicacion.setUbicacion_id(rs.getInt("ubicacion_id"));
                ubicacion.setEdificio_id(rs.getInt("edificio_id"));
                ubicacion.setAula_id(rs.getInt("aula_id"));


                lista.add(ubicacion);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
}
