/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.EspecialidadBean;
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
 * @author TOSHIBA
 */
public class EspecialidadDao {

    public List consultaEspecialidad() {
        List lista = new ArrayList();

        Connection conn;
        try {
            conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT especialidad_id, especialidad.nombre, especialidad.carrera_id, carrera.nombre AS carrera FROM especialidad INNER JOIN carrera ON especialidad.carrera_id = carrera.carrera_id;");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                EspecialidadBean B = new EspecialidadBean();
                B.setEspecialidad_id(rs.getInt("especialidad_id"));
                B.setNombre(rs.getString("nombre"));
                B.setCarrera_id(rs.getInt("carrera_id"));
                B.setCarrera(rs.getString("carrera"));


                lista.add(B);
            }
            conn.close();
            ps.close();
            rs.close();


        } catch (SQLException ex) {
            Logger.getLogger(EspecialidadDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;

    }
}
