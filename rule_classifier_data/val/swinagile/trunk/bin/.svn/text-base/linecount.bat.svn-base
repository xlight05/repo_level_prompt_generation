@echo off
cls
echo.
echo ------------------------------
echo  Line Count Script
echo ------------------------------
echo.

pushd %~dp0
pushd ..
for /r src %%f in (*.java) do bin\wc.exe --lines %%f
popd
popd
