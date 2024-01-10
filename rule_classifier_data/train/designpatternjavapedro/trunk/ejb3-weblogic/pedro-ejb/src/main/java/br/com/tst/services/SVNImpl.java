package br.com.tst.services;

import java.io.InputStream;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import br.com.tst.services.entity.SvnLog;

@EJB(name = "svnservice", mappedName = "svnservice",beanInterface=SVNServices.class)
@Stateless(name="svnservice",mappedName="svnservice")
public class SVNImpl implements SVNServices {

	public SvnLog getSVNLog(Integer maxResults) {
		SvnLog log = null;
		try {
			String[] command = new String[9];
			command[0] = "cmd";
			command[1] = "/C";
			command[2] = "svn.exe";
			command[3] = "log";
			command[4] = "-v";
			command[5] = "-l";
			command[6] = maxResults.toString();
			command[7] = "https://designpatternjavapedro.googlecode.com/svn";
			command[8] = "--xml";
			InputStream in = Runtime.getRuntime().exec(command)
					.getInputStream();

			/*
			 * BufferedReader reader = new BufferedReader(new
			 * InputStreamReader(in)); StringBuilder sb = new StringBuilder();
			 * String line = null; while ((line = reader.readLine()) != null) {
			 * sb.append(line + "\n"); } System.out.println(sb);
			 */
			/* File in = new File("C:\\bea\\svn.xml"); */

			JAXBContext context = JAXBContext.newInstance(SvnLog.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			log = (SvnLog) unmarshaller.unmarshal(in);
		} catch (Exception e) {
			System.out.println("Erro ao chamar o getSVNLOG:" + e);
		}
		return log;
	}

	public static void main(String[] args) {
		SVNImpl svn = new SVNImpl();
		System.out.println(svn.getSVNLog(new Integer("1")));

	}
}
