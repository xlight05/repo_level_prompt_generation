/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * Integer.parseInt(s[i])
 */


package bean;

import com.opensymphony.xwork2.ActionSupport;
import dao.ListadoDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.swing.JOptionPane;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 *
 * @author seter
 */
public class EstadoListaAlumnoBean extends ActionSupport implements ServletRequestAware  {
 private HttpServletRequest request;
    public int getE_id() {
        return e_id;
    }

    public void setE_id(int e_id) {
        this.e_id = e_id;
    }

    public String getEstados() {
        return estados;
    }

    public void setEstados(String estados) {
        this.estados = estados;
    }

    public int getId_ususario() {
        return id_ususario;
    }

    public void setId_ususario(int id_ususario) {
        this.id_ususario = id_ususario;
    }

    public int getL_id() {
        return l_id;
    }

    public void setL_id(int l_id) {
        this.l_id = l_id;
    }

    public List getRepeusto() {
        return repeusto;
    }

    public void setRepeusto(List repeusto) {

        this.repeusto = repeusto;
    }
    private int e_id;
    private String estados;
    private int l_id = 1;
    private int id_ususario;
    int t;
    private List repeusto = new ArrayList();
    int id_e[] = new int[50];
    String estado[] = new String[50];

    @Override
    @SuppressWarnings("empty-statement")
    public String execute () throws SQLException {
        ListadoDao asistencia =new ListadoDao();
          String matriculas[] = request.getParameterValues("matricula");
         String  s[] = request.getParameterValues("asistncia");
        int i=0;
       do{
		request.getParameterValues("matricula");
                request.getParameterValues("asistncia");
                asistencia.asistencias(Integer.parseInt(matriculas[i]));
            JOptionPane.showMessageDialog(null, matriculas[i]);
            i++;
        }while(i<matriculas.length);
        return SUCCESS;
    }
@Override
    public void setServletRequest(HttpServletRequest hsr) {
        request = hsr;
        
    }
}

