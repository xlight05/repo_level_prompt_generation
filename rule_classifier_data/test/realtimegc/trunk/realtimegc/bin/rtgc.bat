@echo off
rem usage:
rem rtgc [filename] [SUN|IBM] title

@start javaw -cp rtgc.jar;commons-logging-1.1.1.jar;jcommon-1.0.12.jar;jfreechart-1.0.9.jar;joda-time-1.6.jar com.shautvast.realtimegc.Main %*
