/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uniceub.fsw.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author hiragi
 */
public class Conversor {

    /**
     * Converte uma string de data no formato dd/MM/yyyy em um objeto Date
     * @param data
     * @return date
     */
    public static Date stringToDate(String data) {
        String formato = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
        try {
                return dateFormat.parse(data);
        } catch (ParseException e) {
                e.printStackTrace();
        }
        return null;
    }

    /**
     * Converte um objeto data em uma string no formato dd/MM/yyyy
     * @param data
     * @return
     */
    public static String dateToString(Date data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(data);
    }
}
