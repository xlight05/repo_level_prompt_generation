package se.openprocesslogger.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import se.openprocesslogger.Data;
import se.openprocesslogger.db.DataFetcher;
import se.openprocesslogger.svg.data.CsvFormat;
import se.openprocesslogger.svg.data.DataDecoder;
import se.openprocesslogger.svg.data.ProcDataHolder;

/**
 * Servlet implementation class CSVExportServlet
 */
public class CSVExportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(CSVExportServlet.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CSVExportServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strFileName = "";
		String strCSVString = "";
		
		try{
			response.setContentType("text/csv; charset=UTF-8");
			log.debug("Names: ."+request.getParameter("varNames").replaceAll("\\|", "/")+".");
			log.debug("From: ."+request.getParameter("from")+".");
			log.debug("To: ."+request.getParameter("to")+".");
			log.debug("Id ."+request.getParameter("dataId")+".");
			String strVarNames = request.getParameter("varNames").replaceAll("\\|", "/");
			String[] arrVarNames = strVarNames.split("\\$");
			long from = Long.parseLong(request.getParameter("from"));
			long to = Long.parseLong(request.getParameter("to"));
			SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd_HH.mm.ss");
			strFileName = "graph_"+ dateFormat.format(new Date(from)) + "-" + dateFormat.format(new Date(to)) + ".csv";
			strCSVString = getCsvString(arrVarNames, from, to);
			response.setHeader("Content-Disposition", "inline; filename=" + strFileName);
			PrintWriter writer = response.getWriter();
			writer.println(strCSVString);
			writer.close();
		}catch(Exception ex){
			response.setContentType("text/html; charset=UTF-8");			
			PrintWriter writer = response.getWriter();
			writer.println(ex.toString());
			writer.close();	
		}
	}

	private String getCsvString(String[] varNames, long from, long to){
		if(varNames == null || varNames.length == 0)
			return null;
		ArrayList<ProcDataHolder> dataHolder = new ArrayList<ProcDataHolder>();
		for(int i=0 ; i<varNames.length ; i++){
			Data[] d = DataFetcher.instance().getData(varNames[i], from, to);
			if (d.length > 0){
				ProcDataHolder p = DataDecoder.decode(d);
				Arrays.sort(p.pData);
				dataHolder.add(p);
			}
		}		
		return CsvFormat.exportData(dataHolder.toArray(new ProcDataHolder[0]), from, to);
	}
}
