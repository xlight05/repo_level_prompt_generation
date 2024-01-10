package bean;

import com.opensymphony.xwork2.ActionSupport;
import dao.AlumnoDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class AlumnoBean extends ActionSupport {
//HOLA A TODOS

    private int id_ususario;
    private String nombre;
    private String amaterno;
    private String apaterno;
    private String especialidad;
    private List listaalumno = new ArrayList();

    public int getId_ususario() {
        return id_ususario;
    }

    public void setId_ususario(int id_ususario) {
        this.id_ususario = id_ususario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAmaterno() {
        return amaterno;
    }

    public void setAmaterno(String amaterno) {
        this.amaterno = amaterno;
    }

    public String getApaterno() {
        return apaterno;
    }

    public void setApaterno(String apaterno) {
        this.apaterno = apaterno;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public void setListaalumno(List listaalumno) {
        this.listaalumno = listaalumno;
    }

    public List getListaalumno() {
        return listaalumno;
    }

    @Override
    public String execute() throws SQLException {
        AlumnoDao alumno = new AlumnoDao();
        boolean insertOK = false;

        insertOK = alumno.insertar(getNombre(), getApaterno(), getAmaterno());
        if (insertOK) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    public String consultas() {

        AlumnoDao alumno = new AlumnoDao();
        setListaalumno(alumno.consultarTodo());
        JOptionPane.showMessageDialog(null, getListaalumno().size());
        if (getListaalumno().size() != 0) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }
////////////////////modificacion de alumno de  forma directa ibgresando id/////////////////////////////////

    public String modificarregistro() {
        boolean x = false;
        AlumnoDao al = new AlumnoDao();
        x = al.modificar(getId_ususario(), getNombre(), getApaterno(), getAmaterno());

        if (x) {
            consultaalumno();
            JOptionPane.showMessageDialog(null, getId_ususario());
            return SUCCESS;

        } else {
            return ERROR;
        }
    }

    public String consultaalumno() {
        AlumnoDao l = new AlumnoDao();
        JOptionPane.showMessageDialog(null, getId_ususario());

        setListaalumno(l.consultaPorId(getId_ususario()));
        //JOptionPane.showMessageDialog(null, "se encontro un rejistro");
        if (getId_ususario() != 0) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }
}
