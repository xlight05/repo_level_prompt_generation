@echo off
echo Lancement de la base de données Localisation
java -classpath hsqldb.jar org.hsqldb.Server -database.0 localisation -dbname.0 localisation
