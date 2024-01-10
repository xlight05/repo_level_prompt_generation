package br.uniceub.fsw.framework;

/**
 * Classe temporariamente comentada até que surja necessiade para ela. Importe as libs na pasta ireport incluidas auando precissar usar essa classe.
 * A importacao das libs para esta classe acarreta num aumento absurdo de analise de codigo no netbeans
 */


//
//import javax.servlet.http.*;
//import net.sf.jasperreports.engine.*;
//import java.io.*;
//import java.util.*;
//import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//
///**
// *
// * @author Gilberto Hiragi
// */
//public class Report {
//
//    public static boolean impRelPDF(String pathRel, HashMap params, List fields, HttpServletResponse response) {
//        try {
//            response.reset();
//            response.setContentType("application/pdf");
//            OutputStream out = response.getOutputStream();
//
//            JasperReport jr = JasperCompileManager.compileReport(pathRel);
//            JasperPrint jp;
//            JRDataSource jrds;
//
//            if (fields == null)
//                 jp = JasperFillManager.fillReport(jr, params, ConexaoMySQL.getInstance());
//            else {
//                jrds = new JRBeanCollectionDataSource(fields);
//                jp = JasperFillManager.fillReport(jr, params, jrds);
//            }
//            JasperExportManager.exportReportToPdfStream(jp, out);
//
//            return true;
//        } catch (Exception ex) {
//            System.out.println("Erro ao emitir relat�rio: " + pathRel + " - Exce��o: " + ex);
//            return false;
//        }
//
//
//    }
//
//    public static boolean impRelExcel(String pathRel, HashMap params, List fields, HttpServletResponse response) {
//        try {
//            response.reset();
//            response.setContentType("application/vnd.ms-excel");
//            OutputStream out = response.getOutputStream();
//
//            JasperReport jr = JasperCompileManager.compileReport(pathRel);
//            JasperPrint jp;
//            JRDataSource jrds;
//
//            if (fields == null)
//                 jp = JasperFillManager.fillReport(jr, params, ConexaoMySQL.getInstance());
//            else {
//                jrds = new JRBeanCollectionDataSource(fields);
//                jp = JasperFillManager.fillReport(jr, params, jrds);
//            }
//
//            net.sf.jasperreports.engine.export.JExcelApiExporter exporter = new net.sf.jasperreports.engine.export.JExcelApiExporter();
//            exporter.setParameter(net.sf.jasperreports.engine.export.JExcelApiExporterParameter.JASPER_PRINT, jp);
//            exporter.setParameter(net.sf.jasperreports.engine.export.JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
//            exporter.setParameter(net.sf.jasperreports.engine.export.JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
//            exporter.setParameter(net.sf.jasperreports.engine.export.JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
//            exporter.setParameter(net.sf.jasperreports.engine.export.JExcelApiExporterParameter.IS_FONT_SIZE_FIX_ENABLED, Boolean.TRUE);
//            exporter.setParameter(net.sf.jasperreports.engine.export.JExcelApiExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);
//            exporter.setParameter(net.sf.jasperreports.engine.export.JExcelApiExporterParameter.OUTPUT_STREAM, out);
//            exporter.exportReport();
//
//            return true;
//        } catch (Exception ex) {
//            System.out.println("Erro ao emitir relat�rio: " + pathRel + " - Exce��o: " + ex);
//            return false;
//        }
//
//    }
//}
