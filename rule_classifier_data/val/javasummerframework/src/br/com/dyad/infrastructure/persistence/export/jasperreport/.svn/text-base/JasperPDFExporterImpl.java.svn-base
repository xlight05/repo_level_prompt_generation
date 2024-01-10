package br.com.dyad.infrastructure.persistence.export.jasperreport;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRAlignment;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignImage;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JRDesignRectangle;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.util.JRColorUtil;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.dyad.client.system.SystemInfo;
import br.com.dyad.commons.reflect.ReflectUtil;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.entity.KeyGenerator;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;
import br.com.dyad.infrastructure.persistence.export.DataExport;
import br.com.dyad.infrastructure.persistence.export.PDFExporter;

public class JasperPDFExporterImpl implements PDFExporter{
	//-- Parametros de configuracao	
	private static final String PARAM_TITLE = "REPORT_TITLE";
	private static final String PARAM_SUBTITLE = "REPORT_SUBTITLE";
	private static final String PARAM_ENTERPRISE_NAME = "ENTERPRISE_NAME";
	private static final String PARAM_ENTERPRISE_URI = "ENTERPRISE_URI";
	private static final String PARAM_LOGO_URI = "ENTERPRISE_LOGO_URL";
	
	private Map<String, String> params;
	
	List dataList;
	String[] columns;
	String[] columnLabels;
	Integer maxRecordCount;
	private String database;
	
	public JasperPDFExporterImpl( String database, String[] columns ){
		this.database = database;
		this.columns = columns;
	}	
	public JasperPDFExporterImpl(String database, String[] columns, String[] columnLabels ){
		this(database, columns);
		this.columnLabels = columnLabels;
	}
	public JasperPDFExporterImpl( String database, String[] columns, String[] columnLabels, Integer maxRecordCount ){
		this(database, columns, columnLabels);
		this.maxRecordCount = maxRecordCount;
	}
	
	public String[] getColumns() {
		return columns;
	}
	public void setColumns(String[] columns) {
		this.columns = columns;
	}	
	public String[] getColumnLabels() {
		return columnLabels;
	}
	public void setColumnLabels(String[] columnLabels) {
		this.columnLabels = columnLabels;
	}	
	public Integer getMaxRecordCount() {
		return maxRecordCount;
	}
	public void setMaxRecordCount(Integer maxRecordCount) {
		this.maxRecordCount = maxRecordCount;		
	}	
	public Map<String, String> getParams() {
		return params;
	}
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	public List getDataList() {
		return dataList;
	}
	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	@Override
	public Object export(List list){
		try {
			System.out.println(list.size());
			if ( list == null ){
				throw new RuntimeException("O datalist não foi informado.");
			}
			if ( columns == null || columns.length == 0 ){
				throw new RuntimeException("As colunas não foram informadas.");
			}						
			configureParams();
			
			JasperDesign jasperDesign = createReport(list);
			JasperReport jasperReport	= JasperCompileManager.compileReport(jasperDesign);
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list);		
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, ds);
			
			Long fileKey = KeyGenerator.getInstance(database).generateKey(KeyGenerator.KEY_RANGE_NO_LICENSE);
			File tempDir = new File(SystemInfo.getInstance().getTempDir() + File.separator + database + File.separator + "downloads");
			tempDir.mkdir();
			File targetFile = new File(tempDir.getPath() + File.separator + fileKey + ".pdf");
			
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter( JRPdfExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter( JRPdfExporterParameter.OUTPUT_FILE, targetFile );
			exporter.setParameter( JRPdfExporterParameter.IGNORE_PAGE_MARGINS, true );
			exporter.setParameter(JRPdfExporterParameter.CHARACTER_ENCODING, "UTF-8");					
			exporter.exportReport();
			return targetFile.getAbsolutePath();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private void configureParams() {
		if ( params == null ){
			params = new HashMap<String, String>();
			params.put( JasperPDFExporterImpl.PARAM_TITLE, "Usuários" );
			params.put( JasperPDFExporterImpl.PARAM_SUBTITLE, "Teste de relatorio da nova infra" );		
			params.put( JasperPDFExporterImpl.PARAM_ENTERPRISE_NAME, "Dyad & Associados");
		}
	}	
	
	private JasperDesign createReport( List datalist ) throws Exception {
		//Cria o objeto do relatorio, configura os imports, propriedades e a consulta;
		JasperDesign report = new JasperDesign();
		report.setName("Teste User");
		report.addImport("java.util.*");
		report.addImport("br.com.dyad.infrastructure.entity.*");
		report.addImport("net.sf.jasperreports.engine.*");
		report.addImport("net.sf.jasperreports.engine.data.*");
		report.setProperty( "ireport.scriptlethandling", "2" );
		report.setProperty( "ireport.encoding", "UTF-8" );		
		
		report.setColumnCount(columns.length);
		int pageWidth = report.getPageWidth() - ( 2 * report.getRightMargin() );
		report.setColumnWidth(pageWidth / columns.length);
		report.setPageWidth(600);
		report.setColumnSpacing(0);
		report.setLeftMargin(10);
		report.setRightMargin(10);
		report.setTopMargin(10);
		report.setBottomMargin(10);
		report.setTitleNewPage(false);
		report.setSummaryNewPage(false);
				
		//Define as colunas do relatorio 
		this.defineFields(report, datalist);
		this.defineStyles( report );
		this.defineParameters( report );
		this.defineTitle(report);
		this.defineColumnHeader(report);
		this.defineDetail( report, datalist );

		return report;
	}
	
	private void defineFields(JasperDesign report, List list) throws JRException {
		for (int i = 0; i < columns.length; i++) {
			JRDesignField fieldAux = new JRDesignField();			
			fieldAux.setName(columns[i]);
			//fieldAux.setDescription(columnLabels[i]);
			if ( list != null && list.size() > 0 ){
				Class fieldType = ReflectUtil.getFieldType(list.get(0).getClass(), columns[i]);
				fieldAux.setValueClass(fieldType);
				report.addField( fieldAux );
			}			
		}
	}
	
	private void defineStyles(JasperDesign report) throws JRException {
		JRDesignStyle styleBackground = new JRDesignStyle();
		styleBackground.setName("backgroundStyle");
		styleBackground.setHorizontalAlignment(JRAlignment.HORIZONTAL_ALIGN_CENTER);
		styleBackground.setVerticalAlignment(JRAlignment.VERTICAL_ALIGN_MIDDLE);
		styleBackground.setBold(true);
		styleBackground.setFontName("Helvetica");
		styleBackground.setPdfFontName("Helvetica-Bold");
		styleBackground.setForecolor(JRColorUtil.getColor("lightGray", java.awt.Color.lightGray));
		styleBackground.setFontSize(150);
		report.addStyle(styleBackground);		
	}
	
	private void defineParameters(JasperDesign report) {
		try {
			JRDesignParameter paramTitle 	= new JRDesignParameter();
			JRDesignParameter paramSubtitle	= new JRDesignParameter();
			JRDesignParameter paramEnterpriseName = new JRDesignParameter();
			JRDesignParameter paramEnterpriseLogoURL = new JRDesignParameter();
			JRDesignParameter paramEnterpriseSiteURL= new JRDesignParameter();

			paramTitle.setName( JasperPDFExporterImpl.PARAM_TITLE );
			paramTitle.setValueClass(java.lang.String.class);
			
			paramSubtitle.setName( JasperPDFExporterImpl.PARAM_SUBTITLE );
			paramSubtitle.setValueClass(java.lang.String.class);
			
			paramEnterpriseName.setName( JasperPDFExporterImpl.PARAM_ENTERPRISE_NAME );
			paramEnterpriseName.setValueClass(java.lang.String.class);
			
			paramEnterpriseLogoURL.setName( JasperPDFExporterImpl.PARAM_LOGO_URI );
			paramEnterpriseLogoURL.setValueClass(java.lang.String.class);
			
			paramEnterpriseSiteURL.setName( JasperPDFExporterImpl.PARAM_ENTERPRISE_URI );
			paramEnterpriseSiteURL.setValueClass(java.lang.String.class);
			
			report.addParameter(paramTitle);
			report.addParameter(paramSubtitle);
			report.addParameter(paramEnterpriseName);
			report.addParameter(paramEnterpriseLogoURL);
			report.addParameter(paramEnterpriseSiteURL);
		} catch (JRException e) {
			e.printStackTrace();
		}
		//List<JRParameter> l = report.getParametersList();
		//for (JRParameter param : l) logger.info("param.name:"+ param.getName());		
	}
	
	private void defineDetail(JasperDesign report, List list) {
		JRDesignBand detail = new JRDesignBand();
		detail.setHeight(15);
		detail.setSplitType(JRBand.SPLIT_TYPE_IMMEDIATE);
		
		int position = 5;
		for ( int i = 0; i < columns.length; i++) {
			//Define a expression
			JRDesignExpression exp = new JRDesignExpression();			
			//exp.setValueClass(java.lang.String.class);
			if ( list != null && list.size() > 0 ){
				Class reportClass = list.get(0).getClass();
				Class fieldType = ReflectUtil.getFieldType(reportClass, columns[i]);
				if ( ReflectUtil.inheritsFrom(fieldType, BaseEntity.class) ){
					exp.setText("$F{"+ columns[i] +"}.toString()");
					exp.setValueClass(String.class);
				} else {
					exp.setText("$F{"+ columns[i] +"}");
					exp.setValueClass(fieldType);
				}				
			}

			//Define os TextFields
			JRDesignTextField textField = new JRDesignTextField();
			textField.setStretchWithOverflow(true);
			textField.setBlankWhenNull(true);
			textField.setEvaluationTime( JRExpression.EVALUATION_TIME_NOW );
			textField.setHyperlinkType(JRHyperlink.HYPERLINK_TYPE_NONE);
			textField.setHyperlinkTarget( JRHyperlink.HYPERLINK_TARGET_SELF );
			textField.setX(position);
			textField.setY(0);
			int pageWidth = report.getPageWidth() - ( 2 * report.getRightMargin() );
			textField.setWidth( pageWidth / columns.length );
			textField.setHeight(15);
			textField.setExpression( exp );			
			detail.addElement(textField);
			position += pageWidth / columns.length;
		}		
		report.setDetail(detail);
	}
	
	private void defineColumnHeader(JasperDesign report) {
		JRDesignBand columnHeaders = new JRDesignBand();
		columnHeaders.setHeight(20);
		columnHeaders.setSplitType(JRBand.SPLIT_TYPE_IMMEDIATE);
		int count = 0;
		for ( int i = 0; i < columns.length; i++) {
			JRDesignStaticText header = new JRDesignStaticText();
			header.setText(columnLabels[i]);
			header.setKey("staticText-"+ count );
			int pageWidth = report.getPageWidth() - ( 2 * report.getRightMargin() );
			header.setWidth(pageWidth);
			header.setHeight(columnHeaders.getHeight());			
			header.setFontSize(10); 	
			header.setBold(true);		
			header.setUnderline(true);	
			header.setX(count);
			header.setY(0);
			columnHeaders.addElement(header);
			count += pageWidth / columns.length;
		}
		report.setColumnHeader( columnHeaders );
	}
	
	private void defineTitle(JasperDesign report) {
		JRDesignBand titulo = new JRDesignBand();
		titulo.setHeight(40);
		titulo.setSplitType(JRBand.SPLIT_TYPE_IMMEDIATE);		

		JRDesignRectangle backTitulo = new JRDesignRectangle();
		backTitulo.setX(0);
		backTitulo.setY(0);
		backTitulo.setHeight(titulo.getHeight());
		backTitulo.setWidth(report.getPageWidth() - ( 2 * report.getRightMargin() ) );		
		backTitulo.setBackcolor(new java.awt.Color(204, 204, 204));
		backTitulo.setStretchType(JRElement.STRETCH_TYPE_NO_STRETCH);
		titulo.addElement(backTitulo);
		//-- Define os TextFields
		JRDesignExpression enterpriseExp = new JRDesignExpression();
		enterpriseExp.setText("$P{ENTERPRISE_NAME}");
		enterpriseExp.setValueClass(java.lang.String.class);
		JRDesignTextField textFieldEnterpriseName = new JRDesignTextField();
		textFieldEnterpriseName.setStretchWithOverflow(true);
		textFieldEnterpriseName.setBlankWhenNull(true);
		textFieldEnterpriseName.setEvaluationTime( JRExpression.EVALUATION_TIME_NOW );
		textFieldEnterpriseName.setHyperlinkType(JRHyperlink.HYPERLINK_TYPE_NONE);
		textFieldEnterpriseName.setHyperlinkTarget( JRHyperlink.HYPERLINK_TARGET_SELF );
		textFieldEnterpriseName.setY(0);
		textFieldEnterpriseName.setWidth(424);
		textFieldEnterpriseName.setHeight(titulo.getHeight());
		textFieldEnterpriseName.setExpression( enterpriseExp );
		textFieldEnterpriseName.setFontSize(18);
		textFieldEnterpriseName.setMode(JRElement.MODE_TRANSPARENT);	
		//-- Adiciona a logomarca, se for definido um arquivo
		if ( this.params.get("ENTERPRISE_LOGO_URL") != null ) {
			JRDesignExpression logoExp = new JRDesignExpression();
			logoExp.setText("$P{ENTERPRISE_LOGO_URL}");
			logoExp.setValueClass(java.lang.String.class);
			JRDesignExpression logoInformadoExp = new JRDesignExpression();
			logoInformadoExp.setText("$P{ENTERPRISE_LOGO_URL} != null");
			logoInformadoExp.setValueClass(java.lang.Boolean.class);
			
			JRDesignImage logo = new JRDesignImage(report);
			logo.setStretchType(JRElement.STRETCH_TYPE_RELATIVE_TO_BAND_HEIGHT);
			logo.setExpression(logoExp);
			logo.setHeight(titulo.getHeight());
			logo.setWidth(100);
			logo.setX(0);
			logo.setY(0);
			logo.setMode(JRElement.MODE_TRANSPARENT);
			logo.setPrintWhenExpression(logoInformadoExp);			
			
			titulo.addElement(logo);
			textFieldEnterpriseName.setX(110);
		} else {
			textFieldEnterpriseName.setX(0);			
		}
		titulo.addElement(textFieldEnterpriseName);		
		report.setTitle(titulo);
	}
	
	public static void main(String[] args) {
		DataExport de = new DataExport();
		Session session = PersistenceUtil.getSession("INFRA");
		Query q = session.createQuery("from User");
		List userList = q.list();
		de.setDataList(userList);
		try {
			de.toPDF(new JasperPDFExporterImpl( "INFRA", new String[]{"id", "login", "completeName", "email", "language"},
					new String[]{"Id", "Login", "Complete Name", "Email", "Language"}));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
