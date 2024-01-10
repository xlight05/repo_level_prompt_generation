/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import utilerias.ConexionSQL;



/**
 *
 * @author Alumno
 */
public class paseListaAsitenciaDao {
   static int a,b;
    public void consultaunidad(int lista_asistencia,int unidad) throws SQLException{
       a=lista_asistencia;
      // b=unidad;
      // JOptionPane.showMessageDialog(null,a);
    }
    public boolean insercion_asistencia_aalumno(int historial,int estado) throws SQLException{
  //  JOptionPane.showMessageDialog(null,a);
    Connection conn=ConexionSQL.getConnection();
    PreparedStatement ps=conn.prepareStatement("insert into asistencia" +
            " (lista_asistencia_id,fecha,estado_id,historial_alumno_id,unidad_id)" +
            "values(?,'2012-03-03',?,?,1)" );
    ps.setInt(1, a);
    ps.setInt(2, estado);
    ps.setInt(3, historial);
    if(ps.executeUpdate()==1)
    return true;
    else
        return false;
    }
}
