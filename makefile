all: run

clean:
	rm -f out/TaskRunner.jar out/MassComputer.jar

out/TaskRunner.jar: out/parcs.jar src/TaskRunner.java src/Vertex.java
	@javac -cp out/parcs.jar src/TaskRunner.java src/Vertex.java
	@jar cf out/TaskRunner.jar -C src TaskRunner.class -C src Vertex.class
	@rm -f src/TaskRunner.class src/Vertex.class

out/MassComputer.jar: out/parcs.jar src/MassComputer.java src/Vertex.java
	@javac -cp out/parcs.jar src/MassComputer.java src/Vertex.java
	@jar cf out/MassComputer.jar -C src MassComputer.class -C src Vertex.class
	@rm -f src/MassComputer.class src/Vertex.class

build: out/TaskRunner.jar out/MassComputer.jar

run: out/TaskRunner.jar out/MassComputer.jar
	@cd out && java -cp 'parcs.jar:TaskRunner.jar' TaskRunner