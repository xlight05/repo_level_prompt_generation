###########################################################
##                                                       ##
## Assuming you have Java 5+, MySQL and Maven2 installed ##
##                                                       ##
###########################################################

#__1__ start MySQL
net start MySQL

#__2__ create the db
CREATE DATABASE `ejb3-maven`

#__3__ Create the sample table
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `first_name` varchar(50) default NULL,
  `last_name` varchar(50) default NULL,
  `birth_day` timestamp NULL default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1

#__4__ update user DB passwords at:
./ejb3-maven/misc/roussev-ds.xml
./ejb3-maven/roussev-ejb/src/test/resources/META-INF/persistence.xml

#__5__ Run Maven2
mvn clean install

#__6__ Copy the EAR artifact to JBoss deploy
cp ./ejb3-maven/roussev-ear/target/ejb3-maven-1.0.ear $JBOSS_HOME/server/default/deploy/ejb3-maven-1.0.ear

#__7__ Copy the datasource configuration to JBoss deploy (You need to do this only once)
cp ./ejb3-maven/misc/roussev-ds.xml  $JBOSS_HOME/server/default/deploy/roussev-ds.xml

#__8__ Copy the datasource configuration to JBoss deploy (You need to do this only once)
cp ./ejb3-maven/misc/jboss-log4j.xml  $JBOSS_HOME/server/default/conf/jboss-log4j.xml

#__9__ Access the web applciation at http://localhost:8080/ejb3-maven/user
cp ./ejb3-maven/misc/jboss-log4j.xml  $JBOSS_HOME/server/default/conf/jboss-log4j.xml

