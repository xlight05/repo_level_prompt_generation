import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.*;
import java.util.jar.*;
import java.util.regex.*;

/**
 * The main dog.
 */
final class JLoogle {

  /** The set of classes that we exclude from searching. */
  private final static Set<String> EXCLUDES = new HashSet<String>();
  static {
    for (String s : Excludes.EXCLUDES) {
      EXCLUDES.add(s);
    }
  }

  /** Files containing method patterns. */
  private final List<File> filesToParse = new ArrayList<File>();

  /** Files containing class files. */
  private final List<File> classFilesToProcess = new ArrayList<File>();

  /** Strings containing method patterns. */
  private final List<String> stringsToParse = new ArrayList<String>();

  /**
   * The class loader used to find classes. Set in {@link
   * #createClassLoader()}.
   */
  private ClassLoader classLoader;

  /* Options */
  private boolean printFileNames;
  private boolean verbose;
  private Output output;

  // --------------------------------------------------
  // Interface
  // --------------------------------------------------

  /**
   * Sets the {@link Output} with which to output methods.
   *
   * @param output the new {@link Output} with which to output methods.
   */
  public void setOutput(Output output) {
    this.output = output;
  }

  /**
   * Adds the path to the classpath.
   *
   * @param classpath String defined with {@link File#pathSeparator}.
   */
  public void addToClasspath(String classpath) {
    for (StringTokenizer st = new StringTokenizer(classpath, 
                                                  File.pathSeparator); 
         st.hasMoreTokens();) {
      File path = new File(st.nextToken());
      addClasspath(path);
    }
  }

  /**
   * Adds <code>path</code> to the classpath.
   *
   * @param new path to add to the classpath.
   */
  public void addClasspath(File path) {
    if (path.exists()) {
      classFilesToProcess.add(path);
    }
  }
  

  /** 
   * Main programmatic entry point. 
   */
  public void realMain(String[] args) throws Exception {
    processArgs(args);
    setOptions();
    createClassLoader();
    if (filesToParse.isEmpty()) {
      if (stringsToParse.isEmpty()) {
        process(new Parser(System.in));
      } else {
        for (String s : stringsToParse) {
          process(new Parser(new StringInputStream(s)));
        }
      }
    } else {
      for (File res : filesToParse) {
        try {
          process(new Parser(new FileInputStream(res)));
        } catch (java.io.FileNotFoundException e) {
          handle(e);
        }
      }
    }
  }

  /** 
   * Creates the class loader from the list of files to search and
   * sets {@link #classLoader}. 
   */
  private void createClassLoader() {
    if (classFilesToProcess.isEmpty()) {
      classLoader = ClassLoader.getSystemClassLoader();
      addToClasspath(System.getProperty("java.class.path"));
    } else {
      List<URL> urls = new ArrayList<URL>();
      for (File f : classFilesToProcess) {
        try {
          if (verbose) Log.n("Reading jar " + f);
          urls.add(f.toURL());
        } catch (MalformedURLException e) {
          handle(e);
        }
      }
      classLoader = new URLClassLoader((URL[])urls.toArray(new URL[urls.size()]),
                                       ClassLoader.getSystemClassLoader());
    }
  }

  /** Sets various options. */
  private void setOptions() {
    Log.setVerbose(verbose);
    if (output == null) {
      setOutput(new Output() {
          public void output(Method m) {
            JLoogle.this.output(m);
          }
        });
    }
  }

  /**
   * Sets the options and add files to {@link #filesToParse} and
   * {@link classFilesToProcess}.
   */
  private void processArgs(String[] args) {
    boolean exit = false;
    for (int i=0; i<args.length;) {
      String arg = args[i++];
      if (arg.startsWith("-f") || arg.equals("--print-files")) {
        printFileNames = true;
      } else if (arg.equals("-v") || arg.equals("--verbose")) {
        verbose = true;
      } else if (arg.equals("-l") || arg.equals("--license")) {
        printLicense();
        exit = true;
      } else if (arg.equals("-b") || arg.equals("--build-date")) {
        printBuildDate();
        exit = true;
      } else if (arg.equals("-c")     || arg.equals("--code")) {
        printCodeURL();
        exit = true;
      } else if (arg.equals("-p") || arg.equals("--classpath")) {
        String classpath = demandArg(i++,args,arg);
        if (classpath == null) {
          exit = true;
        } else {
          addToClasspath(classpath);
        }
      } else if (arg.equals("-o") || arg.equals("--output")) {
        String outputClass = demandArg(i++,args,arg);
        if (outputClass == null) {
          exit = true;
        } else {
          setOutput(createOutput(outputClass));
        }
      } else if (arg.equals("-h") || arg.equals("--help")) {
        printHelp();
        exit = true;
      } else if (arg.startsWith("-")) {
        error("Unkown option: " + arg);
        exit = true;
      } else {
        File f = new File(arg);
        if (!f.exists()) {
          stringsToParse.add(arg);
        } else {
          if (f.isDirectory() || f.getName().endsWith(".jar") || f.getName().endsWith(".class")) {
            classFilesToProcess.add(f);
          } else {
            filesToParse.add(f);
          }
        }
      }
    }
    if (exit) exit(0);
  }

  private Output createOutput(String className) {
    try {
      return (Output)Class.forName(className).newInstance();
    } catch (ClassNotFoundException e) {
      handle(e);
    } catch (InstantiationException e) {
      handle(e);
    } catch (IllegalAccessException e) {
      handle(e);
    }
    error("Unable to create output for class " + className);
    exit(0);
    return null;
  }

  private String demandArg(int i, String[] args, String name) {
    if (i >= args.length) {
      error("Required argument to " + name);
      return null;
    }
    return args[i];
  }

  /** Prints the license. */
  private void printLicense() {
    License.print();
  }

  /** Prints the build date. */
  private void printBuildDate() {
    System.err.println("Last built: " + Builds.BUILD_DATE_STRING);
    System.err.println("Version: " + Builds.VERSION_STRING);
  }

  /** Prints the code URL. */
  private void printCodeURL() {
    System.err.println("The project code is:");
    System.err.println();
    System.err.println("  http://code.google.com/p/jloogle/");
    System.err.println();
  }

  /** Prints help. */
  private void printHelp() {
    System.err.println("Usage: java " + getClass().getSimpleName() + " [ <option> | <file> ]*");
    System.err.println("where <option> is one of");
    System.err.println("   -f  || --print-files       Print file names");
    System.err.println("   -v  || --verbose           Print verbose messages");
    System.err.println("   -l  || --license           Print the license");
    System.err.println("   -b  || --build-date        Print the build date and version");
    System.err.println("   -c  || --code              Print the code URL");
    System.err.println("   -p  || --classpath <path>  use classpath <path>");
    System.err.println("   -o  || --output <class>    Use <class> for output");
    System.err.println("   -h  || --help              Print this message");
    System.err.println("and <file>s are files:");
    System.err.println("   -*.jar                     Jar files containing classes");
    System.err.println("   -*.class                   Class files to process");
    System.err.println("   -directory                 Directories containing class files");
    System.err.println("   -...                       Files containing method patterns");
    System.err.println("If no files are given, we read from STDIN");
  }

  /** Exits with code <code>code</code>. */
  private void exit(int code) {
    System.exit(code);
  }

  /** Prints an error. */
  private void error(String msg) {
    System.err.println(msg);
  }

  /** Handles an exception. */
  private void handle(Throwable t) {
    t.printStackTrace();
  }

  /** Processes the parser and goes through all the classes on the classpath. */
  private void process(Parser parser) throws Exception {
    try {
      ASTStart n = parser.Start();
      for (File path : classFilesToProcess) {
        if (path.isDirectory()) {
          List<File> q = new ArrayList<File>();
          q.add(path);
          final int dirPathLength = path.getAbsolutePath().length();
          while (!q.isEmpty()) {
            for (File f : q.remove(0).listFiles()) {
              if (f.isDirectory()) {
                q.add(f);
              } else {
                processClass(n,f.getAbsolutePath().substring(dirPathLength+1),f);
              }
            }
          }
        } else if (path.getName().endsWith(".jar")) {
          JarFile jf = new JarFile(path);
          for (Enumeration en = jf.entries(); en.hasMoreElements();) {
            processClass(n,((JarEntry)en.nextElement()).getName(),path);
          }
        } else {
          error("Invalid class files: " + path);
        }
      }
    } catch (ParseException e) {
      handle(e);
    }
  }

  /** 
   * Processes a single class with relative name
   * <code>classFileName</code>.  Example:
   * <code>a/v/c/ClassName.class</code>.
   */
  private void processClass(ASTStart n, String classFileName, 
                            Object from) throws Exception {
    if (!classFileName.endsWith(".class")) return;
    if (classFileName.indexOf("$") != -1) return;
    if ("".equals(classFileName)) return;
    if (classFileName.endsWith(".")) return;
    if (EXCLUDES.contains(classFileName)) return;
    for (String exclude : EXCLUDES) {
      if (Pattern.matches(exclude + "[^\\.]*\\.class", classFileName)) {
        return;
      }
    }
    String className = classFileName.substring(0, classFileName.length()-6)
      .replace("/",".");
    try {
      Class cls = loadClass(className);
      Log.n("Class " + Util.toString(cls) + " from " + from);
      for (Method m : cls.getMethods()) {
        MatcherVisitor matcher = new MatcherVisitor(m);
        if (matcher.matches(n)) output.output(m);
      }
      for (Method m : cls.getDeclaredMethods()) {
        m.setAccessible(true);
        //
        // Skip the public ones, since we already did these
        //
        if (Modifier.isPublic(m.getModifiers())) continue;
        MatcherVisitor matcher = new MatcherVisitor(m);
        if (matcher.matches(n)) output.output(m);
      }
    } catch (NoClassDefFoundError ignore) {}
  }

  private Class loadClass(String className) throws ClassNotFoundException {
    return classLoader.loadClass(className);
  }
  
  /**
   * Outputs the class <code>cls</code>'s method <code>m</code> to STDOUT.
   */
  private static void output(Method m) {
    Class cls = m.getDeclaringClass();
    StringBuilder sb = new StringBuilder();
    sb.append(Util.toString(m.getReturnType()));
    sb.append(" ");
    sb.append(Util.toString(cls));
    sb.append(".");
    sb.append(m.getName());
    sb.append("(");
    boolean first = true;
    for (Class t : m.getParameterTypes()) {
      sb.append(Util.toString(t));
      if (!first) sb.append(",");
      first = false;
    }
    sb.append(")");
    System.out.println(sb.toString());
  }

}