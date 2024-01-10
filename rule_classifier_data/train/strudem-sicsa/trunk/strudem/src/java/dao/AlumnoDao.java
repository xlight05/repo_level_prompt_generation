package dao;

import bean.AlumnoBean;
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

public class AlumnoDao {

    public boolean insertar(String nombre, String apaterno, String amaterno) throws SQLException {
        boolean insertOK = false;
        Connection conn = ConexionSQL.getConnection();
        JOptionPane.showMessageDialog(null, nombre);
        // PreparedStatement ps = conn.prepareStatement("insert into datoUsuario ( nombre, apaterno,amaterno) values(?,?,?)");
        // String sql = "update empleado set nombre=?, apellido=?, puesto=?  where id_e=?";
        PreparedStatement ps = conn.prepareStatement("insert into datoUsuario ( nombre, apaterno,amaterno) values(?,?,?)");
        ps.setString(1, nombre);
        ps.setString(2, apaterno);
        ps.setString(3, amaterno);
        // ps.setString(4, especialidad);
        if (ps.executeUpdate() == 1) {
            insertOK = true;
        }
        ps.close();
        conn.close();
        return insertOK;
    }

    public List consultarTodo() {
        List listaalumno = new ArrayList();
        try {
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("select id_ususario,nombre,apaterno,amaterno from datoUsuario ");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("ntrando");
                AlumnoBean B = new AlumnoBean();
                B.setId_ususario(rs.getInt("id_ususario"));
                B.setNombre(rs.getString("nombre"));
                B.setApaterno(rs.getString("apaterno"));
                B.setAmaterno(rs.getString("amaterno"));

                listaalumno.add(B);
            }
            conn.close();
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaalumno;

    }

    public boolean modificar(int clave, String nombre, String apaterno, String amaterno) {
        boolean e = false;
        try {
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("update datoUsuario set nombre=?,apaterno=?,amaterno=? where id_ususario=?");
            ps.setString(1, nombre);
            ps.setString(2, apaterno);
            ps.setString(3, amaterno);
            ps.setInt(4, clave);
            //ps.setString(4, especialidad);
            if (ps.executeUpdate() == 1) {
                e = true;
            }
            conn.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return e;

    }

    public List consultaPorId(int clave) {
        List lista = new ArrayList();
        AlumnoBean B = new AlumnoBean();
        try {
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("select id_ususario,nombre,apaterno,amaterno from datoUsuario where id_ususario=?");
            ps.setInt(1, clave);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                B.setId_ususario(clave);
                B.setNombre(rs.getString("nombre"));
                B.setApaterno(rs.getString("apaterno"));
                B.setAmaterno(rs.getString("amaterno"));
                lista.add(B);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
}
