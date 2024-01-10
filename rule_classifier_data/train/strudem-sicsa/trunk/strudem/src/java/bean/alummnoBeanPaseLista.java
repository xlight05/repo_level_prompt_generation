/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

/**
 *
 * @author seter
 */
public class alummnoBeanPaseLista {

    private int alumno_id;
    private String nombre;
    private String a_paterno;
    private String a_materno;
    private int historialalumno;
    private int listaasistencia;
    private int unidads;

    public int getUnidads() {
        return unidads;
    }

    public void setUnidads(int unidads) {
        this.unidads = unidads;
    }
    public String getA_materno() {
        return a_materno;
    }

    public void setA_materno(String a_materno) {
        this.a_materno = a_materno;
    }

    public String getA_paterno() {
        return a_paterno;
    }

    public void setA_paterno(String a_paterno) {
        this.a_paterno = a_paterno;
    }

    public int getAlumno_id() {
        return alumno_id;
    }

    public void setAlumno_id(int alumno_id) {
        this.alumno_id = alumno_id;
    }

    public int getHistorialalumno() {
        return historialalumno;
    }

    public void setHistorialalumno(int historialalumno) {
        this.historialalumno = historialalumno;
    }

    public int getListaasistencia() {
        return listaasistencia;
    }

    public void setListaasistencia(int listaasistencia) {
        this.listaasistencia = listaasistencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
