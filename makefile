all: run

clean:
	rm -f out/TaskRunner.jar out/HullComputer.jar

out/TaskRunner.jar: out/parcs.jar src/TaskRunner.java
	@javac -cp out/parcs.jar src/TaskRunner.java
	@jar cf out/TaskRunner.jar -C src TaskRunner.class
	@rm -f src/TaskRunner.class

out/HullComputer.jar: out/parcs.jar src/HullComputer.java
	@javac -cp out/parcs.jar src/HullComputer.java
	@jar cf out/HullComputer.jar -C src HullComputer.class
	@rm -f src/HullComputer.class src/Interval.class

build: out/TaskRunner.jar out/HullComputer.jar

run: out/TaskRunner.jar out/HullComputer.jar
	@cd out && java -cp 'parcs.jar:TaskRunner.jar' TaskRunner