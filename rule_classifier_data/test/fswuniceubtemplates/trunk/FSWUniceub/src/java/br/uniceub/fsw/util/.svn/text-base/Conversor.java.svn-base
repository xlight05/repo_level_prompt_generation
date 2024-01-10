/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uniceub.fsw.util;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    /**
     * Converte uma String em um String codificada com md5 iqual ao do MySQL
     * @param input
     * @return
     * @throws NoSuchAlgorithmException 
     */
    public static String md5(String input){
        String result = input;
        if(input != null) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5"); //or "SHA-1"
                md.update(input.getBytes());
                BigInteger hash = new BigInteger(1, md.digest());
                result = hash.toString(16);
                if ((result.length() % 2) != 0) {
                    result = "0" + result;
                }
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
