/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.ProfesorBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import utilerias.ConexionSQL;

/**
 *
 * @author karlo0s
 */
public class ProfesorDao {

    public boolean insertar(String nombre, String a_paterno, String a_materno, int usuario_id, int edad, int telefono, String direccion) throws SQLException {
        boolean insertOK = false;

        Connection conn = ConexionSQL.getConnection();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO profesor ( nombre, a_paterno, a_materno, usuario_id, edad, telefono, direccion) values(?,?,?,?,?,?,?)");
        ps.setString(1, nombre);
        ps.setString(2, a_paterno);
        ps.setString(3, a_materno);
        ps.setInt(4, usuario_id);
        ps.setInt(5, edad);
        ps.setInt(6, telefono);
        ps.setString(7, direccion);


        if (ps.executeUpdate() == 1) {
            insertOK = true;
        }
        ps.close();
        conn.close();
        return insertOK;
    }

    public List consultarTodo() {
        List listaprofesor = new ArrayList();
        try {
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT profesor_id, nombre, a_paterno, a_materno, usuario_id, edad, telefono, direccion from profesor ");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("Recibiendo datos de profesor...");
                ProfesorBean profesor = new ProfesorBean();
                profesor.setProfesor_id(rs.getInt("profesor_id"));
                profesor.setNombre(rs.getString("nombre"));
                profesor.setA_paterno(rs.getString("a_paterno"));
                profesor.setA_materno(rs.getString("a_materno"));
                profesor.setUsuario_id(rs.getInt("usuario_id"));
                profesor.setTelefono(rs.getInt("telefono"));
                profesor.setDireccion(rs.getString("direccion"));
                profesor.setEdad(rs.getInt("edad"));

                listaprofesor.add(profesor);
            }
            conn.close();
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProfesorDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaprofesor;

    }

    public boolean modificar(int profesor_id, String nombre, String a_paterno, String a_materno, int usuario_id, int edad, int telefono, String direccion) {
        boolean e = false;
        try {
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE profesor set nombre=?,a_paterno=?,a_materno=?, usuario_id = ?, edad = ?, telefono = ?, direccion = ? where profesor_id = ?");
            ps.setString(1, nombre);
            ps.setString(2, a_paterno);
            ps.setString(3, a_materno);
            ps.setInt(4, usuario_id);
            ps.setInt(5, edad);
            ps.setInt(6, telefono);
            ps.setString(7, direccion);
            
            if (ps.executeUpdate() == 1) {
                e = true;
            }
            conn.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(ProfesorDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return e;

    }

    public List consultaPorId(int clave) {
        List lista = new ArrayList();
        ProfesorBean profesor = new ProfesorBean();
        try {
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT profesor_id, nombre, a_paterno, a_materno, usuario_id, edad, telefono, direccion from profesor where id_profesor=?");
            ps.setInt(1, clave);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                profesor.setProfesor_id(rs.getInt("profesor_id"));
                profesor.setNombre(rs.getString("nombre"));
                profesor.setA_paterno(rs.getString("a_paterno"));
                profesor.setA_materno(rs.getString("a_materno"));
                profesor.setUsuario_id(rs.getInt("usuario_id"));
                profesor.setTelefono(rs.getInt("telefono"));
                profesor.setDireccion(rs.getString("direccion"));
                profesor.setEdad(rs.getInt("edad"));

                lista.add(profesor);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
}
