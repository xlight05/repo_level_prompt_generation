/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.UsuarioDao;
import com.opensymphony.xwork2.Action;
import java.util.Map;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.List;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author Mauricio
 */
public class LoginBean extends ActionSupport implements SessionAware{

    private Map session;
    private String mensajeError;
    private String usr;
    private String pas;
    private int profesor_id;
    private String nombre;
    private String a_materno;
    private String a_paterno;
    private String telefono;
    private String direccion;
    private int edad;
    private String tipo;

    public String execute() throws Exception {
        String estado = Action.ERROR;
        if (getUsr() != null && getPas() != null) {    //Verificamos que los datos no sean nulos

            UsuarioBean usuario;
            UsuarioDao dao = new UsuarioDao();
            usuario = dao.accederUsuario(getUsr(), getPas());

            //obtiene datos de la tabla profesor por medio del usuario_id
            LoginBean datosUsuario = dao.detallesUsuario(usuario.getUsuario_id());

            //Fin de busqueda
            if (usuario != null) {
                //Si el usuario se encuentra se agrega a la sesion
                session.put("usuario", usuario);
                session.put("datosUsuario", datosUsuario);

                if ("admin".equals(usuario.getTipo())) {
                    estado = SUCCESS;
                } else if ("profesor".equals(usuario.getTipo())) {
                    estado = LOGIN;
                }

            } else {
                //Si no se encuentra se devuelve un error
                addActionError("No existe esta cuenta");
                setMensajeError("No existe esta cuenta");
                return ERROR;
            }
        } else {
            addActionError("Inserte datos correctos");
            setMensajeError("Inserte datos correctos");
            return ERROR;
        }
        return estado;
    }

    /**
     *
     * @return Devuelve el tipo de usuario que ha iniciado sessión si es
     * profesor o administador
     */
    public String tipoUsuarioActual() {
        String resultado = ERROR;
        //TODO: SELECCION DE TIPO DE USUARIO CON LOS RESULTADOS LOGIN Y SUCCESS
        //Map se = ActionContext.getContext().getSession();
        UsuarioBean usuario;
        try {
            usuario = (UsuarioBean) session.get("usuario");
            if ("admin".equals(usuario.getTipo())) {
                resultado = SUCCESS;
            } else if ("profesor".equals(usuario.getTipo())) {
                resultado = LOGIN;
            }
        } catch (Exception e) {
            addActionError("Acceso restringido al contenido o no haz iniciado sesión");
            setMensajeError("Acceso restringido al contenido o no haz iniciado sesión");
        }

        return resultado;
    }

    public String cerrarSession() {
        //Map session = ActionContext.getContext().getSession();
        session.remove("usuario");
        session.remove("datosUsuario");
        return SUCCESS;
    }

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public String getPas() {
        return pas;
    }

    public void setPas(String pas) {
        this.pas = pas;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    /**
     * @return the mensajeError
     */
    public String getMensajeError() {
        return mensajeError;
    }

    /**
     * @return the profesor_id
     */
    public int getProfesor_id() {
        return profesor_id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the a_materno
     */
    public String getA_materno() {
        return a_materno;
    }

    /**
     * @return the a_paterno
     */
    public String getA_paterno() {
        return a_paterno;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @return the edad
     */
    public int getEdad() {
        return edad;
    }

    /**
     * @param profesor_id the profesor_id to set
     */
    public void setProfesor_id(int profesor_id) {
        this.profesor_id = profesor_id;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @param a_materno the a_materno to set
     */
    public void setA_materno(String a_materno) {
        this.a_materno = a_materno;
    }

    /**
     * @param a_paterno the a_paterno to set
     */
    public void setA_paterno(String a_paterno) {
        this.a_paterno = a_paterno;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
