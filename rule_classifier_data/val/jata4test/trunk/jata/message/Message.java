package jata.message;
import java.lang.reflect.Constructor;

import jata.exception.*;

public abstract class Message implements ICD{
	protected Message father;
	protected String decodeFromType;
	protected String codeToType;
	
	
	public static ICD getICD(Class<?> cls) throws JataException {
		try {
			Class<?>[] para = new Class[1];
			para[0] = Message.class;
			Constructor<?> constructor = cls.getConstructor(para);
			Object[] param = new Object[1];
			param[0] = null;
			return (ICD) constructor.newInstance(param);
		} catch (Exception ex) {
			throw new JataMessageException(ex, cls.getName(), "getICD()|F|");
		}
	}
	
	public String getTypeName(){
		return this.getClass().getName();
	}
	
	protected abstract boolean Decoder (Object stream) throws Exception;
	protected abstract Object Coder() throws JataException;
	
	public void decode(Object stream) throws JataMessageException {
		try {
			 Assert.CStrBlankN(decodeFromType, "decodeFromType|V|");
			 Assert.CStrEqN(stream.getClass().getName(), decodeFromType, "stream.getClass().getName(), decodeFromType|Vs|");
			 
			 if (Assert.C(
					 Decoder(stream),"Decoder(stream)|Fr|"
					 )){
				 check();
			 }
		}catch (Exception ex){
			throw new JataMessageException(ex,this,"decode()|F|");
		}
	}
	
	public Object code() throws JataMessageException{
		try{
			Assert.CStrBlankN(codeToType, "codeToType|V|");
			Object obj = Coder();
			Assert.CN(obj, "Coder()|Fr|");
			Assert.CStrEqN(obj.getClass().getName(), codeToType, "obj.getClass().getName(), codeToType|Vs|");
			return obj;
		}catch (Exception ex){
			throw new JataMessageException(ex,this,"code()|F|");
		}
	}
	
	public Message(Message Father){
        father = Father;
        decodeFromType = null;
        codeToType = null;
    }
	public Message GetMessage()
    {
        return (Message)this;
    }
	protected boolean IsInRange() {return true;}
	protected boolean IsInList() {return true;}
	protected boolean check() throws JataMessageException {
		try{
			Assert.C(IsInRange(), "IsInRange()|Fr|");
			Assert.C(IsInList(), "IsInList()|Fr|");
			
			return true;
		}catch(Exception ex){
			throw new JataMessageException(ex,this,"check()|F|");
		}
	}
	

	public Message newOne() throws JataMessageException {
		try{
			Class<?> [] para = new Class[1];
			para[0] = Message.class;
			Constructor<?> constructor = this.getClass().getConstructor(para);
			Object [] param = new Object[1];
			param[0]=null;
			return (Message) constructor.newInstance(param);
		}catch (Exception ex){
			throw new JataMessageException(ex,this,"newOne()|F|");
		}
	}
	
	public String toString(){
		return "Msg<"+getTypeName()+">";
	}
}
