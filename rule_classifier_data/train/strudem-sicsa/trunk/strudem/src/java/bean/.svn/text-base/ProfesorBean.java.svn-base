/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.opensymphony.xwork2.ActionSupport;
import dao.AulaDao;
import dao.ProfesorDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author karlo0s
 */
public class ProfesorBean extends ActionSupport {
private int profesor_id;
private int usuario_id;
private String nombre;
private String a_paterno;
private String a_materno;
private int telefono;
private int edad;
private String direccion;
private List lista = new ArrayList();

    /**
     * @return the profesor_id
     */
    public int getProfesor_id() {
        return profesor_id;
    }

    /**
     * @param profesor_id the profesor_id to set
     */
    public void setProfesor_id(int profesor_id) {
        this.profesor_id = profesor_id;
    }

    /**
     * @return the usuario_id
     */
    public int getUsuario_id() {
        return usuario_id;
    }

    /**
     * @param usuario_id the usuario_id to set
     */
    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the a_paterno
     */
    public String getA_paterno() {
        return a_paterno;
    }

    /**
     * @param a_paterno the a_paterno to set
     */
    public void setA_paterno(String a_paterno) {
        this.a_paterno = a_paterno;
    }

    /**
     * @return the a_materno
     */
    public String getA_materno() {
        return a_materno;
    }

    /**
     * @param a_materno the a_materno to set
     */
    public void setA_materno(String a_materno) {
        this.a_materno = a_materno;
    }

    /**
     * @return the telefono
     */
    public int getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the edad
     */
    public int getEdad() {
        return edad;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
    
    public String cargarPagina() {
        return SUCCESS;
    }

    public String insertar() throws SQLException {
        ProfesorDao dao = new ProfesorDao();
        boolean insertOK;

        insertOK = dao.insertar(getNombre(),getA_paterno(),getA_materno(),getUsuario_id(),getEdad(),getTelefono(),getDireccion());
        if (insertOK) {
            addActionMessage("Se ha insertado un nuevo registro a la lista de profesores");
            return SUCCESS;
        } else {
            addActionError("No se pudo insertar el registro, corrigarlo e intente de nuevo");
            return ERROR;
        }
    }

    public String consultas() {

        ProfesorDao dao = new ProfesorDao();
        setLista(dao.consultarTodo());

        if (!getLista().isEmpty()) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    public String modificarRegistro() {
        boolean esCorrecto;
        ProfesorDao al = new ProfesorDao();
        esCorrecto = al.modificar(getProfesor_id(),getNombre(),getA_paterno(),getA_materno(),getUsuario_id(),getEdad(),getTelefono(),getDireccion());

        if (esCorrecto) {
            consultaProfesor();
            //JOptionPane.showMessageDialog(null, getProfesor_id());
            addActionMessage("Se ha modificado el registro " + getProfesor_id() + " satisfactoriamente");
            return SUCCESS;

        } else {
            addActionError("Ocurrió un error al intentar modificar el registro " + getProfesor_id() + ", verifiquelo e intente de nuevo");
            return ERROR;
        }
    }

    public String consultaProfesor() {
        ProfesorDao l = new ProfesorDao();
        setLista(l.consultaPorId(getProfesor_id()));
        System.out.println("la lista tiene: " + lista.size());

        if (getProfesor_id() != 0) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }
}
