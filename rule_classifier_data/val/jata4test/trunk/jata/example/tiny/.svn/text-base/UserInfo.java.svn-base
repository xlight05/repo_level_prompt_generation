package jata.example.tiny;

import java.util.regex.*;

import jata.message.*;

public class UserInfo extends Message{
	public UserInfo(Message Father) {
		super(Father);
		decodeFromType = "java.lang.String";
	}
	public UserInfo(){
		this(null);
	}

	public String name;
	public int pwd;
	
	@Override
	protected Object Coder() {
		
		return null;
	}

	@Override
	protected boolean Decoder(Object stream) {
		String s = (String) stream;
		if (Pattern.matches("Name:\\w+?Password:\\d*", s)) {
			s = s.replace("Name:", "");
			s = s.replace("Password:", "~");
			name = s.split("~", 2)[0];
			pwd = Integer.decode(s.split("~",2)[1]);
		} else {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean IsInRange()
    {
        if (pwd > 100000)
            return true;
        else
            return false;
    }
	
	@Override
	public boolean IsInList()
    {
        if (name.startsWith("U"))
            return true;
        else
            return false;
    }
}
