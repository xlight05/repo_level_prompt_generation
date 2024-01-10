package dao;

import bean.MateriaBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilerias.ConexionSQL;

public class MateriaDao {

    public boolean insertar(String nombre, int cuatrimestre_id, int status) throws SQLException {
        boolean insertOK = false;

        Connection conn = ConexionSQL.getConnection();
        CallableStatement ps = conn.prepareCall("{ call pau_materia_alta (?,?,?)}");
        //PreparedStatement ps = conn.prepareStatement("INSERT INTO materia (nombre,cuatrimestre_id) VALUES (?,?) ");
        ps.setString(1, nombre);
        ps.setInt(2, cuatrimestre_id);
        ps.setInt(3, status);

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
            CallableStatement ps = conn.prepareCall("{call pau_materia_mostrar }");
            //PreparedStatement ps = conn.prepareStatement("SELECT materia_id, nombre,cuatrimestre_id FROM materia");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("Recibiendo datos de materias...");
                MateriaBean B = new MateriaBean();
                B.setMateria_id(rs.getInt("materia_id"));
                B.setNombre(rs.getString("nombre"));
                B.setCuatrimestre_id(rs.getInt("cuatrimestre_id"));
                B.setStatus(rs.getInt("status"));
                if (B.getStatus() == 1) {
                    B.setStatusLetra("Activo");
                } else {
                    B.setStatusLetra("Inactivo");
                }


                lista.add(B);
            }

            conn.close();
            ps.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(MateriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;

    }

    public boolean modificar(int id, String nombre, int cuatrimestre_id) {
        boolean autorizado = true;
        try {
            Connection conn = ConexionSQL.getConnection();
            CallableStatement ps = conn.prepareCall("{call pau_materia_modificar (?,?,?)}");
            //PreparedStatement ps = conn.prepareStatement("update materia set nombre=?,cuatrimestre_id=?  where materia_id=?");
            ps.setInt(1, id);
            ps.setString(2, nombre);
            ps.setInt(3, cuatrimestre_id);

            if (ps.executeUpdate()==1) {
                autorizado = true;
            }

            conn.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(MateriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return autorizado;

    }

    public List consultarCuatrimestre() {
        List lista = new ArrayList();
        try {
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT cuatrimestre_id, nivel FROM cuatrimestre");
            //PreparedStatement ps = conn.prepareStatement("SELECT alumno_id, nombre,a_paterno,a_materno,matricula,carrera_id FROM alumno");
            ResultSet rs = ps.executeQuery();

            List listaCuatrimestre_id = new ArrayList();
            List listaNivel = new ArrayList();
            while (rs.next()) {
                listaCuatrimestre_id.add(rs.getInt("cuatrimestre_id"));
                listaNivel.add(rs.getInt("nivel"));
            }
            lista.add(listaCuatrimestre_id);
            lista.add(listaNivel);

            conn.close();
            ps.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;

    }

    public List consultaPorId(int clave) {
        List lista = new ArrayList();
        MateriaBean B = new MateriaBean();

        try {
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT materia_id, nombre,cuatrimestre_id,status FROM materia WHERE materia_id=?");
            ps.setInt(1, clave);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                B.setMateria_id(rs.getInt("materia_id"));
                B.setNombre(rs.getString("nombre"));
                B.setCuatrimestre_id(rs.getInt("cuatrimestre_id"));
                B.setStatus(rs.getInt("status"));
                if (B.getStatus() == 1) {
                    B.setStatusLetra("Activo");
                } else {
                    B.setStatusLetra("Inactivo");
                }

                lista.add(B);
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(MateriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public void inactivar(int materia_id) {
        try {
            Connection conn = ConexionSQL.getConnection();
            CallableStatement ps = conn.prepareCall("{call pau_act_inact_materia(?) }");
            //PreparedStatement ps = conn.prepareStatement("SELECT alumno_id, nombre,a_paterno,a_materno,matricula,carrera_id FROM alumno");
            ps.setInt(1, materia_id);
            ResultSet rs = ps.executeQuery();

            ps.executeUpdate();



            ps.close();
            conn.close();

            conn.close();
            ps.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return;
    }
}
