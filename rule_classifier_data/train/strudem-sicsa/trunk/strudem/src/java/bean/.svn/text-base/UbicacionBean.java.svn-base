/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.opensymphony.xwork2.ActionSupport;
import dao.PeriodoDao;
import dao.UbicacionDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author karlo0s
 */
public class UbicacionBean extends ActionSupport {

    private int ubicacion_id;
    private int edificio_id;
    private int aula_id;
    private String edificio, aula;
    private List lista = new ArrayList();

    /**
     * @return the ubicacion_id
     */
    public int getUbicacion_id() {
        return ubicacion_id;
    }

    /**
     * @param ubicacion_id the ubicacion_id to set
     */
    public void setUbicacion_id(int ubicacion_id) {
        this.ubicacion_id = ubicacion_id;
    }

    /**
     * @return the edificio_id
     */
    public int getEdificio_id() {
        return edificio_id;
    }

    /**
     * @param edificio_id the edificio_id to set
     */
    public void setEdificio_id(int edificio_id) {
        this.edificio_id = edificio_id;
    }

    /**
     * @return the aula_id
     */
    public int getAula_id() {
        return aula_id;
    }

    /**
     * @param aula_id the aula_id to set
     */
    public void setAula_id(int aula_id) {
        this.aula_id = aula_id;
    }

    /**
     * @return the edificio
     */
    public String getEdificio() {
        return edificio;
    }

    /**
     * @return the aula
     */
    public String getAula() {
        return aula;
    }

    /**
     * @param edificio the edificio to set
     */
    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    /**
     * @param aula the aula to set
     */
    public void setAula(String aula) {
        this.aula = aula;
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
        UbicacionDao dao = new UbicacionDao();
        boolean insertOK;

        insertOK = dao.insertar(getEdificio_id(), getAula_id());
        if (insertOK) {
            addActionMessage("Se ha insertado un nuevo registro a la lista de ubicacion");
            return SUCCESS;
        } else {
            addActionError("No se pudo insertar el registro, corrigarlo e intente de nuevo");
            return ERROR;
        }
    }

    public String consultas() {

        UbicacionDao dao = new UbicacionDao();
        setLista(dao.consultarTodo());

        if (!getLista().isEmpty()) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    public String modificarRegistro() {
        boolean esCorrecto;
        UbicacionDao al = new UbicacionDao();
        esCorrecto = al.modificar(getUbicacion_id(), getEdificio_id(), getAula_id());

        if (esCorrecto) {
            consultaUbicacion();
            //JOptionPane.showMessageDialog(null, getUbicacion_id());
            addActionMessage("Se ha modificado el registro " + getUbicacion_id() + " satisfactoriamente");
            return SUCCESS;

        } else {
            addActionError("Ocurrió un error al intentar modificar el registro " + getUbicacion_id() + ", verifiquelo e intente de nuevo");
            return ERROR;
        }
    }

    public String consultaUbicacion() {
        UbicacionDao l = new UbicacionDao();
        setLista(l.consultaPorId(getUbicacion_id()));
        System.out.println("la lista tiene: " + lista.size());

        if (getUbicacion_id() != 0) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }
}
