all: run

clean:
	rm -f out/TaskRunner.jar out/MassComputer.jar out/AngleComputer.jar out/HullComputer.jar

out/TaskRunner.jar: out/parcs.jar src/TaskRunner.java src/Vertex.java
	@javac -cp out/parcs.jar src/TaskRunner.java src/Vertex.java
	@jar cf out/TaskRunner.jar -C src TaskRunner.class -C src Vertex.class
	@rm -f src/TaskRunner.class src/Vertex.class

out/MassComputer.jar: out/parcs.jar src/MassComputer.java src/Vertex.java
	@javac -cp out/parcs.jar src/MassComputer.java src/Vertex.java
	@jar cf out/MassComputer.jar -C src MassComputer.class -C src Vertex.class
	@rm -f src/MassComputer.class src/Vertex.class

out/AngleComputer.jar: out/parcs.jar src/AngleComputer.java src/Vertex.java
	@javac -cp out/parcs.jar src/AngleComputer.java src/Vertex.java
	@jar cf out/AngleComputer.jar -C src AngleComputer.class -C src Vertex.class
	@rm -f src/AngleComputer.class src/Vertex.class

out/HullComputer.jar: out/parcs.jar src/HullComputer.java src/Vertex.java
	@javac -cp out/parcs.jar src/HullComputer.java src/Vertex.java
	@jar cf out/HullComputer.jar -C src HullComputer.class -C src Vertex.class
	@rm -f src/HullComputer.class src/Vertex.class

build: out/TaskRunner.jar out/MassComputer.jar out/AngleComputer.jar out/HullComputer.jar 

run: out/TaskRunner.jar out/MassComputer.jar out/AngleComputer.jar out/HullComputer.jar
	@cd out && java -cp 'parcs.jar:TaskRunner.jar' TaskRunner