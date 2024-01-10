/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.opensymphony.xwork2.ActionSupport;
import dao.PeriodoDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author karlo0s
 */
public class PeriodoBean extends ActionSupport {
    private int periodo_id;
    private String descripcion;
    private String fecha_ini;
    private String fecha_vig;
    private List lista = new ArrayList();

    /**
     * @return the periodo_id
     */
    public int getPeriodo_id() {
        return periodo_id;
    }

    /**
     * @param periodo_id the periodo_id to set
     */
    public void setPeriodo_id(int periodo_id) {
        this.periodo_id = periodo_id;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the fecha_inicio
     */
    public String getFecha_ini() {
        return fecha_ini;
    }

    /**
     * @param fecha_inicio the fecha_inicio to set
     */
    public void setFecha_ini(String fecha_ini) {
        this.fecha_ini = fecha_ini;
    }

    /**
     * @return the fecha_vigente
     */
    public String getFecha_vig() {
        return fecha_vig;
    }

    /**
     * @param fecha_vigente the fecha_vigente to set
     */
    public void setFecha_vig(String fecha_vig) {
        this.fecha_vig = fecha_vig;
    }

    /**
     * @return the lista
     */
    public List getLista() {
        return lista;
    }

    /**
     * @param lista the lista to set
     */
    public void setLista(List lista) {
        this.lista = lista;
    }
    
      public String insertar() throws SQLException {
        PeriodoDao dao = new PeriodoDao();
        boolean insertOK;

        insertOK = dao.insertar(getDescripcion(),getFecha_ini(),getFecha_vig());
        if (insertOK) {
            addActionMessage("Se ha insertado un nuevo registro a la lista de periodo");
            return SUCCESS;
        } else {
            addActionError("No se pudo insertar el registro, corrigarlo e intente de nuevo");
            return ERROR;
        }
    }

    public String consultas() {

        PeriodoDao dao = new PeriodoDao();
        setLista(dao.consultarTodo());

        if (!getLista().isEmpty()) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    public String modificarRegistro() {
        boolean esCorrecto;
        PeriodoDao al = new PeriodoDao();
        esCorrecto = al.modificar(getPeriodo_id(),getDescripcion(),getFecha_ini(),getFecha_vig());

        if (esCorrecto) {
            consultaPeriodo();
            //JOptionPane.showMessageDialog(null, getPeriodo_id());
            addActionMessage("Se ha modificado el registro " + getPeriodo_id() + " satisfactoriamente");
            return SUCCESS;

        } else {
            addActionError("Ocurrió un error al intentar modificar el registro " + getPeriodo_id() + ", verifiquelo e intente de nuevo");
            return ERROR;
        }
    }

    public String consultaPeriodo() {
        PeriodoDao l = new PeriodoDao();
        setLista(l.consultaPorId(getPeriodo_id()));
        System.out.println("la lista tiene: " + lista.size());

        if (getPeriodo_id() != 0) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }
}
