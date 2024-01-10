package org.rsbot.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.JarURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import com.sun.org.apache.bcel.internal.Constants;
import com.sun.org.apache.bcel.internal.classfile.ClassParser;
import com.sun.org.apache.bcel.internal.classfile.ConstantClass;
import com.sun.org.apache.bcel.internal.classfile.ConstantUtf8;
import com.sun.org.apache.bcel.internal.classfile.Field;
import com.sun.org.apache.bcel.internal.classfile.Method;
import com.sun.org.apache.bcel.internal.generic.ALOAD;
import com.sun.org.apache.bcel.internal.generic.ASTORE;
import com.sun.org.apache.bcel.internal.generic.ArrayType;
import com.sun.org.apache.bcel.internal.generic.BasicType;
import com.sun.org.apache.bcel.internal.generic.ClassGen;
import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.D2F;
import com.sun.org.apache.bcel.internal.generic.D2I;
import com.sun.org.apache.bcel.internal.generic.D2L;
import com.sun.org.apache.bcel.internal.generic.DUP;
import com.sun.org.apache.bcel.internal.generic.F2D;
import com.sun.org.apache.bcel.internal.generic.F2I;
import com.sun.org.apache.bcel.internal.generic.F2L;
import com.sun.org.apache.bcel.internal.generic.FieldGen;
import com.sun.org.apache.bcel.internal.generic.FieldInstruction;
import com.sun.org.apache.bcel.internal.generic.GOTO;
import com.sun.org.apache.bcel.internal.generic.I2B;
import com.sun.org.apache.bcel.internal.generic.I2C;
import com.sun.org.apache.bcel.internal.generic.I2D;
import com.sun.org.apache.bcel.internal.generic.I2F;
import com.sun.org.apache.bcel.internal.generic.I2L;
import com.sun.org.apache.bcel.internal.generic.I2S;
import com.sun.org.apache.bcel.internal.generic.ILOAD;
import com.sun.org.apache.bcel.internal.generic.INVOKESPECIAL;
import com.sun.org.apache.bcel.internal.generic.IRETURN;
import com.sun.org.apache.bcel.internal.generic.Instruction;
import com.sun.org.apache.bcel.internal.generic.InstructionFactory;
import com.sun.org.apache.bcel.internal.generic.InstructionHandle;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.bcel.internal.generic.InvokeInstruction;
import com.sun.org.apache.bcel.internal.generic.L2D;
import com.sun.org.apache.bcel.internal.generic.L2F;
import com.sun.org.apache.bcel.internal.generic.L2I;
import com.sun.org.apache.bcel.internal.generic.LDC;
import com.sun.org.apache.bcel.internal.generic.MethodGen;
import com.sun.org.apache.bcel.internal.generic.ObjectType;
import com.sun.org.apache.bcel.internal.generic.POP;
import com.sun.org.apache.bcel.internal.generic.PUSH;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.sun.org.apache.bcel.internal.generic.ReturnInstruction;
import com.sun.org.apache.bcel.internal.generic.SIPUSH;
import com.sun.org.apache.bcel.internal.generic.Type;

class Stream
{
	byte[] data;
	int off;

	Stream(byte[] data)
	{
		this.data = data;
	}

	int readByte()
	{
		return (0xFF & data[off++]);
	}

	int readWord()
	{
		return 
		(
				( (0xFF & data[off++]) << 8	) +
				( (0xFF & data[off++])		)
		);
	}

	String readString()
	{
		StringBuilder builder = new StringBuilder();
		while(data[off] != 0)
		{
			builder.append((char) data[off++]);
		}
		off++;
		return builder.toString();
	}
}

class ClassData
{
	String official_name;
	String injected_name;
}

class FieldData
{
	String official_class_name;
	String official_field_name;
	String injected_field_name;
	String injected_field_signature;
}

class StaticFieldData
{
	String official_class_name;
	String official_field_name;
	String injected_field_name;
	String injected_field_signature;
}

class MasterXYData
{
	String class_name;
	String method_name;
	String method_signature;
	int append_index;
	int aload;
	int iload_x;
	int iload_y;
}

class ServerMessageListenerData
{
	String class_name;
	String method_name;
	String method_signature;
	int append_index;
	int aload;
}

class RSObjectsData
{
	String method_name;
	String method_signature;
	String[] object_class_names;
}

class RenderData
{
	int append_index;
	
	String class_name;
	String method_name;
	String method_signature;

	String render_class_name;
	String render_field_name;
	String render_field_signature;

	String renderData_class_name;
	String renderData_field_name;
	String renderData_field_signature;
}

class TileHeightData
{
	String method_name;
	String method_signature;
}

class CharData
{
	byte[] c;
	byte[] i;
}

class HookData
{
	ArrayList<ClassData> classes = new ArrayList<ClassData>();
	ArrayList<FieldData> fields = new ArrayList<FieldData>();
	ArrayList<StaticFieldData> staticFields = new ArrayList<StaticFieldData>();

	MasterXYData masterXY = new MasterXYData();
	ServerMessageListenerData serverMessageListener = new ServerMessageListenerData();
	RSObjectsData rsObjects = new RSObjectsData();
	RenderData render = new RenderData();
	TileHeightData tileHeight = new TileHeightData();
	CharData charData = new CharData();
	
	int version;

	HookData(byte[] data)
	{
		//Initialize stream
		Stream s_data = new Stream(data);
		
		//Read version
		version = s_data.readWord();

		//Read classes
		int classes_length = s_data.readWord();
		for(int i = 0; i < classes_length; i++)
		{
			ClassData c = new ClassData();
			c.injected_name = s_data.readString();
			c.official_name = s_data.readString();

			classes.add(c);
		}

		//Read fields
		int fields_length = s_data.readWord();
		for(int i = 0; i < fields_length; i++)
		{
			FieldData f = new FieldData();
			f.injected_field_name = s_data.readString();
			f.injected_field_signature = s_data.readString();
			f.official_class_name = s_data.readString();
			f.official_field_name = s_data.readString();

			fields.add(f);
		}

		//Read Static fields
		int static_fields_length = s_data.readWord();
		for(int i = 0; i < static_fields_length; i++)
		{
			StaticFieldData f = new StaticFieldData();
			f.injected_field_name = s_data.readString();
			f.injected_field_signature = s_data.readString();
			f.official_class_name = s_data.readString();
			f.official_field_name = s_data.readString();

			staticFields.add(f);
		}

		//Read master x/y info
		masterXY.class_name = s_data.readString();
		masterXY.method_name = s_data.readString();
		masterXY.method_signature = s_data.readString();
		masterXY.append_index = s_data.readWord();
		masterXY.aload = s_data.readByte();
		masterXY.iload_x = s_data.readByte();
		masterXY.iload_y = s_data.readByte();

		//Read server message listener info
		serverMessageListener.class_name = s_data.readString();
		serverMessageListener.method_name = s_data.readString();
		serverMessageListener.method_signature = s_data.readString();
		serverMessageListener.append_index = s_data.readWord();
		serverMessageListener.aload = s_data.readByte();

		//Read RSObjects info
		rsObjects.method_name = s_data.readString();
		rsObjects.method_signature = s_data.readString();
		rsObjects.object_class_names = new String[s_data.readByte()];
		for(int i = 0; i < rsObjects.object_class_names.length; i++)
			rsObjects.object_class_names[i] = s_data.readString();

		//Read update render data info
		render.append_index = s_data.readWord();
		
		render.class_name = s_data.readString();
		render.method_name = s_data.readString();
		render.method_signature = s_data.readString();
		
		render.render_class_name = s_data.readString();
		render.render_field_name = s_data.readString();
		render.render_field_signature = s_data.readString();
		
		render.renderData_class_name = s_data.readString();
		render.renderData_field_name = s_data.readString();
		render.renderData_field_signature = s_data.readString();
		
		//Read tile height info
		tileHeight.method_name = s_data.readString();
		tileHeight.method_signature = s_data.readString();
		
		//Read char data
		int c_length = s_data.readByte();
		charData.c = new byte[c_length];
		charData.c = Arrays.copyOfRange(s_data.data, s_data.off, s_data.off + c_length);
		s_data.off += c_length;
		
		int i_length = s_data.readByte();
		charData.i = new byte[i_length];
		charData.i = Arrays.copyOfRange(s_data.data, s_data.off, s_data.off + i_length);
		s_data.off += i_length;
	}	
}

public class Injector {	
	private Logger log = Logger.getLogger(Injector.class.getName());
	private static String ACCESSOR_PACKAGE = "org.rsbot.accessors.";
	private static boolean loadLocal = false;
	
	private ClassGen[] loaded;
	private HookData hd = null;

	private int world;
	
	public Injector()
	{
		if(loadLocal)
		{
			try
			{
				hd = new HookData(download(new File("info.dat").toURI().toURL().toString()));
			}catch(Exception e){ e.printStackTrace(); }
		}
		else
			hd = new HookData(download(GlobalConfiguration.Paths.URLs.UPDATE));

		world = 1 + new Random().nextInt(169);
	}
	
	public String generateTargetName()
	{
		String s = "";
		for(byte b : hd.charData.i)
			s += (char) hd.charData.c[b];
		
		return s;
	}
	
	private int getCachedVersion()
	{
		try {
			File versionFile = new File(GlobalConfiguration.Paths.getVersionCache());
			BufferedReader reader = new BufferedReader(new FileReader(versionFile));
			int version = Integer.parseInt(reader.readLine());
			reader.close();
			return version;
		}catch(Exception e)
		{
			return 0;
		}
	}
	
	private JarFile getJar(boolean loader)
	{
		while(true)
		{
			try
			{
				String s = "jar:http://world" + world + "." + generateTargetName() + ".com/";
				if(loader)
					s += "loader.jar!/";
				else
					s += generateTargetName() + ".jar!/";
				
				URL url = new URL(s);
				return ((JarURLConnection) url.openConnection()).getJarFile();
			}
			catch(Exception ignored) { }
		}
	}

	public HashMap<String, byte[]> getClasses()
	{
		try {
			ArrayList<ClassGen> classlist = new ArrayList<ClassGen>();
			
			if (hd.version != getCachedVersion())
			{
				log.info("Downloading loader");
				JarFile loaderJar = getJar(true);
				log.info("Downloading client");
				JarFile clientJar = getJar(false);

				Enumeration<JarEntry> entries = clientJar.entries();
				while(entries.hasMoreElements())
				{
					JarEntry entry = entries.nextElement();
					String name = entry.getName();
					if (name.endsWith(".class"))
					{
						ClassParser cp = new ClassParser(clientJar.getInputStream(entry), name);
						classlist.add(new ClassGen(cp.parse()));
					}
				}

				entries = loaderJar.entries();
				ArrayList<ClassGen> loaderclasslist = new ArrayList<ClassGen>();
				while(entries.hasMoreElements())
				{
					JarEntry entry = entries.nextElement();
					String name = entry.getName();
					if(name.endsWith(".class"))
					{
						loaderclasslist.add(new ClassGen(new ClassParser(loaderJar.getInputStream(entry), name).parse()));
					}
				}
				
				log.info("Parsing loader");
				ClassGen[] loader = new ClassGen[loaderclasslist.size()];
				loaderclasslist.toArray(loader);
				String[] classNames = new String[5];
				for(ClassGen cg : loader)
				{
					if(!cg.getClassName().equals("loader")) continue;

					for(Method m : cg.getMethods())
					{
						if(!m.getName().equals("run")) continue;
						InstructionSearcher s = new InstructionSearcher(cg, m);
						s.nextLDC("client");
						for(int i = 0; i < 5; i++)
							classNames[i] = (String) ((LDC) s.previous("LDC")).getValue(cg.getConstantPool());
						break;
					}
				}

				for(ClassGen cg : loader)
				{
					for(String name : classNames)
					{
						if(!cg.getClassName().equals(name)) continue;

						ClassGen ccg = null;
						for(Iterator<ClassGen> it = classlist.iterator(); it.hasNext(); ccg = it.next() )
						{
							if(ccg == null) continue;

							if(ccg.getClassName().equals(name))
							{
								classlist.remove(ccg);
								break;
							}
						}
						classlist.add(cg);
					}
				}
				
				int size = classlist.size();
				loaded = new ClassGen[size];
				classlist.toArray(loaded);
				
				//Check version
				if (getRSBuild() != hd.version) {
					String message = GlobalConfiguration.NAME + " is currently outdated, please wait patiently for a new version.";
					log.severe(message);
					JOptionPane.showMessageDialog(null, message, "Outdated", JOptionPane.WARNING_MESSAGE);
					return new HashMap<String, byte[]>();
				}
				
				cacheClient();
			}
			else
			{
				log.info("Loading client #" + hd.version);
				
				try {
					JarFile cachedJar = new JarFile(GlobalConfiguration.Paths.getClientCache());

					Enumeration<JarEntry> entries = cachedJar.entries();
					while(entries.hasMoreElements())
					{
						JarEntry entry = entries.nextElement();
						String name = entry.getName();
						if (name.endsWith(".class"))
						{
							ClassParser cp = new ClassParser(cachedJar.getInputStream(entry), name);
							classlist.add(new ClassGen(cp.parse()));
						}
					}

					int size = classlist.size();
					loaded = new ClassGen[size];
					classlist.toArray(loaded);
				}catch(Exception e)
				{
					File versionFile = new File(GlobalConfiguration.Paths.getVersionCache());
					if(versionFile.delete())
					{
						log.info("Error loading client, redownloading...");
						return getClasses();
					} else {
						log.severe("Error loading cached client.");
						return new HashMap<String, byte[]>();
					}
				}
			}
			
			log.info("Injecting client #" + hd.version);

			//Interface and TileData ClassGen
			ClassGen cgInterface = null;
			ClassGen cgTileData = null;

			//Inject interfaces
			ClassData[] classes = hd.classes.toArray(new ClassData[hd.classes.size()]);
			for(ClassGen cg : loaded)
			{
				for(ClassData cd : classes)
				{
					if(cg.getClassName().equals(cd.official_name))
					{
						if(cd.injected_name.equals("RSInterface"))
							cgInterface = cg;
						else if(cd.injected_name.equals("TileData"))
							cgTileData = cg;

						cg.addInterface(ACCESSOR_PACKAGE + cd.injected_name);
						break;
					}
				}
			}

			//Inject masterx/y fields
			cgInterface.addField(new FieldGen(0, Type.INT, "masterX", cgInterface.getConstantPool()).getField());
			cgInterface.addField(new FieldGen(0, Type.INT, "masterY", cgInterface.getConstantPool()).getField());

			//Inject fields
			FieldData[] fields = hd.fields.toArray(new FieldData[hd.fields.size()]);
			for(ClassGen cg : loaded)
			{
				for(FieldData fd : fields)
				{
					if(cg.getClassName().equals(fd.official_class_name))
					{
						injectGetter(cg, Type.getType(fd.injected_field_signature), 
								fd.injected_field_name, fd.official_field_name);
					}
				}
			}

			//Inject static fields
			StaticFieldData[] staticFields = hd.staticFields.toArray(new StaticFieldData[hd.staticFields.size()]);
			ClassGen client = findClass("client");
			String[] username = new String[2];
			for(StaticFieldData fd : staticFields)
			{
				if(fd.injected_field_name.equals("getCurrentUsername"))
					username = new String[]{ fd.official_class_name, fd.official_field_name };
				injectGetter(client, Type.getType(fd.injected_field_signature), 
							fd.injected_field_name, fd.official_class_name + "." + fd.official_field_name);
			}

			//Inject master x/y
			ClassGen c_masterxy = findClass(hd.masterXY.class_name);
			Method m_masterxy = c_masterxy.containsMethod(hd.masterXY.method_name, hd.masterXY.method_signature);

			InstructionFactory fac = new InstructionFactory(c_masterxy, c_masterxy.getConstantPool());
			MethodGen mgn = new MethodGen(m_masterxy, c_masterxy.getClassName(), c_masterxy.getConstantPool());
			InstructionList il = mgn.getInstructionList();
			InstructionHandle[] ih = il.getInstructionHandles();

			InstructionHandle ih_append = ih[hd.masterXY.append_index];
			ih_append = il.append(ih_append, new ALOAD(hd.masterXY.aload));
			ih_append = il.append(ih_append, new ILOAD(hd.masterXY.iload_x));
			ih_append = il.append(ih_append, fac.createPutField(cgInterface.getClassName(), "masterX", Type.INT));
			ih_append = il.append(ih_append, new ALOAD(hd.masterXY.aload));
			ih_append = il.append(ih_append, new ILOAD(hd.masterXY.iload_y));
			il.append(ih_append, fac.createPutField(cgInterface.getClassName(), "masterY", Type.INT));

			mgn.setInstructionList(il);
			mgn.setMaxLocals();
			mgn.setMaxStack();
			mgn.update();
			c_masterxy.replaceMethod(m_masterxy, mgn.getMethod());

			//Inject server message listener
			ClassGen c_sml = findClass(hd.serverMessageListener.class_name);
			Method m_sml = c_sml.containsMethod(hd.serverMessageListener.method_name, hd.serverMessageListener.method_signature);
			fac = new InstructionFactory(c_sml, c_sml.getConstantPool());
			mgn = new MethodGen(m_sml, c_sml.getClassName(), c_sml.getConstantPool());
			il = mgn.getInstructionList();
			ih = il.getInstructionHandles();

			ih_append = ih[hd.serverMessageListener.append_index];
			ih_append = il.append(ih_append, fac.createGetStatic("client", "callback", 
					Type.getType(org.rsbot.accessors.Callback.class)));
			ih_append = il.append(ih_append, new ALOAD(hd.serverMessageListener.aload));
			il.append(ih_append, fac.createInvoke(ACCESSOR_PACKAGE + "Callback",
							"notifyServerMessage", Type.VOID, new Type[] { Type.STRING }, 
							Constants.INVOKEINTERFACE));

			mgn.setInstructionList(il);
			mgn.setMaxLocals();
			mgn.setMaxStack();
			mgn.update();
			c_sml.replaceMethod(m_sml, mgn.getMethod());

			//RSObjects
			for(String RSObject : hd.rsObjects.object_class_names)
			{
				ClassGen rso = findClass(RSObject);
				Method rso_id = rso.containsMethod(hd.rsObjects.method_name, hd.rsObjects.method_signature);
				QIS s = new QIS(rso, rso_id);
				s.gotoEnd();

				if(!(s.previous(ReturnInstruction.class) instanceof IRETURN))
					continue;

				InstructionHandle[][] arguments = s.getArgumentInstructions();
				if(arguments.length != 1)
					continue;

				//Create method, since we have different types of getID we have to reconstruct it in the client
				//and not using the bot to figure it out!
				il = new InstructionList();
				for(InstructionHandle ih1 : arguments[0])
					il.append(ih1.getInstruction());
				il.append(s.current());

				MethodGen mg_getid = new MethodGen(
						Constants.ACC_PUBLIC | Constants.ACC_FINAL, // access_flags
						Type.INT, // return_type
						null, // arguement_types
						null, // arguement_names
						"getID", // method_name
						rso.getClassName(), // class_name;
						il, // instruction_list;
						rso.getConstantPool() // constant_pool_gen
				);
				mg_getid.stripAttributes(true);
				mg_getid.setMaxLocals();
				mg_getid.setMaxStack();

				rso.addMethod(mg_getid.getMethod());
			}

			//Render data
			ClassGen c_rd = findClass(hd.render.class_name);
			Method m_rd = c_rd.containsMethod(hd.render.method_name, hd.render.method_signature);
			fac = new InstructionFactory(c_rd, c_rd.getConstantPool());
			mgn = new MethodGen(m_rd, c_rd.getClassName(), c_rd.getConstantPool());
			il = mgn.getInstructionList();
			ih = il.getInstructionHandles();

			ih_append = ih[hd.render.append_index];
			ih_append = il.append(ih_append, fac.createGetStatic("client", "callback", 
					Type.getType(org.rsbot.accessors.Callback.class)));
			ih_append = il.append(ih_append, fac.createGetStatic(hd.render.render_class_name, 
					hd.render.render_field_name, Type.getType(hd.render.render_field_signature)));
			ih_append = il.append(ih_append, fac.createGetStatic(hd.render.renderData_class_name, 
					hd.render.renderData_field_name, Type.getType(hd.render.renderData_field_signature)));
			il.append(ih_append, fac.createInvoke(ACCESSOR_PACKAGE + "Callback", "updateRenderInfo",
					Type.VOID, new Type[] { Type.getType(org.rsbot.accessors.Render.class), 
					Type.getType(org.rsbot.accessors.RenderData.class) }, Constants.INVOKEINTERFACE));

			mgn.setInstructionList(il);
			mgn.setMaxLocals();
			mgn.setMaxStack();
			mgn.update();
			c_rd.replaceMethod(m_rd, mgn.getMethod());

			//Tile height			
			il = new InstructionList();
			fac = new InstructionFactory(cgTileData, cgTileData.getConstantPool());

			il.append(new ALOAD(0));
			il.append(new ILOAD(1));
			il.append(new ILOAD(2));
			il.append(fac.createInvoke(
					cgTileData.getClassName(), 
					hd.tileHeight.method_name,
					Type.getReturnType(hd.tileHeight.method_signature), 
					Type.getArgumentTypes(hd.tileHeight.method_signature), 
					Constants.INVOKEVIRTUAL));
			il.append(new IRETURN());

			MethodGen mg = new MethodGen(
					Constants.ACC_PUBLIC | Constants.ACC_FINAL, // access_flags
					Type.INT, // return_type
					new Type[] { Type.INT, Type.INT}, // arguement_types
					new String[]{ "x", "z"}, // arguement_names
					"getHeight", // method_name
					cgTileData.getClassName(), // class_name;
					il, // instruction_list;
					cgTileData.getConstantPool() // constant_pool_gen
			);
			mg.setMaxLocals();
			mg.setMaxStack();

			cgTileData.addMethod(mg.getMethod());

			//Hack mouse/keyboard/canvas/signlink
			hackMouse();
			//hackMouseWheel();
			hackKeyboard();
			hackCanvas();
			hackSignUID(username);

			//Insert callback
			insertCallback();
			
			//Return the classes
			HashMap<String, byte[]> ret = new HashMap<String, byte[]>();
			for(ClassGen cg : loaded)
			{
				ret.put(cg.getClassName(), cg.getJavaClass().getBytes());
			}
			
			return ret;
		}catch(Exception e){e.printStackTrace();}

		return null;
	}
	
	private int getRSBuild() throws Exception {
		ClassGen client = findClass("client");
		Method main = client.containsMethod("main", "([Ljava/lang/String;)V");
		MethodGen mg = new MethodGen(main, "client", client.getConstantPool());
		Instruction instructions[] = mg.getInstructionList().getInstructions();
		boolean foundWidth = false, foundHeight = false;
		for (int i = instructions.length - 1; i >= 0; i--) {
			Instruction instruction = instructions[i];
			short opcode = instruction.getOpcode();
			if (opcode == 17) {
				SIPUSH sipush = (SIPUSH) instruction;
				short value = sipush.getValue().shortValue();
				if (value != 1024 && value != 768 && value > 400 && value < 1000) {
					return value;
				}
				if(value == 1024 || value == 768){
					if(value == 1024){
						if(foundWidth) return value;
						else foundWidth = true;
					}else{
						if(foundHeight) return value;
						else foundHeight = true;
					}
				}
			}
		}
		return -1;
	}
	
	private void cacheClient()
	{
		try {
			File file = new File(GlobalConfiguration.Paths.getClientCache());
			FileOutputStream stream = new FileOutputStream(file);
			JarOutputStream out = new JarOutputStream(stream);

			for (ClassGen cg : loaded)
			{
				out.putNextEntry(new JarEntry(cg.getClassName() + ".class"));
				out.write(cg.getJavaClass().getBytes());
			}

			out.close();
			stream.close();

			FileWriter writer = new FileWriter(GlobalConfiguration.Paths.getVersionCache());
			writer.write(Integer.toString(hd.version));
			writer.close();
		}catch(IOException ignored){}
	}
	
	private void insertCallback()
	{
		ClassGen client = findClass("client");
		
		//Insert field callback
		Field f = new FieldGen(Constants.ACC_PUBLIC | Constants.ACC_STATIC,
				Type.getType(org.rsbot.accessors.Callback.class), "callback", client.getConstantPool()).getField();
		client.addField(f);
		
		//Insert getCallback
		{
			InstructionList il = new InstructionList();
			InstructionFactory factory = new InstructionFactory(client, client.getConstantPool());
			il.append(factory.createGetStatic("client", f.getName(), f.getType()));
			il.append(InstructionFactory.createReturn(f.getType()));

			MethodGen mg = new MethodGen(Constants.ACC_PUBLIC | Constants.ACC_FINAL, // access_flags
					f.getType(), // return_type
					null, // arguement_types
					null, // arguement_names
					"getCallback", // method_name
					"client", // class_name;
					il, // instruction_list;
					client.getConstantPool() // constant_pool_gen
			);
			mg.setMaxLocals();
			mg.setMaxStack();

			client.addMethod(mg.getMethod());
		}
		
		//Insert setCallback
		{
			InstructionList il = new InstructionList();
			InstructionFactory factory = new InstructionFactory(client, client.getConstantPool());
			il.append(new ALOAD(1));
			il.append(factory.createPutStatic("client", f.getName(), f.getType()));
			il.append(new RETURN());

			MethodGen mg = new MethodGen(Constants.ACC_PUBLIC | Constants.ACC_FINAL, // access_flags
					Type.VOID, // return_type
					new Type[]{f.getType()}, // arguement_types
					null, // arguement_names
					"setCallback", // method_name
					"client", // class_name;
					il, // instruction_list;
					client.getConstantPool() // constant_pool_gen
			);
			mg.setMaxLocals();
			mg.setMaxStack();

			client.addMethod(mg.getMethod());
		}
	}

	private void hackCanvas()
	{
		for(ClassGen cg : loaded)
		{
			if(cg.getSuperclassName().equals("java.awt.Canvas")){
				ConstantPoolGen cpg = cg.getConstantPool();
				cpg.setConstant(cg.getSuperclassNameIndex(), new ConstantClass(cpg.addUtf8("org/rsbot/bot/input/CanvasWrapper")));
			}
		}
	}
	
	private void hackMouse(){
		for(ClassGen cg : loaded){
			String interfaces[] = cg.getInterfaceNames();
			boolean foundMouseListener = false;
			
			for(String iface : interfaces){
				if(iface.endsWith("MouseListener"))
					foundMouseListener = true;	
				else if(iface.endsWith("MouseWheelListener")) {
					setSuperclassName(findClass(cg.getSuperclassName()), "org/rsbot/bot/input/Mouse");
					break;
				}
			}
			
			if(foundMouseListener)
			{
				Method methods[] = cg.getMethods();
				for(Method m : methods) {
					String name = m.getName();
					if(name.startsWith("mouse") || name.startsWith("focus"))
						cg.getConstantPool().setConstant(m.getNameIndex(), new ConstantUtf8("_" + name));
				}
			}
		}
	}

	private void hackKeyboard(){
		for(ClassGen cg : loaded)
		{
			String interfaces[] = cg.getInterfaceNames();
			for(String iface : interfaces)
			{
				if(iface.endsWith("KeyListener")) 
				{
					setSuperclassName(findClass(cg.getSuperclassName()), "org/rsbot/bot/input/Keyboard");
					
					Method methods[] = cg.getMethods();
					for(Method m : methods) {
						String name = m.getName();
						if(name.startsWith("key") || name.startsWith("focus"))
							cg.getConstantPool().setConstant(m.getNameIndex(), new ConstantUtf8("_" + name));
					}
					
					return;
				}
			}
		}
	}
	
	private boolean hackSignUID(String[] username){
		ClassGen CacheIOMaster = null;
		ClassGen CacheIO = null;
		
		for(ClassGen cg : loaded)
		{
			ConstantPoolGen cpg = cg.getConstantPool();
			if(cpg.lookupUtf8(" in file ") != -1) 
				CacheIOMaster = cg;
			else if(cpg.lookupString("Warning! fileondisk ") != -1) 
				CacheIO = cg;
		}
		
		if(CacheIOMaster == null || CacheIO == null) 
			return false;

		Field fCacheIO = null;
		for(Field f : CacheIOMaster.getFields()){
			if(!f.isStatic() && f.getType().toString().equals(CacheIO.getClassName()))
				fCacheIO = f;
		}
		
		if(fCacheIO == null) 
			return false;

		// patch part 1
		boolean breakLoop = false;
		for(ClassGen cg : loaded){
			if(breakLoop) break;
			for(Method m : cg.getMethods())
			{
				if(breakLoop) break;
				if(!m.isAbstract() && !m.isNative())
				{
					InstructionSearcher s = new InstructionSearcher(cg, m);
					
					while(s.next("athrow") != null)
					{
						int T_INDEX = s.index;
						if(!(s.previous() instanceof INVOKESPECIAL)){ s.index = T_INDEX; continue; }
						if(!((InvokeInstruction) s.current()).getClassName(cg.getConstantPool()).equals("java.io.IOException")) { s.index = T_INDEX; continue; }

						if(s.previous("invokevirtual") == null || s.previous("invokevirtual") == null){ s.index = T_INDEX; continue; }
						InvokeInstruction iv = (InvokeInstruction) s.current();
						
						if(iv.getClassName(cg.getConstantPool()).equals(CacheIOMaster.getClassName()))
						{
							FieldInstruction fi = s.previousFieldInstruction();
							InstructionFactory fac = new InstructionFactory(cg, cg.getConstantPool());
							MethodGen mgn = new MethodGen(m, cg.getClassName(), cg.getConstantPool());
							InstructionList il = mgn.getInstructionList();
							il.insert(il.insert(
									il.getInstructionHandles()[s.index()],
									fac.createInvoke(CacheIOMaster.getClassName(), "fixFile", Type.VOID, Type.NO_ARGS, Constants.INVOKEVIRTUAL)),
									fac.createGetStatic(fi.getClassName(cg.getConstantPool()), fi.getFieldName(cg.getConstantPool()), fi.getFieldType(cg.getConstantPool())));
							mgn.setMaxLocals();
							mgn.setMaxStack();
							mgn.update();
							cg.replaceMethod(m, mgn.getMethod());
							breakLoop = true; //Break method and class loop aswell
							break; //Don't continue, because it takes useless time
						}
						
						s.index = T_INDEX;
					}
				}
			}
		}

		//patch part 2
		InstructionList il2 = new InstructionList();
		InstructionFactory fac2 = new InstructionFactory(CacheIOMaster, CacheIOMaster.getConstantPool());
		MethodGen mgff = 	new MethodGen(Constants.ACC_PUBLIC, // access flags
				Type.VOID,               // return type
				Type.NO_ARGS, // argument types
				new String[] {}, // arg names
				"fixFile", CacheIOMaster.getClassName(),    // method, class
				il2, CacheIOMaster.getConstantPool());
		il2.append(new ALOAD(0));
		il2.append(fac2.createGetField(CacheIOMaster.getClassName(), fCacheIO.getName(), fCacheIO.getType()));
		il2.append(fac2.createNew("java.lang.String"));
		il2.append(new DUP());
		il2.append(fac2.createGetStatic(username[0], username[1], Type.STRING));
		il2.append(fac2.createInvoke(Type.STRING.getClassName(), "getBytes", new ArrayType(Type.BYTE, 1), Type.NO_ARGS, Constants.INVOKEVIRTUAL));
		il2.append(fac2.createInvoke("java.lang.String", "<init>", Type.VOID, new Type[] {new ArrayType(Type.BYTE, 1)}, Constants.INVOKESPECIAL));
		il2.append(fac2.createInvoke(CacheIO.getClassName(), "FixFile", Type.VOID, new Type[] {Type.STRING}, Constants.INVOKEVIRTUAL));
		il2.append(new RETURN());

		mgff.setMaxLocals();
		mgff.setMaxStack();
		CacheIOMaster.addMethod(mgff.getMethod());


		//patch part 3
		createMethodFixFile(CacheIO);
		
		return true;
	}
	
	public static void createMethodFixFile(ClassGen CacheIO){
		Field cFile = null;
		Field cRAF = null;
		for(Field f : CacheIO.getFields()){
			if(f.getType().toString().equals("java.io.File")) cFile = f;
			if(f.getType().toString().equals("java.io.RandomAccessFile")) cRAF = f;
		}
		if(cFile == null || cRAF == null) return;

		InstructionList il = new InstructionList();
		InstructionFactory fac = new InstructionFactory(CacheIO, CacheIO.getConstantPool());
		MethodGen ff = 	new MethodGen(Constants.ACC_PUBLIC, // access flags
				Type.VOID,               // return type
				new Type[] {Type.STRING}, // argument types
				new String[] {"username"}, // arg names
				"FixFile", CacheIO.getClassName(),    // method, class
				il, CacheIO.getConstantPool());
		//add all the instructions
		InstructionHandle instr1 = il.append(new ALOAD(0));
		il.append(fac.createNew("java.io.File"));
		il.append(new DUP());
		il.append(new ALOAD(0));
		il.append(fac.createGetField(CacheIO.getClassName(), cFile.getName(), cFile.getType()));
		il.append(fac.createInvoke("java.io.File", "getParent", Type.STRING, Type.NO_ARGS, Constants.INVOKEVIRTUAL));
		il.append(fac.createNew("java.lang.StringBuilder"));
		il.append(new DUP());
		il.append(new ALOAD(1));
		il.append(fac.createInvoke("java.lang.String", "valueOf", Type.STRING, new Type[] {Type.OBJECT}, Constants.INVOKESTATIC));
		il.append(fac.createInvoke("java.lang.StringBuilder", "<init>", Type.VOID, new Type[] {Type.STRING}, Constants.INVOKESPECIAL));
		il.append(new PUSH(CacheIO.getConstantPool(), ".dat"));
		il.append(fac.createInvoke("java.lang.StringBuilder", "append", Type.getType(StringBuilder.class), new Type[] {Type.STRING}, Constants.INVOKEVIRTUAL));
		il.append(fac.createInvoke("java.lang.StringBuilder", "toString", Type.STRING, Type.NO_ARGS, Constants.INVOKEVIRTUAL));
		il.append(fac.createInvoke("java.io.File", "<init>", Type.VOID, new Type[] {Type.STRING, Type.STRING}, Constants.INVOKESPECIAL));
		il.append(fac.createPutField(CacheIO.getClassName(), cFile.getName(), cFile.getType()));
		il.append(new ALOAD(0));
		il.append(fac.createGetField(CacheIO.getClassName(), cFile.getName(), cFile.getType()));
		il.append(fac.createInvoke("java.io.File", "createNewFile", Type.BOOLEAN, Type.NO_ARGS, Constants.INVOKEVIRTUAL));
		il.append(new POP());
		il.append(new ALOAD(0));
		il.append(fac.createNew("java.io.RandomAccessFile"));
		il.append(new DUP());
		il.append(new ALOAD(0));
		il.append(fac.createGetField(CacheIO.getClassName(), cFile.getName(), cFile.getType()));
		il.append(new PUSH(CacheIO.getConstantPool(), "rw"));
		il.append(fac.createInvoke("java.io.RandomAccessFile", "<init>", Type.VOID, new Type[]{Type.getType(File.class), Type.STRING}, Constants.INVOKESPECIAL));
		InstructionHandle instr2 = il.append(fac.createPutField(CacheIO.getClassName(), cRAF.getName(), cRAF.getType()));
		InstructionHandle instr3 = il.append(new ASTORE(2));
		il.append(fac.createGetStatic("java.lang.System", "out", Type.getType(PrintStream.class)));
		il.append(new PUSH(CacheIO.getConstantPool(), "###############\r\n# !! ERROR !! #\r\n###############"));
		il.append(fac.createInvoke("java.io.PrintStream", "println", Type.VOID, new Type[] {Type.STRING}, Constants.INVOKEVIRTUAL));
		il.append(new ALOAD(2));
		il.append(fac.createInvoke("java.io.IOException", "printStackTrace", Type.VOID, Type.NO_ARGS, Constants.INVOKEVIRTUAL));
		InstructionHandle instr0 = il.append(new RETURN());
		il.append(instr2, new GOTO(instr0));
		ff.addExceptionHandler(instr1, instr2, instr3, new ObjectType("java.io.IOException"));
		ff.setMaxLocals();
		ff.setMaxStack();
		CacheIO.addMethod(ff.getMethod());
	}
	
	public ClassGen findClass(String className) {
		for (ClassGen clazz : loaded){
			if (clazz.getClassName().equals(className))
				return clazz;
		}
		
		for(ClassData cd : hd.classes)
		{
			if(cd.injected_name.equals(className))
				return findClass(cd.official_name);
		}
		
		return null;
	}

	private byte[] download(String address)
	{
		ByteArrayOutputStream out = null;
		InputStream  in = null;
		try {
			URL url = new URL(address);
			URLConnection urlc = url.openConnection();
			urlc.setConnectTimeout(1000);
			urlc.setReadTimeout(1000);

			out = new ByteArrayOutputStream();
			in = urlc.getInputStream();
			byte[] buffer = new byte[1024];
			int numRead;
			while ((numRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, numRead);
			}
		}catch (SocketTimeoutException ste){
			//Socket timed out, so the server is down.
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException ignored) { }
		}
		if(out != null)
			return out.toByteArray();

		return null;
	}
	
	private void setSuperclassName(ClassGen cg, String name)
	{
		ConstantPoolGen cpg = cg.getConstantPool();
		cpg.setConstant(cg.getSuperclassNameIndex(), new ConstantClass(cpg.addUtf8(name)));
	}

	private void injectGetter(ClassGen cg, Type castType, String methodName, String fieldName)
	{
		if(fieldName.contains("."))
		{
			String[] parts = fieldName.split("\\.");
			if(parts.length > 2)
				throw new RuntimeException("Argument 'fieldName': " + fieldName + " contains more then one '.'!");

			ClassGen c = findClass(parts[0]);
			if(c == null)
				throw new RuntimeException("Could not find class: " + parts[0]);

			for(Field f : c.getFields())
			{
				if(f.getName().equals(parts[1]))
				{
					injectGetter(cg, methodName, castType, c.getClassName(), f.getName(), f.getType());
					return;
				}
			}

			throw new RuntimeException("Could not find field: " + parts[1] + " in class: " + parts[0]);
		}

		for(Field f : cg.getFields())
		{
			if(f.getName().equals(fieldName))
			{
				if(f.getType() instanceof BasicType && !f.getType().equals(castType))
					injectGetter(cg, methodName, castType, cg.getClassName(), f.getName(), f.getType(), true);
				else
					injectGetter(cg, castType, methodName, f);
				return;
			}
		}

		throw new RuntimeException("Could not find field:" + fieldName + " in class: " + cg.getClassName());
	}

	private void injectGetter(ClassGen cg, Type castType, String methodName, Field f) {
		injectGetter(cg, methodName, castType, cg.getClassName(), f.getName(), f.getType());
	}
	
	private int getFlags(String field_class, String field_name, Type field_type) {
		ClassGen cg = findClass(field_class);
		Field fields[] = cg.getFields();
		for(Field f : fields) {
			if(field_name.equals(f.getName()) && field_type.equals(field_type)) {
				return f.getAccessFlags();
			}
		}
		throw new RuntimeException("Hooked some invlaid field -> " + field_type + " " + field_class + "." + field_name);
	}
	private ClassGen injectGetter(ClassGen into, String method_name, Type return_type, String field_class, String field_name, Type field_type){
		return injectGetter(into, method_name, return_type, field_class, field_name, field_type, false);
	}

	private ClassGen injectGetter(ClassGen into, String method_name, Type return_type, String field_class, String field_name, Type field_type, boolean checkcast) {
		boolean isStatic = (getFlags(field_class, field_name, field_type) & Constants.ACC_STATIC) != 0;
		ConstantPoolGen cpg = into.getConstantPool();
		InstructionList il = new InstructionList();
		InstructionFactory factory = new InstructionFactory(into, cpg);
		String class_name = into.getClassName();

		if(!isStatic)
			il.append(new ALOAD(0));

		il.append(factory.createFieldAccess(field_class, field_name, field_type, isStatic ? Constants.GETSTATIC : Constants.GETFIELD));
		if(checkcast && !(return_type instanceof BasicType)) 
			il.append(factory.createCheckCast(return_type instanceof ArrayType ? ((ArrayType) return_type) : (ObjectType) return_type));
		else if(checkcast)
		{
			switch(field_type.getType())
			{
			case Constants.T_DOUBLE:
				switch(return_type.getType())
				{
				case Constants.T_FLOAT:
					il.append(new D2F());
					break;
				case Constants.T_INT:
					il.append(new D2I());
					break;
				case Constants.T_LONG:
					il.append(new D2L());
					break;
				}
				break;
			case Constants.T_FLOAT:
				switch(return_type.getType())
				{
				case Constants.T_DOUBLE:
					il.append(new F2D());
					break;
				case Constants.T_INT:
					il.append(new F2I());
					break;
				case Constants.T_LONG:
					il.append(new F2L());
					break;
				}
				break;
			case Constants.T_INT:
				switch(return_type.getType())
				{
				case Constants.T_BYTE:
					il.append(new I2B());
					break;
				case Constants.T_CHAR:
					il.append(new I2C());
					break;
				case Constants.T_DOUBLE:
					il.append(new I2D());
					break;
				case Constants.T_FLOAT:
					il.append(new I2F());
					break;
				case Constants.T_LONG:
					il.append(new I2L());
					break;
				case Constants.T_SHORT:
					il.append(new I2S());
					break;
				}
				break;
			case Constants.T_LONG:
				switch(return_type.getType())
				{
				case Constants.T_DOUBLE:
					il.append(new L2D());
					break;
				case Constants.T_FLOAT:
					il.append(new L2F());
					break;
				case Constants.T_INT:
					il.append(new L2I());
					break;
				}
				break;
			}
		}
		il.append(InstructionFactory.createReturn(return_type));

		MethodGen mg = new MethodGen(
				Constants.ACC_PUBLIC | Constants.ACC_FINAL, // access_flags
				return_type, // return_type
				null, // arguement_types
				null, // arguement_names
				method_name, // method_name
				class_name, // class_name;
				il, // instruction_list;
				cpg // constant_pool_gen
		);
		mg.setMaxLocals();
		mg.setMaxStack();      

		into.addMethod(mg.getMethod());
		return into;
	}
}
