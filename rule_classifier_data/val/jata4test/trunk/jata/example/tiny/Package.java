package jata.example.tiny;

import java.util.regex.Pattern;

import jata.exception.JataException;
import jata.message.Message;

public class Package extends Message {

	public Package(Message Father) {
		super(Father);
		decodeFromType = "java.lang.String";
        codeToType = "Integer";
	}
	
	public Package(){
		this(null);
	}
	
	public UserInfo info;
	public CheckCode code;

	@Override
	protected Object Coder() {
		int n = info.pwd;
		return n;
	}

	@Override
	protected boolean Decoder(Object stream) throws JataException {
		String s = (String)stream;
		if (Pattern.matches("Name:\\w+?Password:\\d*[@]\\w*", s)) {
			info = new UserInfo(this.GetMessage());
            info.decode((Object)s.split("@",2)[0]);
            code = new CheckCode(this.GetMessage());
            code.decode((Object)s.split("@",2)[1]);
            return true;
		}
		return false;
	}

}
