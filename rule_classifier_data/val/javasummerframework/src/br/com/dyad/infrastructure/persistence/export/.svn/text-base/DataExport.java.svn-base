package br.com.dyad.infrastructure.persistence.export;

import java.util.List;

import br.com.dyad.commons.data.DataList;

/**
 * 
 * @author Danilo Sampaio
 * Classe usada para exportar dados a partir de um datalist. Os formatos disponíveis são: .csv, .xls, e pdf.
 *
 */
public class DataExport {
	public static String CSV = "CSV";
	public static String PDF = "PDF";
	public static String XLS = "XLS";
	List dataList;
	
	public DataExport(){
		
	}
	public DataExport( List dataList ){
		this();
		this.dataList = dataList;
	}
	
	public List getDataList() {
		return dataList;
	}
	public void setDataList(List dataList) {
		this.dataList = dataList;
	}	
	
	/**
	 * @author Danilo Sampaio
	 * @param exporter
	 * @return
	 * Exporta os dados do datalist para o formato .csv
	 * @throws Exception 
	 */
	public String toCSV( CSVExporter exporter ) throws Exception {
		validateParams();
		return (String) exporter.export(dataList);
	}
	/**
	 * @author Danilo Sampaio
	 * @param exporter
	 * @return
	 * Exporta os dados do datalist para o formato .xls
	 * @throws Exception 
	 */
	public String toXLS( XLSExporter exporter ) throws Exception {
		validateParams();
		return (String) exporter.export(dataList);
	}
	/**
	 * @author Danilo Sampaio
	 * @param exporter
	 * @return
	 * Exporta os dados do datalist para o formato .pdf
	 * @throws Exception 
	 */
	public String toPDF( PDFExporter exporter ) throws Exception {
		validateParams();
		return (String) exporter.export(dataList);
	}
	
	private void validateParams() throws Exception{
		if ( dataList == null ){
			throw new Exception("O datalist não foi informado.");
		}
	}
}
