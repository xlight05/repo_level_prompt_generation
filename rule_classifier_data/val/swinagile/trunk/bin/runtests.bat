@echo off
pushd %~dp0
cd ..
rem SET JAVA_HOME=.\lib\jdk1.5.0_07
set ANT_HOME=%cd%\lib\apache-ant-1.6.5
set CLASSPATH=%JAVA_HOME%\lib;%cd%\lib\junit-4.1.jar;%cd%\lib
set PATH=%JAVA_HOME%\bin;%ANT_HOME%\bin;%PATH%
echo.
echo.
cls
ANT test
popd
