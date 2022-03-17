
# Defines compiler and compiler variables
LIBS = lib/mysql-connector-java-8.0.28.jar
JC = javac
CP = bin/out/
JVM = java
FILE = 
DEST = -d
JFLAGS = 
# Clear any default targets for building .class files
# from .java files
.SUFFIXES: .java .class

# Target entry for creating .class files from .java
.java.class:
	$(JC) $(JFLAGS) $*.java

# File with main method
MAIN = Main

UTILS = utils

PACKAGE_NAME = main.Main


# SOURCES consists of a word for each java source file
SOURCES = \
		src/utils/ConnectionUtils.java \
		src/utils/User.java \
		src/utils/Connector.java \
		src/main/Main.java \
		src/utils/Menus.java	\
		src/utils/DatabaseConnection.java	\

all:
		$(JC) -cp $(LIBS):$(CP) $(SOURCES) $(DEST) bin/out/

# Replaces all ending suffixes of .java with .class
classes: $(SOURCES:.java=.class)

# Default make target entry
default: all

# Target for running the program
run: all
		$(JVM) -cp $(LIBS):$(CP) $(PACKAGE_NAME)

# make clean will remove any created classes
clean:
		$(RM) *.class