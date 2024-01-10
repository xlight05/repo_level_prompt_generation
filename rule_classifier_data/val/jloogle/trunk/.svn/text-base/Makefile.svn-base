include Make.defs

JAVAOPTS=
TEST_JAVAOPTS=-classpath .$(SEP)test$(SEP)$(JUNIT_JAR)
NAME=jloogle
VERSION=0.1.1
DISTSRC=$(NAME)-$(VERSION)-src
DIST=$(NAME)-$(VERSION)

GENFILES=Builds.java Visitor.java License.java Excludes.java
TESTFILES=$(shell, ls tests | grep -v "\.svn")
ALL_BUT_EXCLUDES=$(shell, ls *.java | grep -v Excludes.java)

PARSER=Parser
MAIN=Main

%.jj:%.jjt
	jjtree $<

%.java:%.jj
	javacc $<

test/%.class:test/%.java
	javac -g $(TEST_JAVAOPTS) -sourcepath test -d test $<

%.class:%.java
	javac -g $(JAVAOPTS) $<

%.run:%.class
	java $(JAVAOPTS) $*

%.test:test/%.class
	java $(TEST_JAVAOPTS) org.junit.runner.JUnitCore $*

%.tar.gz:%.tar
	gzip -f -9 $<

all: $(MAIN).class
run: $(MAIN).run
jjt: $(PARSER).jjt
jj: $(PARSER).jj
jar: $(NAME).jar
runtests: all test/AllTests.class AllTests.test
compiletests: all test/AllTests.class
rt: runtests
ct: compiletests

upload: release
	ant upload

lastUpdated.inc: Makefile *.php
	echo `date` > $@

$(MAIN).class: $(PARSER).jjt $(PARSER).jj $(GENFILES) $(PARSER).class \
									MatcherVisitor.class JLoogle.class $(PARSER).class

compile: $(PARSER).jjt $(PARSER).jj $(GENFILES)
	javac -g $(JAVAOPTS) *.java

Visitor.java: AST*.java Makefile
	rm -f $@
	touch $@
	echo "interface Visitor {" >> $@
	for f in `ls AST*.java | sed -e 's/.java//g'`; do\
		echo "  void visit($$f node);" >> $@; \
	done
	echo "}" >> $@

Excludes.java: Makefile $(ALL_BUT_EXCLUDES) $(PARSER).java
	rm -f $@
	touch $@
	echo "final class Excludes {" >> $@
	echo "  final static String[] EXCLUDES = {" >> $@
	for f in `ls *.java | sed -e 's/.java//g'`; do\
		echo "  \"$$f\"," >> $@; \
	done
	echo "  };" >> $@
	echo "}" >> $@

License.java: Makefile LICENSE
	rm -f $@
	touch $@
	echo "final class License {" >> $@
	echo "  static void print() {" >> $@
	cat LICENSE | sed -e 's/"/\\"/g' | sed -e 's/^/    System.err.println\("/g'  | sed -e 's/$$/"\);/g' >> $@
	echo "  }" >> $@
	echo "}" >> $@

Builds.java: Makefile
	rm -f $@
	touch $@
	echo "final class Builds {" >> $@
	echo "  final static String BUILD_DATE_STRING = \"" `date` "\";" >> $@
	echo "  final static String VERSION_STRING = \"$(VERSION)\";" >> $@
	echo "}" >> $@

test/AllTests.java: Makefile test/AllTests.template.start \
											test/AllTests.template.end test/*Test.java
	rm -f $@
	touch $@
	cat test/AllTests.template.start >> $@
	for f in `ls test/*Test.java | sed -e 's/.java//g' | sed -e 's/test\///g'`; do\
		echo "$$f.class," >> $@; \
	done
	cat test/AllTests.template.end >> $@

$(DISTSRC).tar: Makefile *.java $(TESTFILES) $(PARSER).jjt LICENSE \
							README maketargets test/*.java test/*.template.*
	tar cvf $@ $^

$(NAME).jar: $(MAIN).class manifest.mf LICENSE
	jar cvfm $@ manifest.mf *.class LICENSE

manifest.mf: Makefile
	rm -f $@
	touch $@
	echo "Main-class: $(MAIN)" >> $@

release: jjdoc dist jar api
	rm -rf release/$(DIST)
	mkdir -p release/$(DIST)
	mkdir -p release/$(DIST)/bin
	mkdir -p release/$(DIST)/doc
	mkdir -p release/$(DIST)/doc/api
	mkdir -p release/$(DIST)/lib
	cp $(NAME).jar release/$(DIST)/lib
	cp jloogle release/$(DIST)/bin
	cp Parser.html release/$(DIST)/doc
	cp -r api/* release/$(DIST)/doc/api
	cp LICENSE release/$(DIST)
	cp README release/$(DIST)
	tar cvf release/$(DIST).tar release/$(DIST)
	gzip -f -9 release/$(DIST).tar
	rm -rf release/$(DIST)

install: $(NAME).jar
	mkdir -p /usr/share/$(NAME)/bin
	cp $(NAME).jar /usr/share/$(NAME)
	rm -f /usr/share/$(NAME)/bin/jloogle
	touch /usr/share/$(NAME)/bin/jloogle
	echo "#\!/bin/sh" >> /usr/share/$(NAME)/bin/jloogle
	echo "java -jar /usr/share/$(NAME)/jloogle.jar $$""@" >> /usr/share/$(NAME)/bin/jloogle
	chmod +x /usr/share/$(NAME)/bin/jloogle
	ln -fns /usr/share/$(NAME)/bin/jloogle /usr/local/bin

uninstall:
	rm -rf /usr/share/$(NAME)

test: tests/*.j $(MAIN).class
	for f in `ls tests/*.j`; do \
		java $(MAIN) $$f; \
	done

dist: $(DISTSRC).tar.gz
	mkdir -p release
	cp $^ release

parser: $(PARSER).java

api: all
	rm -rf $@
	mkdir -p $@
	javadoc -d $@ *.java

jjdoc: $(PARSER).jj
	jjdoc $^

jjdocclean:
	rm -f $(PARSER).html

jjtreeclean:
	rm -f $(PARSER).jj $(PARSER)TreeConstants.java JJT$(PARSER)State.java
	rm -f $(GENFILES)

javaccclean:
	rm -f $(PARSER).java $(PARSER)Constants.java $(PARSER)TokenManager.java\
		TokenMgrError.java ParseException.java SimpleCharStream.java\
		Token.java 

distclean:
	rm -rf $(DISTSRC).tar $(DISTSRC).tar.gz org META-INF *.jar

docclean:
	rm -rf api

testclean:
	rm -f test/AllTests.java test/*~ test/*.class

clean: jjtreeclean javaccclean jjdocclean docclean
	rm -f *.class *~ tests/*~ manifest.mf

allclean: clean distclean testclean