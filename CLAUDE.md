# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project overview

This is a minimal, single-module IntelliJ IDEA Java scratch project (module name `untitled`, see `untitled.iml`). There is no build tool (no Maven/Gradle) — source is compiled directly with `javac`/IntelliJ's own compiler.

This directory is its own Git repository (branch `main`, remote `origin` = https://github.com/adrianox073/untitled.git). It sits physically inside a clone of `eugenp/tutorials` (`D:\Cursos\tutorials`) but is unrelated to it; run git commands from this directory.

## Structure

- `src/Prueba.java` — the only real source file, module entry point (`public class Prueba`, `main` method). The null/empty logic lives in `static String describir(String)`.
- `test/PruebaTest.java` — JUnit 5 tests for `Prueba.describir`.
- `lib/` — `junit-platform-console-standalone` jar.
- `test.java` (repo root) — a scratch/example file, outside the `src` source folder (so IntelliJ does not compile it).
- `out/production/untitled/` — IntelliJ's compiled output directory.

Both `Prueba.java` and `test.java` assign `null` to a String and guard it with `x == null || x.isEmpty()` before use, printing `vacio`.

## Common commands

Compile and run the main class from the project root:

```
javac -d out/production/untitled src/Prueba.java
java -cp out/production/untitled Prueba
```

Tests use JUnit 5 via the standalone console jar in `lib/` (no build tool). Test sources live in `test/` (registered as a test source folder in `untitled.iml`). Compile and run them (after compiling `src`):

```
javac -d out/test/untitled -cp "out/production/untitled;lib/junit-platform-console-standalone-1.11.4.jar" test/PruebaTest.java
java -jar lib/junit-platform-console-standalone-1.11.4.jar execute -cp "out/production/untitled;out/test/untitled" --select-class PruebaTest
```

There is no linter or build script configured.