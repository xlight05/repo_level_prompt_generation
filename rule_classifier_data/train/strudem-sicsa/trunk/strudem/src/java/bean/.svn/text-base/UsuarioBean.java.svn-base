package bean;

import com.opensymphony.xwork2.ActionSupport;
import dao.UsuarioDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioBean extends ActionSupport {

    private int usuario_id;
    private String usuario;
    private String password;
    private String tipo;
    private List lista = new ArrayList();
    private List<String> tiposUsuario;
    private String message;

    /**
     * @return the usuario_id
     */
    public int getUsuario_id() {
        return usuario_id;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the lista
     */
    public List getLista() {
        return lista;
    }

    /**
     * @param usuario_id the usuario_id to set
     */
    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
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

    /**
     * @param lista the lista to set
     */
    public void setLista(List lista) {
        this.lista = lista;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    public String cargarPagina() {
        tiposUsuario = new ArrayList();
        tiposUsuario.add("admin");
        tiposUsuario.add("profesor");
        return SUCCESS;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public String insertar() throws SQLException {
        UsuarioDao dao = new UsuarioDao();
        boolean insertOK;

        insertOK = dao.insertar(getUsuario(), getPassword(), getTipo());
        if (insertOK) {
            addActionMessage("Se ha insertado un nuevo registro a la lista de usuarios");
            return SUCCESS;
        } else {
            addActionError("No se pudo insertar el registro, intente nuevamente");
            return ERROR;
        }
    }

    public String consultas() {

        UsuarioDao dao = new UsuarioDao();
        setLista(dao.consultarTodo());

        if (!getLista().isEmpty()) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    public String modificarRegistro() {
        boolean esCorrecto;
        UsuarioDao al = new UsuarioDao();
        esCorrecto = al.modificar(getUsuario_id(), getUsuario(), getPassword(), getTipo());

        if (esCorrecto) {
            consultaUsuario();
            //JOptionPane.showMessageDialog(null, getUsuario_id());
            addActionMessage("Se ha modificado el registro " + getUsuario_id() + " satisfactoriamente");
            return SUCCESS;

        } else {
            addActionError("Ocurrió un error al intentar modificar el registro " + getUsuario_id() + ", verifiquelo e intente de nuevo");
            return ERROR;
        }
    }

    public String consultaUsuario() {
        UsuarioDao l = new UsuarioDao();
        setLista(l.consultaPorId(getUsuario_id()));
        
        
        if (getUsuario_id() != 0) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    /**
     * @return the tiposUsuario
     */
    public List getTiposUsuario() {
        return tiposUsuario;
    }

    /**
     * @param tiposUsuario the tiposUsuario to set
     */
    public void setTiposUsuario(List tiposUsuario) {
        this.tiposUsuario = tiposUsuario;
    }
}
