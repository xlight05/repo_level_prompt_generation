package dao;

import bean.LoginBean;
import bean.UsuarioBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilerias.ConexionSQL;

public class UsuarioDao {

    public UsuarioBean accederUsuario(String usuario, String password) throws SQLException {


        Connection conn = ConexionSQL.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT usuario_id, usuario, password, tipo FROM usuario WHERE usuario LIKE ? AND password LIKE ?");
        ps.setString(1, usuario);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        UsuarioBean bean = null;

        while (rs.next()) {
            bean = new UsuarioBean();
            bean.setUsuario_id(rs.getInt("usuario_id"));
            bean.setUsuario(rs.getString("usuario"));
            bean.setPassword(rs.getString("password"));
            bean.setTipo(rs.getString("tipo"));
            
        }
        //Determina si el nombre de usuario y contraseña son correctos y ademas si existe mas de un usuario con el mismo nombre invalida el acceso

        rs.close();
        ps.close();
        conn.close();
        //devuelve -1 si es incorrecto o no existe o el id del usuario
        return bean;
    }

    public LoginBean detallesUsuario(int usuario_id) {
        LoginBean B = null;

        try {
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT usuario.usuario_id, usuario.usuario, usuario.password, profesor.profesor_id, profesor.nombre, profesor.a_paterno, profesor.a_materno, profesor.telefono, profesor.direccion, profesor.edad FROM usuario INNER JOIN profesor ON usuario.usuario_id = profesor.usuario_id  WHERE usuario.usuario_id=? ");
            ps.setInt(1, usuario_id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                B = new LoginBean();
                B.setUsr(rs.getString("usuario"));
                B.setPas(rs.getString("password"));
                B.setNombre(rs.getString("nombre"));
                B.setA_paterno(rs.getString("a_paterno"));
                B.setA_materno(rs.getString("a_materno"));
                B.setDireccion(rs.getString("direccion"));
                B.setEdad(rs.getInt("edad"));
                B.setProfesor_id(rs.getInt("profesor_id"));
                B.setTelefono(rs.getString("telefono"));
               //B.setTipo(rs.getString("tipo"));
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }


        return B;
    }

    public boolean insertar(String usuario, String password, String tipo) throws SQLException {
        boolean insertOK = false;

        Connection conn = ConexionSQL.getConnection();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO usuario (usuario, password, tipo) VALUES (?,?,?) ");
        ps.setString(1, usuario);
        ps.setString(2, password);
        ps.setString(3, tipo);

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
            PreparedStatement ps = conn.prepareStatement("SELECT usuario_id, usuario, password, tipo FROM usuario");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("Recibiendo datos de usuarios...");
                UsuarioBean B = new UsuarioBean();
                B.setUsuario_id(rs.getInt("usuario_id"));
                B.setUsuario(rs.getString("usuario"));
                B.setPassword(rs.getString("password"));
                B.setTipo(rs.getString("tipo"));
                
                lista.add(B);
            }

            conn.close();
            ps.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;

    }

    public boolean modificar(int id, String usuario, String password, String tipo) {
        boolean autorizado = false;
        try {
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("update usuario set usuario=?,password=?,tipo=? where usuario_id=?");
            ps.setString(1, usuario);
            ps.setString(2, password);
            ps.setString(3, tipo);
            ps.setInt(4, id);


            if (ps.executeUpdate() == 1) {
                autorizado = true;
            }

            conn.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return autorizado;

    }

    public List consultaPorId(int clave) {
        List lista = new ArrayList();
        UsuarioBean B = null;

        try {
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT usuario_id, usuario, password, tipo FROM usuario WHERE usuario_id=?");
            ps.setInt(1, clave);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                B = new UsuarioBean();
                B.setUsuario_id(clave);
                B.setUsuario(rs.getString("usuario"));
                B.setPassword(rs.getString("password"));
                B.setTipo(rs.getString("tipo"));

                lista.add(B);
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
}
