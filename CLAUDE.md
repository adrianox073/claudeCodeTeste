# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project overview

This is a minimal, single-module Maven Java project (artifact `untitled`, compiled for Java 11; IntelliJ module `untitled.iml` is Maven-managed). Dependencies: H2 (runtime persistence) and JUnit 5 (tests). The local Maven repository on this machine is `D:\code\repo` (not `~/.m2`), so never hardcode jar paths; ask Maven for the classpath.

This directory is its own Git repository (branch `main`, remote `origin` = https://github.com/adrianox073/untitled.git). It sits physically inside a clone of `eugenp/tutorials` (`D:\Cursos\tutorials`) but is unrelated to it; run git commands from this directory.

## Structure

- `src/main/java/Prueba.java` — entry point. `static String describir(String)` returns `"vacio"` for null/empty input, otherwise the input. `main` evaluates sample texts, saves each one with its result to H2, and prints the stored rows.
- `src/main/java/MensajeRepository.java` — plain JDBC repository over table `mensaje (id IDENTITY, texto, resultado, fecha)`; creates the table on construction, `guardar(texto, resultado)`, `listar()`. `URL_MEMORIA` = `jdbc:h2:mem:untitled;DB_CLOSE_DELAY=-1` (in-memory, lives for the JVM lifetime; data is lost when the program exits).
- `src/main/java/Mensaje.java` — immutable row value (no records: Java 11 target).
- `src/test/java/` — `PruebaTest` (describir) and `MensajeRepositoryTest` (each test uses its own random in-memory H2 DB).

## Common commands

```
mvn test                     # compile and run all tests
mvn compile
mvn -q dependency:build-classpath -Dmdep.includeScope=runtime -Dmdep.outputFile=target/cp.txt
java -cp "target/classes;$(cat target/cp.txt)" Prueba    # Git Bash; ';' is the Windows separator
```

There is no linter configured.
