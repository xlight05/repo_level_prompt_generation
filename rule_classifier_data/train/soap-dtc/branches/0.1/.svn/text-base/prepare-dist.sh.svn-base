#!/bin/bash

# This script builds the www structure to be deployed on a web server

cd soap-dtc
mvn clean
mvn test || exit 1

cd ../soap-dtc-core
mvn javadoc:javadoc || exit 1
mvn jxr:jxr || exit 1
mvn assembly:assembly || exit 1

cd ../soap-dtc-server
mvn javadoc:javadoc || exit 1
mvn jxr:jxr || exit 1
mvn assembly:assembly || exit 1

cd ../soap-dtc
mvn assembly:assembly || exit 1
mvn deploy || exit 1

cd ..
mkdir www

mkdir www/releases
cp soap-dtc/target/*.zip www/releases
cp soap-dtc/target/*.tar.bz2 www/releases
cp soap-dtc-core/target/*.zip www/releases
cp soap-dtc-core/target/*.tar.bz2 www/releases
cp soap-dtc-server/target/*.zip www/releases
cp soap-dtc-server/target/*.tar.bz2 www/releases

cp -R repository www/

mkdir www/generated-docs
mkdir www/generated-docs/soap-dtc-core
mkdir www/generated-docs/soap-dtc-server
cp -R soap-dtc-core/target/site/apidocs www/generated-docs/soap-dtc-core
cp -R soap-dtc-server/target/site/apidocs www/generated-docs/soap-dtc-server

cp -R soap-dtc-core/target/site/xref www/generated-docs/soap-dtc-core
cp -R soap-dtc-server/target/site/xref www/generated-docs/soap-dtc-server

