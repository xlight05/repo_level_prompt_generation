/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;
import dao.MateriaDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;

@ParentPackage(value = "admin")
@InterceptorRef("jsonValidationWorkflowStack")
@Validations(requiredStrings = {
    @RequiredStringValidator(fieldName = "nombre", type = ValidatorType.FIELD, message = "Nombre no puede estar vacio"),})
/**
 *
 * @author Mily
 */
public class MateriaBean extends ActionSupport {

    private int materia_id;
    private int status;
    private String statusLetra;
    private String nombre;
    private int cuatrimestre_id;
    private List listaCuatrimestre_id = new ArrayList();
    private List listaNivel = new ArrayList();
    private List lista = new ArrayList();

    public String cargarPagina() {
        MateriaDao dao = new MateriaDao();
        List carreraPack = new ArrayList();
        carreraPack = dao.consultarCuatrimestre();
        setListaCuatrimestre_id((List) carreraPack.get(0));
        setListaNivel((List) carreraPack.get(1));
        return SUCCESS;
    }

    public String insertar() throws SQLException {
        MateriaDao dao = new MateriaDao();
        boolean insertOK;

        insertOK = dao.insertar(getNombre(), getCuatrimestre_id(),getStatus());
        if (insertOK) {
            addActionMessage("Se ha insertado un nuevo registro a la lista de materia");
            return SUCCESS;
        } else {
            addActionError("No se pudo insertar el registro, corrigarlo e intente de nuevo");
            return ERROR;
        }
    }

    public String consultas() {

        MateriaDao dao = new MateriaDao();
        setLista(dao.consultarTodo());

        if (!getLista().isEmpty()) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    public String modificarRegistro() {
        boolean esCorrecto;
        MateriaDao al = new MateriaDao();
        esCorrecto = al.modificar(getMateria_id(), getNombre(), getCuatrimestre_id());

        if (esCorrecto) {
            consultaMateria();
            //JOptionPane.showMessageDialog(null, getAula_id());
            addActionMessage("Se ha modificado el registro " + getMateria_id() + " satisfactoriamente");
            return SUCCESS;

        } else {
            addActionError("Ocurrió un error al intentar modificar el registro " + getMateria_id() + ", verifiquelo e intente de nuevo");
            return ERROR;
        }
    }

    public String consultaMateria() {
        MateriaDao l = new MateriaDao();
        setLista(l.consultaPorId(getMateria_id()));
        System.out.println("la lista tiene: " + lista.size());

        if (getMateria_id() != 0) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    /**
     * @return the materia_id
     */
    public int getMateria_id() {
        return materia_id;
    }

    /**
     * @param materia_id the materia_id to set
     */
    public void setMateria_id(int materia_id) {
        this.materia_id = materia_id;
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
     * @return the cuatrimestre_id
     */
    public int getCuatrimestre_id() {
        return cuatrimestre_id;
    }

    /**
     * @param cuatrimestre_id the cuatrimestre_id to set
     */
    public void setCuatrimestre_id(int cuatrimestre_id) {
        this.cuatrimestre_id = cuatrimestre_id;
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

    /**
     * @return the listaCuatrimestre_id
     */
    public List getListaCuatrimestre_id() {
        return listaCuatrimestre_id;
    }

    /**
     * @return the listaNivel
     */
    public List getListaNivel() {
        return listaNivel;
    }

    /**
     * @param listaCuatrimestre_id the listaCuatrimestre_id to set
     */
    public void setListaCuatrimestre_id(List listaCuatrimestre_id) {
        this.listaCuatrimestre_id = listaCuatrimestre_id;
    }

    /**
     * @param listaNivel the listaNivel to set
     */
    public void setListaNivel(List listaNivel) {
        this.listaNivel = listaNivel;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the statusLetra
     */
    public String getStatusLetra() {
        return statusLetra;
    }

    /**
     * @param statusLetra the statusLetra to set
     */
    public void setStatusLetra(String statusLetra) {
        this.statusLetra = statusLetra;
    }
    
    

      public String inactivar(){
      MateriaDao dao = new MateriaDao();
      dao.inactivar(getMateria_id());
      setLista(dao.consultarTodo());
      return SUCCESS;
        
        
    }

}
