#!/bin/sh

classes=com.itzg.quidsee.proxy.WindowsProxyConfigurator

rm jni/*.h
javah -d jni -classpath target/classes $classes

echo Generated 
ls -l jni/*.h