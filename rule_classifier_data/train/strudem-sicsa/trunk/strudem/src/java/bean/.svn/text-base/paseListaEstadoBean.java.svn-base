/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.opensymphony.xwork2.ActionSupport;
import dao.paseListaAsitenciaDao;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 *
 * @author seter
 */
public class paseListaEstadoBean extends ActionSupport implements ServletRequestAware {

    private HttpServletRequest request;

    @Override
    @SuppressWarnings("empty-statement")
    public String execute() throws SQLException {
        paseListaAsitenciaDao unidad = new paseListaAsitenciaDao();
        String matriculas[] = request.getParameterValues("alumnomatricula");
        String s[] = request.getParameterValues("estado");
        int i = 0;
        do {
            request.getParameterValues("alumnomatricula");
            request.getParameterValues("estado");
            // JOptionPane.showMessageDialog(null, matriculas[i]+"alumno***- -***asistencia"+s[i]);
            unidad.insercion_asistencia_aalumno(Integer.parseInt(matriculas[i]), Integer.parseInt(s[i]));
            i++;
        } while (i < matriculas.length);
        addActionMessage("La asistencia de los alumnos fue registrada correctamente, vaya al menú Asistencias para ver los cambios reflejados");
        return SUCCESS;
    }

    @Override
    public void setServletRequest(HttpServletRequest hsr) {
        request = hsr;
    }
}
