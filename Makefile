
# Defines compiler and compiler variables
JFLAGS = -g -cp lib/mysql-connector-java-8.0.28.jar
JC = javac
JVM = java
FILE = 
DEST = -d
# Clear any default targets for building .class files
# from .java files
.SUFFIXES: .java .class

# Target entry for creating .class files from .java
.java.class:
	$(JC) $(JFLAGS) $*.java

# File with main method
MAIN = Main



# SOURCES consists of a word for each java source file
SOURCES = \
		src/utils/ConnectionUtils.java \
		src/utils/Connector.java \
		src/utils/User.java \
		src/main/Main.java

all:
		$(JC) $(JFLAGS) $(SOURCES) $(DEST) bin

# Replaces all ending suffixes of .java with .class
classes: $(SOURCES:.java=.class)

# Default make target entry
default: classes

# Target for running the program
run: classes
	$(JVM) $(MAIN) 

# make clean will remove any created classes
clean:
		$(RM) *.class