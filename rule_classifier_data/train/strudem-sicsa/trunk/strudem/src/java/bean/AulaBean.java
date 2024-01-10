/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.opensymphony.xwork2.ActionSupport;
import dao.AulaDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mauricio
 */
public class AulaBean extends ActionSupport {

    private int aula_id;
    private String descripcion;
    private List lista = new ArrayList();

    public String cargarPagina() {
        return SUCCESS;
    }

    public String insertar() throws SQLException {
        AulaDao dao = new AulaDao();
        boolean insertOK;

        insertOK = dao.insertar(getDescripcion());
        if (insertOK) {
            addActionMessage("Se ha insertado un nuevo registro a la lista de aulas");
            return SUCCESS;
        } else {
            addActionError("No se pudo insertar el registro, corrigarlo e intente de nuevo");
            return ERROR;
        }
    }

    public String consultas() {

        AulaDao dao = new AulaDao();
        setLista(dao.consultarTodo());

        if (!getLista().isEmpty()) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    public String modificarRegistro() {
        boolean esCorrecto;
        AulaDao al = new AulaDao();
        esCorrecto = al.modificar(getAula_id(), getDescripcion());

        if (esCorrecto) {
            consultaAula();
            //JOptionPane.showMessageDialog(null, getAula_id());
            addActionMessage("Se ha modificado el registro " + getAula_id() + " satisfactoriamente");
            return SUCCESS;

        } else {
            addActionError("Ocurrió un error al intentar modificar el registro " + getAula_id() + ", verifiquelo e intente de nuevo");
            return ERROR;
        }
    }

    public String consultaAula() {
        AulaDao l = new AulaDao();
        setLista(l.consultaPorId(getAula_id()));
        System.out.println("la lista tiene: " + lista.size());

        if (getAula_id() != 0) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    /**
     * @return the aula_id
     */
    public int getAula_id() {
        return aula_id;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param aula_id the aula_id to set
     */
    public void setAula_id(int aula_id) {
        this.aula_id = aula_id;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
}
