/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import dao.MisGruposDao;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Gilberto Tenorio
 */
public class MisGruposBean extends ActionSupport {

    private int grupo_id;
    private int profesor_id;
    private int grupo_profesor_id;
    private int grado;
    private String grupo;
    private String profesor;
    private List lista;

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

    public int getIdSesion() {
        Map session = ActionContext.getContext().getSession();

        ProfesorBean p = (ProfesorBean) session.get("datosUsuario");
        return p.getProfesor_id();
    }

    public String misGrupos() {

        MisGruposDao dao = new MisGruposDao();
        MisGruposBean mgb = new MisGruposBean();
        setLista(dao.misGrupos());

        if (!getLista().isEmpty()) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    /**
     * @return the grupo_id
     */
    public int getGrupo_id() {
        return grupo_id;
    }

    /**
     * @param grupo_id the grupo_id to set
     */
    public void setGrupo_id(int grupo_id) {
        this.grupo_id = grupo_id;
    }

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
     * @return the grupo_profesor_id
     */
    public int getGrupo_profesor_id() {
        return grupo_profesor_id;
    }

    /**
     * @param grupo_profesor_id the grupo_profesor_id to set
     */
    public void setGrupo_profesor_id(int grupo_profesor_id) {
        this.grupo_profesor_id = grupo_profesor_id;
    }

    /**
     * @return the grupo
     */
    public String getGrupo() {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    /**
     * @return the profesor
     */
    public String getProfesor() {
        return profesor;
    }

    /**
     * @param profesor the profesor to set
     */
    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    /**
     * @return the grado
     */
    public int getGrado() {
        return grado;
    }

    /**
     * @param grado the grado to set
     */
    public void setGrado(int grado) {
        this.grado = grado;
    }
}
