@echo off
cls
echo.
echo ------------------------------
echo  Library Synchronisation Tool
echo ------------------------------
echo.

pushd %~dp0
pushd ..\lib
..\bin\wget.exe --tries=5 --timestamping --input-file=..\bin\libraries.conf
..\bin\7z.exe x -bd -aos .\*.zip
popd
popd
