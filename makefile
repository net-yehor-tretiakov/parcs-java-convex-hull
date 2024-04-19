all: run

clean:
	rm -f out/TaskRunner.jar out/MassComputer.jar out/AngleComputer.jar out/HullComputing.jar

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

out/HullComputing.jar: out/parcs.jar src/HullComputing.java src/Vertex.java src/InnerHullComputerVertex.java
	@javac -cp out/parcs.jar src/HullComputing.java src/Vertex.java src/InnerHullComputerVertex.java
	@jar cf out/HullComputing.jar -C src HullComputing.class -C src Vertex.class -C src InnerHullComputerVertex.class 
	@rm -f src/HullComputing.class src/Vertex.class src/InnerHullComputerVertex.class 

build: out/TaskRunner.jar out/MassComputer.jar out/AngleComputer.jar out/HullComputing.jar 

run: out/TaskRunner.jar out/MassComputer.jar out/AngleComputer.jar out/HullComputing.jar
	@cd out && java -cp 'parcs.jar:TaskRunner.jar' TaskRunner