set JAVA_HOME="C:\Program Files\Java\jre1.6.0_05"
set HSQLDB_LIB="D:\Travail\Eclipse\BDD\hsqldb.jar"

%JAVA_HOME%\bin\java -classpath %HSQLDB_LIB% org.hsqldb.Server -database.0 localisation
