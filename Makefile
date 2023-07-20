BUILD=./bin
CLASSPATH=/usr/share/java/hamcrest-core-1.3.jar:/usr/share/java/junit4.jar:$(BUILD)
JAVA=java -cp $(CLASSPATH)
JAVAC=javac -source 1.7 -target 1.7 -cp $(CLASSPATH) -sourcepath . -d $(BUILD)
JDB=jdb -classpath $(CLASSPATH) -sourcepath .

TESTRUNNER=org.junit.runner.JUnitCore
JAVAFILES= $(wildcard *.java)
TESTS= $(patsubst %.java,%,$(wildcard *Tests.java))

verify: $(JAVAFILES)
	@for f in $^; do cn=$${f%.java}; grep "[interface|class].*$${cn}" $$f || { echo "Your submission is misnamed or not a .java file"; exit 1; }; done

bin: $(JAVAFILES)
	mkdir $(BUILD)
	$(JAVAC) $(JAVAFILES)

run: bin
	$(JAVA) $(TESTRUNNER) $(TESTS)

dbg: bin
	$(JDB) $(TESTRUNNER) $(TESTS)

clean:
	rm -rf *~ bin
