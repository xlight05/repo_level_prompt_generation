/**
 * 
 */
package se.openprocesslogger.db;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;

import se.openprocesslogger.Data;

class DataChunk{
	public static Data[] getChunk(Blob b) throws IOException, SQLException, ClassNotFoundException{
		InputStream bis = b.getBinaryStream();
		ObjectInputStream ois = new ObjectInputStream(bis);
		Object o = ois.readObject();
		bis.close();
		ois.close();
		if(!(o instanceof Data[])){ System.out.println("Error deserialize blob"); }
		
		return ((Data[])o);
	}
	
	public static Blob getBlob(Data[] chunk)throws SQLException, IOException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(chunk);
		SerialBlob sb = new SerialBlob(bos.toByteArray());
		return sb;
	}
}
