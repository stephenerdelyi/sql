JFLAGS = -g
JRUNNER = java
MAIN = SQL
JC = javac
.SUFFIXES: .java .class
.java.class:
		$(JC) $(JFLAGS) $*.java

CLASSES = \
		Console.java\
		WriteFile.java\
		ReadFile.java

default: classes

classes: $(CLASSES:.java=.class)
		clear
		@echo "✓ SQL Compiled - Runtime Dependencies Now Available [$(MAIN)]"
		@echo "✓ SQL Executed - Operating System will Initialize"
		@($(JC) $(MAIN).java)
		@($(JRUNNER) $(MAIN))

norun: $(CLASSES:.java=.class)
		clear
		@echo "✓ SQL Compiled - Runtime Dependencies Now Available [$(MAIN)]"
		@echo ""

clean:
		$(RM) *.class
		$(RM) *.lgf
		$(RM) *.zip
		clear
		@echo "✓ SQL Cleaned - Runtime Dependencies No Longer Available"
		@echo ""

export:
		@(zip -r "PA1_SQLSim_ErdelyiStephen.zip" . -x ".git/*" "*.class" "*.lgf" "*.zip")
		clear
		@echo "✓ OS Exported - ZIP File Now Available [SimO$(OSNUM)]"
		@echo ""

commands:
		clear
		@echo "///////////////////////////////////////////////////////////////"
		@echo "//                    MAKEFILE COMMANDS                      //"
		@echo "///////////////////////////////////////////////////////////////"
		@echo "make      	makes the executable and runs the program"
		@echo "make norun	makes the executable w/o running the program"
		@echo "make clean	removes the executable and unnecessary files"
		@echo "make export	makes a packaged .zip for submission"
		@echo ""
