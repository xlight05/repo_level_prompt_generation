/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.opensymphony.xwork2.ActionSupport;
import dao.HorarioDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author seter
 */
public class horarioBean extends ActionSupport {

    private List horario_docente = new ArrayList();
    private List horario_docente2 = new ArrayList();
    private List horario_docente3 = new ArrayList();

    public List getHorario_docente2() {
        return horario_docente2;
    }

    public void setHorario_docente2(List horario_docente2) {
        this.horario_docente2 = horario_docente2;
    }

    public List getHorario_docente3() {
        return horario_docente3;
    }

    public void setHorario_docente3(List horario_docente3) {
        this.horario_docente3 = horario_docente3;
    }

    public List getHorario_docente4() {
        return horario_docente4;
    }

    public void setHorario_docente4(List horario_docente4) {
        this.horario_docente4 = horario_docente4;
    }

    public List getHorario_docente5() {
        return horario_docente5;
    }

    public void setHorario_docente5(List horario_docente5) {
        this.horario_docente5 = horario_docente5;
    }
    private List horario_docente4 = new ArrayList();
    private List horario_docente5 = new ArrayList();
    private String hora_inicio;
    private String hora_fin;
    private String dia;
    private String carrera;
    private String aula;

    public int getClasee_id() {
        return clasee_id;
    }

    public void setClasee_id(int clasee_id) {
        this.clasee_id = clasee_id;
    }
    private int clasee_id;

    public int getLista_clase() {
        return lista_clase;
    }

    public void setLista_clase(int lista_clase) {
        this.lista_clase = lista_clase;
    }
    private int lista_clase;
    private String materria;
    private String periodo;
    private String grupo;
    private String cuatromestre;
    private String edificio;

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public List getHorario_docente() {
        return horario_docente;
    }

    public void setHorario_docente(List horario_docente) {
        this.horario_docente = horario_docente;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getCuatromestre() {
        return cuatromestre;
    }

    public void setCuatromestre(String cuatromestre) {
        this.cuatromestre = cuatromestre;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getMaterria() {
        return materria;
    }

    public void setMaterria(String materria) {
        this.materria = materria;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    //  private String horas_por_semana;

    @Override
    public String execute() throws SQLException {
        HorarioDao horario = new HorarioDao();
        setHorario_docente(horario.horarioDocente());
        setHorario_docente2(horario.horarioDocente2());
        setHorario_docente3(horario.horarioDocente3());
        setHorario_docente4(horario.horarioDocente4());
        setHorario_docente5(horario.horarioDocente5());
        System.out.println("//////////" + getHorario_docente().getClass());
        JOptionPane.showMessageDialog(null, getHorario_docente().size());
        if (!getHorario_docente().isEmpty()) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }
}
