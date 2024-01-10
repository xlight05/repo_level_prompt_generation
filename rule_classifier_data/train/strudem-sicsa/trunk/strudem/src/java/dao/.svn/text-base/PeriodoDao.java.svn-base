/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.PeriodoBean;
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
public class PeriodoDao {
    public boolean insertar(String descripcion, String fecha_ini, String fecha_vig) throws SQLException {
        boolean insertOK = false;

        Connection conn = ConexionSQL.getConnection();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO periodo ( descripcion, fecha_ini, fecha_vig) values(?,?,?)");
        ps.setString(1, descripcion);
        ps.setString(2, fecha_ini);
        ps.setString(3, fecha_vig);
        
        if (ps.executeUpdate() == 1) {
            insertOK = true;
        }
        ps.close();
        conn.close();
        return insertOK;
    }

    public List consultarTodo() {
        List listaperiodo = new ArrayList();
        try {
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT periodo_id, descripcion, fecha_ini, fecha_vig from periodo ;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("Recibiendo datos de periodo...");
                PeriodoBean periodo = new PeriodoBean();
                periodo.setPeriodo_id(rs.getInt("periodo_id"));
                periodo.setDescripcion(rs.getString("descripcion"));
                periodo.setFecha_ini(rs.getString("fecha_ini"));
                periodo.setFecha_vig(rs.getString("fecha_vig"));


                listaperiodo.add(periodo);
            }
            conn.close();
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(PeriodoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaperiodo;

    }

    public boolean modificar(int periodo_id, String descripcion, String fecha_inicio, String fecha_vigente) {
        boolean e = false;
        try {
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE periodo set descripcion=?, fecha_ini=?, fecha_vig=? where periodo_id = ?");
            ps.setString(1, descripcion);
            ps.setString(2, fecha_inicio);
            ps.setString(3, fecha_vigente);

           
            if (ps.executeUpdate() == 1) {
                e = true;
            }
            conn.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(PeriodoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return e;

    }

    public List consultaPorId(int clave) {
        List lista = new ArrayList();
        PeriodoBean periodo = new PeriodoBean();
        try {
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT periodo_id,  descripcion, fecha_ini, fecha_vig from periodo where id_periodo=?");
            ps.setInt(1, clave);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                periodo.setPeriodo_id(rs.getInt("periodo_id"));
                periodo.setDescripcion(rs.getString("descripcion"));
                periodo.setFecha_ini(rs.getString("fecha_ini"));
                periodo.setFecha_vig(rs.getString("fecha_vig"));


                lista.add(periodo);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
}
