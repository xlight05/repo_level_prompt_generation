package br.com.dyad.infrastructure.service.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Table;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.com.dyad.client.DataClass;
import br.com.dyad.client.DyadBaseModel;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.entity.KeyGenerator;
import br.com.dyad.infrastructure.entity.KeyRange;
import br.com.dyad.infrastructure.entity.NavigatorEntity;
import br.com.dyad.infrastructure.entity.ProductLicense;
import br.com.dyad.infrastructure.entity.User;
import br.com.dyad.infrastructure.navigation.admin.security.SecurityUtil;
import br.com.dyad.infrastructure.navigation.persistence.HibernateUtil;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;
import br.com.dyad.infrastructure.utils.MD5Util;

/**
 * @author Danilo Sampaio
 * Este servlet tem a finalidade de fornecer um ponto de conexão com a IDE(Eclipse) para através do plugin da Dyad.
 * O plugin se conecta ao servlet via http, e com autenticação.
 *
 */
public class NavigationServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {		
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException{
		
		//-- Esse parâmetro é passado para requisitar as licenças que o usuário tem permissão
		String getLicenses = request.getParameter(MD5Util.encryptValue("getLicenses"));
		//-- Esse parâmetro é passado para requisitar a árvore de produto, no formato xml
		String getTree = request.getParameter(MD5Util.encryptValue("getTree"));
		//-- Esse parâmetro é passado para requisitar a árvore de classes, no formato xml
		String getBeans = request.getParameter(MD5Util.encryptValue("getBeans"));
		//-- Esse parâmetro é passado para requisitar a criação de um novo bean
		String createBean = request.getParameter(MD5Util.encryptValue("createBean"));
		
		String database = request.getParameter(MD5Util.encryptValue("database"));
		String user = request.getParameter(MD5Util.encryptValue("user"));
		String password = request.getParameter(MD5Util.encryptValue("password"));
		
		//-- Na criação de uma nova classe ou process, o id do parente é necessário. Na tabela sysnavigator, este campo é o parentid
		Long parentId = null;		
		String licenseName = null;		
		//-- Nome da classe a ser criada, que se refere ao campo name
		String className = null;
		//-- No caso da criação de um novo processo, se refere ao nome completo da classe java, por exemplo br.com.dyad.infrastructure.navigation.Teste
		String windowName = null;
		//-- TODO 
		String packageName = null;
		//-- Classe root das classes de dados 
		String baseEntity = BaseEntity.class.getName();
		//-- Superclasse da bean que será criado
		String parentBeanName = null;
		
		if ( getLicenses == null && getTree == null && getBeans == null ){
			licenseName = request.getParameter(MD5Util.encryptValue("licenseName"));
			if ( createBean == null ){
				parentId = new Long(request.getParameter(MD5Util.encryptValue("parentId")));						
				className = request.getParameter(MD5Util.encryptValue("className"));
				String tempWindowName = request.getParameter(MD5Util.encryptValue("windowName"));
				windowName = tempWindowName != null && !tempWindowName.equals("null") ? tempWindowName : null;
				String tempPackage = request.getParameter(MD5Util.encryptValue("packageName"));
				packageName = tempPackage != null && !tempPackage.equals("null") ? tempPackage : null;
			}			
		}
		if (createBean != null ){
			parentBeanName = request.getParameter(MD5Util.encryptValue("parentBean"));
		}
		
		//-- Verifica se o usuário é válido
		Session session = PersistenceUtil.getSession(database);
		Query q = session.createQuery("from User where login = '" + user + "' and password = '" + password + "'");
		List userList = q.list();
		Long userKey = userList.size() > 0 ? ((User)userList.get(0)).getId() : null;
		
		if ( userKey != null ){
			if ( getLicenses != null ){
				doGetLicenses(response, database, session, userKey);
			} else if ( getTree != null ){
				doGetTree(response, database, session);				
			} else if ( getBeans != null ){
				doGetBeans(response, database, baseEntity, session);
			} else if( createBean != null ){
				doCreateBean(response, database, licenseName, parentBeanName, session);
			} else {
				doCreateWindow(response, database, parentId, licenseName, className, windowName, session);
			}
		}	
	}

	private void doCreateWindow(HttpServletResponse response, String database, Long parentId, String licenseName, String className,
			String windowName, Session session) {
		
		if ( className != null ){
			PrintWriter out = null;
			try {
				out = response.getWriter();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			try {
				ProductLicense licenseByName = getLicenseByName(session, licenseName);
				KeyRange keyRange = licenseByName.getKeyRange();
				Long licenseId = licenseByName.getId();
				Long rangeId = keyRange.getId();
				
				session.beginTransaction();
				NavigatorEntity nav = new NavigatorEntity();				
				if ( rangeId == null ){
					nav.createId(database);
				} else {
					nav.createId(database, rangeId);
				}
				nav.setClassId("-89999999999982");
				nav.setLicenseId(licenseId);
				nav.setName(className);
								
				Query createQuery = session.createQuery("from NavigatorEntity where id = " + parentId );
				NavigatorEntity parent = (NavigatorEntity) createQuery.list().get(0);
				
				parent.setId(parentId);					
				nav.setParent(parent);
				nav.setWindow(windowName);
				session.save(nav);				
				session.getTransaction().commit();
				
				try {
					out.write(nav.getId() + "");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				session.clear();
				session.close();
			} catch (Exception e) {
				e.printStackTrace();
				out.write("ERROR=" + e.getMessage());
			}
		}
	}

	private void doCreateBean(HttpServletResponse response, String database, String licenseName, String parentBeanName, Session session) {
		ProductLicense licenseByName = getLicenseByName(session, licenseName);
		KeyRange keyRange = licenseName != null ? licenseByName.getKeyRange() : null;
		Long classId = null;
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			classId = getNewBeanClassIdentifier(database, keyRange);			
			out.write("DISCRIMINATOR_VALUE=" + classId);
			
			Table tableName = (Table)Class.forName(parentBeanName).getAnnotation(Table.class);
			if ( tableName != null ){
				out.write("TABLE_NAME=" + tableName.name());
			}
			session.clear();
			session.close();
		} catch (Exception e) {			
			e.printStackTrace();
			out.write("ERROR=" + e.getMessage());
		}		
	}

	private void doGetBeans(HttpServletResponse response, String database,String baseEntity, Session session) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {			
			e1.printStackTrace();
		}
		try {					
			out.write(getBeans(database, baseEntity));			
			session.clear();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			out.write("ERROR=" + e.getMessage());
		}
	}

	private void doGetTree(HttpServletResponse response, String database,Session session) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {			
			e1.printStackTrace();
		}
		try {					
			out.write(getMenu(database));			
			session.clear();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			out.write("ERROR=" + e.getMessage());
		}
	}

	private void doGetLicenses(HttpServletResponse response, String database, Session session, Long userKey) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {			
			ArrayList userLicenses = SecurityUtil.getLicensesByUserToCreateWindow(userKey, database);
			for (Iterator iterator = userLicenses.iterator(); iterator.hasNext();) {
				DyadBaseModel pl = (DyadBaseModel) iterator.next();
				if ( iterator.hasNext() ){
					out.write((String)pl.get("name") + ";");
				} else {
					out.write((String)pl.get("name"));
				}
			}				
			session.clear();
			session.close();
		} catch (Exception e) {			
			e.printStackTrace();
			out.write("ERROR=" + e.getMessage());
		}
	}
	
	/**
	 * @author Danilo Sampaio
	 * @return
	 * @throws Exception
	 * Gera o xml que representa a árvore de produto da base de dados
	 */
	@SuppressWarnings("unchecked")
	public String getMenu( String database) throws Exception{
		DyadWindow ret = new DyadWindow();
		
		Session s = PersistenceUtil.getSession(database);
		Query qry = s.createQuery("from NavigatorEntity where id = -89999999999989"); /* Navigation */		
		List<NavigatorEntity> navigator = qry.list();
		
		if (navigator != null && navigator.size() > 0){			
			ret.setName( navigator.get(0).getName() );
			ret.setProcess(navigator.get(0).getWindow());
			ret.setId(navigator.get(0).getId());
			ret.setLicenseId(navigator.get(0).getLicenseId());
			addItems(navigator.get(0), ret);
		}
		String xmlSemAcento = Normalizer.normalize("<?xml version=\"1.0\"?>" + ret.toXmlString(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		return xmlSemAcento;
	}
	
	/**
	 * @author Danilo Sampaio
	 * @param database
	 * @return
	 * Gera o xml que representa a árvore de classes de dados
	 * @throws Exception 
	 * @throws ClassNotFoundException 
	 */
	public String getBeans( String database, String parentBean ) throws ClassNotFoundException, Exception{
		HibernateUtil util = new HibernateUtil();						
		util.getDataClasses(database, Class.forName(parentBean));
		DataClass root = util.findClassParent(util.getClassTree(), Class.forName(parentBean));
		DyadBean ret = new DyadBean();
		if ( root != null ){			
			ret.setBeanName(root.getName());
			ret.setClassId(root.getClassId());
			ret.setCompleteBeanName(root.getClassName());
			ret.setLicenseId(null);
			addChildBeans(ret, root.getChildren());
		}
		String xmlSemAcento = Normalizer.normalize("<?xml version=\"1.0\"?>" + ret.toXmlString(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		return xmlSemAcento;
	}
	
	/**
	 * @author Danilo Sampaio
	 * @param source
	 * @param destiny
	 * Adiciona recursivamente os itens filhos da classe informada.
	 */
	private void addItems(NavigatorEntity source, DyadWindow destiny) {
		for (NavigatorEntity item : source.getSubmenu()) {
			DyadWindow temp = new DyadWindow();
			temp.setName( item.getName());		
			temp.setProcess(item.getWindow());
			temp.setId(item.getId());
			temp.setLicenseId(item.getLicenseId());
			destiny.getChildren().add(temp);			
			addItems(item, temp);
		}
	}
	
	private void addChildBeans( DyadBean parent, ArrayList<DataClass> children ){
		for (Iterator iterator = children.iterator(); iterator.hasNext();) {
			DataClass dataClass = (DataClass) iterator.next();
			DyadBean temp = new DyadBean();
			temp.setBeanName(dataClass.getName());
			temp.setClassId(dataClass.getClassId());
			temp.setCompleteBeanName(dataClass.getClassName());
			temp.setLicenseId(null);
			parent.getChildren().add(temp);
			
			if ( dataClass.getChildren() != null && dataClass.getChildren().size() > 0 ){
				addChildBeans(temp, dataClass.getChildren());
			}
		}
	}
	
	private Long getNewBeanClassIdentifier( String database, KeyRange keyRange ) throws Exception{
		if ( keyRange != null ){
			return KeyGenerator.getInstance(database).generateKey(keyRange.getId());
		} else {
			KeyGenerator.getInstance(database).generateKey(KeyGenerator.KEY_RANGE_NO_LICENSE);
		}
		return null;
	}
	
	private ProductLicense getLicenseByName( Session session, String licenseName ){
		if ( licenseName != null ){
			Query createQuery = session.createQuery("from ProductLicense where name = '" + licenseName + "'");
			List productsList = createQuery.list();
			if ( productsList != null && productsList.size() > 0 ){
				ProductLicense lic = (ProductLicense) productsList.get(0);
				return lic;
			}
		}
		return null;
	}
}
