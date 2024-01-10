package jata.example.remoteApdater;

import jata.port.*;
import jata.exception.*;
import jata.message.*;
import java.io.*;

public class PortSM extends SystemPort{
	public PortSM() throws JataException{
		super();
		addOutputType(Message.getICD(MsgSuccess.class));
		addOutputType(Message.getICD(MsgFail.class));
		addInputType(Message.getICD(MsgTryPwd.class));
	}
	@Override
	protected AdapterPackage Adapter(AdapterPackage pkg) throws Exception {
		if (pkg.streamType.equals("java.lang.String")) {
			String s = (String)pkg.stream;
			
			File file=new File("e:\\pwd.txt");
			FileReader fr=new FileReader(file);
			BufferedReader in=new BufferedReader(fr);//包装文件输入流，可整行读�?
			String line;
			line=in.readLine();
			in.close();
			fr.close();
			
			if (line==null||line.equals("")||line.equals(s))
				return new AdapterPackage("Success");
			else {
				int n=0;
				for (int i=0;i<s.length();i++){
					if (s.charAt(i) != line.charAt(i))
						return new AdapterPackage((n+100)+"");
					else
						n++;
				}
				return new AdapterPackage((n+100)+"");
			}
		}
		return null;
	}
}
